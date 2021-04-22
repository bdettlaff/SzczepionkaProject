package com.szczepionka.service;

import com.szczepionka.entity.Appointment;
import com.szczepionka.entity.LocationDetails;
import com.szczepionka.entity.Patient;
import com.szczepionka.exception.PatientAlreadyExistsException;
import com.szczepionka.exception.PatientCantEnrollToSecondAppointment;
import com.szczepionka.model.AppointmentDetailsDTO;
import com.szczepionka.model.AppointmentStatus;
import com.szczepionka.model.PatientDTO;
import com.szczepionka.model.VaccinationBrandt;
import com.szczepionka.repository.AppointmentRepository;
import com.szczepionka.util.VaccinationLocationsFetcher;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientService patientService;
    private final VaccinationLocationsFetcher vaccinationLocationsFetcher;
    private final ModelMapper modelMapper;
    private final EmailService emailService;

    public AppointmentService(AppointmentRepository appointmentRepository, PatientService patientService, VaccinationLocationsFetcher vaccinationLocationsFetcher, ModelMapper modelMapper, EmailService emailService) {
        this.appointmentRepository = appointmentRepository;
        this.patientService = patientService;
        this.vaccinationLocationsFetcher = vaccinationLocationsFetcher;
        this.modelMapper = modelMapper;
        this.emailService = emailService;
    }

    public Appointment enrollFirstAppointment(PatientDTO patientDTO, Long locationId) throws PatientAlreadyExistsException, MessagingException {
        Appointment appointment;
        Patient patient = patientService.findPatientByPesel(patientDTO.getPesel());

        if (patient != null && isFirstAppointmentCancelled(patient.getId())) {
            appointment = updatePatientAndFirstAppointment(patient, patientDTO, locationId);
        } else if (patient != null && !isFirstAppointmentCancelled(patient.getId())) {
            throw new PatientAlreadyExistsException("Patient with given PESEL is already enrolled");
        } else {
            appointment = newFirstAppointment(patientDTO, locationId);
        }

        return appointment;
    }

    private Appointment updatePatientAndFirstAppointment(Patient patient, PatientDTO patientDTO, Long locationId) throws MessagingException {
        patientService.updatePatient(patient.getPesel(), patientDTO);

        Optional<Appointment> appointment = appointmentRepository.findByPatientId(patient.getId());
        if (appointment.isPresent()) {
            appointment.get().setPatientId(patient.getId());
            appointment.get().setFirstAppointmentDate(createAppointmentDate());
            appointment.get().setFirstAppointmentTime(createAppointmentTime());
            appointment.get().setFirstAppointmentStatus(AppointmentStatus.PLANNED);
            appointment.get().setLocationDetails(getLocationDetailsById(locationId));
            appointmentRepository.save(appointment.get());

            emailService.sendMail(patient.getId());
            return appointment.get();
        }

        return null;
    }

    public Appointment newFirstAppointment(PatientDTO patientDTO, long locationId) throws MessagingException {
        Patient patient = patientService.addPatient(patientDTO);

        Appointment appointment = Appointment.builder()
                .patientId(patient.getId())
                .firstAppointmentDate(createAppointmentDate())
                .firstAppointmentTime(createAppointmentTime())
                .firstAppointmentStatus(AppointmentStatus.PLANNED)
                .locationDetails(getLocationDetailsById(locationId))
                .build();
        emailService.sendMail(patient.getId());
        return appointmentRepository.save(appointment);
    }

    public Appointment enrollSecondAppointment(Long appointmentId) throws PatientCantEnrollToSecondAppointment {
        boolean canEnroll = validateIfPatientCanEnrollSecondAppointment(appointmentId);
        if (!canEnroll) {
            throw new PatientCantEnrollToSecondAppointment("Patient is already enrolled to the second appointment");
        }
        return newSecondAppointment(appointmentId);
    }

    public Appointment newSecondAppointment(Long appointmentId) {
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
        if (appointment.isPresent()) {
            int timeGapBetweenVaccinations = getTimeGapBetweenVaccinations(appointment.get().getLocationDetails().getVaccinationBrandt());
            appointment.get().setSecondAppointmentDate(createAppointmentDate().plusDays(timeGapBetweenVaccinations));
            appointment.get().setSecondAppointmentTime(createAppointmentTime());
            appointment.get().setSecondAppointmentStatus(AppointmentStatus.PLANNED);
            return appointmentRepository.save(appointment.get());
        }
        return null;
    }

    private LocalDate createAppointmentDate() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.plusDays(getRandomNumberOfDays());
    }

    private int getRandomNumberOfDays() {
        return ThreadLocalRandom.current().nextInt(1, 30);
    }

    private LocalTime createAppointmentTime() {
        LocalTime startingWorkingHours = LocalTime.of(8, 0);
        return startingWorkingHours.plusMinutes(getRandomMinutesOffset());
    }

    private int getRandomMinutesOffset() {
        return 20 * ThreadLocalRandom.current().nextInt(0, 24);
    }

    private LocationDetails getLocationDetailsById(long id) {
        return modelMapper.map(vaccinationLocationsFetcher.getById(id), LocationDetails.class);
    }

    public Appointment cancelAppointment(int appointmentNumber, Long appointmentId) {
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
        if (appointment.isPresent()) {
            if (appointmentNumber == 1) {
                appointment.get().setFirstAppointmentStatus(AppointmentStatus.CANCELLED);
            } else if (appointmentNumber == 2) {
                appointment.get().setSecondAppointmentStatus(AppointmentStatus.CANCELLED);
            }
            return appointmentRepository.save(appointment.get());
        }
        return null;
    }

    public AppointmentDetailsDTO getAppointmentDetails(UUID patientUUID) {
        Patient patient = patientService.findPatientByUUID(patientUUID);
        Optional<Appointment> appointmentOptional = appointmentRepository.findByPatientId(patient.getId());
        if (appointmentOptional.isPresent()) {
            Appointment appointment = appointmentOptional.get();
            LocationDetails locationDetails = appointment.getLocationDetails();

            return AppointmentDetailsDTO.builder()
                    .appointmentId(appointment.getId())
                    .appointmentLocationName(locationDetails.getLocationName())
                    .appointmentLocationAddress(locationDetails.getAddress())
                    .appointmentLocationPostalCode(locationDetails.getPostalCode())
                    .appointmentLocationCity(locationDetails.getCity())
                    .vaccinationBrandt(locationDetails.getVaccinationBrandt())
                    .firstAppointmentStatus(appointment.getFirstAppointmentStatus())
                    .firstAppointmentDate(appointment.getFirstAppointmentDate())
                    .firstAppointmentTime(appointment.getFirstAppointmentTime())
                    .secondAppointmentStatus(appointment.getSecondAppointmentStatus())
                    .secondAppointmentDate(appointment.getSecondAppointmentDate())
                    .secondAppointmentTime(appointment.getSecondAppointmentTime())
                    .patientReferralId(patient.getReferralId())
                    .patientUUID(patient.getUuid())
                    .build();
        }
        return null;
    }

    private int getTimeGapBetweenVaccinations(VaccinationBrandt vaccinationBrandt) {
        switch (vaccinationBrandt) {
            case PFIZER:
            case MODERNA:
                return 42; //6 weeks
            case ASTRA_ZENECA:
                return 84; //12 weeks
            default:
                return 56; //8 weeks
        }
    }

    private boolean isFirstAppointmentCancelled(Long patientId) {
        Optional<Appointment> appointment = appointmentRepository.findByPatientId(patientId);
        return appointment.isPresent() && appointment.get().getFirstAppointmentStatus() == AppointmentStatus.CANCELLED;
    }

    public boolean validateIfPatientCanEnrollSecondAppointment(Long appointmentId) {
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
        return appointment.isPresent() && isSecondAppointmentNullOrCancelled(appointment.get().getSecondAppointmentStatus());
    }

    private boolean isSecondAppointmentNullOrCancelled(AppointmentStatus secondAppointmentStatus) {
        return secondAppointmentStatus == null || secondAppointmentStatus == AppointmentStatus.CANCELLED;
    }

}
