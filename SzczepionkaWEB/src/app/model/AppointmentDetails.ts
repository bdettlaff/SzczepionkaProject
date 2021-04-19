export class AppointmentDetails {

    patientReferralId: string;
    vaccinationBrandt: string;
    firstAppointmentStatus: string;
    firstAppointmentDate: string;
    firstAppointmentTime: string;
    secondAppointmentStatus: string;
    secondAppointmentDate: string;
    secondAppointmentTime: string;
    appointmentLocationCity: string;
    appointmentLocationPostalCode: string;
    appointmentLocationAddress: string;
    appointmentLocationName: string;
    patientUUID: string;
    appointmentId: string;

    constructor(patientReferralId: string,
                vaccinationBrandt: string,
                firstAppointmentStatus: string,
                firstAppointmentDate: string,
                firstAppointmentTime: string,
                secondAppointmentStatus: string,
                secondAppointmentDate: string,
                secondAppointmentTime: string,
                appointmentLocationCity: string,
                appointmentLocationPostalCode: string,
                appointmentLocationAddress: string,
                appointmentLocationName: string,
                patientUUID: string,
                appointmentId: string) {
        this.patientReferralId = patientReferralId;
        this.vaccinationBrandt = vaccinationBrandt;
        this.firstAppointmentStatus = firstAppointmentStatus;
        this.firstAppointmentDate = firstAppointmentDate;
        this.firstAppointmentTime = firstAppointmentTime;
        this.secondAppointmentStatus = secondAppointmentStatus;
        this.secondAppointmentDate = secondAppointmentDate;
        this.secondAppointmentTime = secondAppointmentTime;
        this.appointmentLocationCity = appointmentLocationCity;
        this.appointmentLocationPostalCode = appointmentLocationPostalCode;
        this.appointmentLocationAddress = appointmentLocationAddress;
        this.appointmentLocationName = appointmentLocationName;
        this.patientUUID = patientUUID;
        this.appointmentId = appointmentId;
    }
}
