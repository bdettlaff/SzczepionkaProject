export class AppointmentDetails {

    patientReferralId: string;
    vaccinationBrandt: string;
    appointmentStatus: string;
    appointmentDate: string;
    appointmentTime: string;
    appointmentLocationCity: string;
    appointmentLocationPostalCode: string;
    appointmentLocationAddress: string;
    appointmentLocationName: string;
    patientUUID: string;
    appointmentId: string;

    constructor(patientReferralId: string,
                vaccinationBrandt: string,
                appointmentStatus: string,
                appointmentDate: string,
                appointmentTime: string,
                appointmentLocationCity: string,
                appointmentLocationPostalCode: string,
                appointmentLocationAddress: string,
                appointmentLocationName: string,
                patientUUID: string,
                appointmentId: string) {
        this.patientReferralId = patientReferralId;
        this.vaccinationBrandt = vaccinationBrandt;
        this.appointmentStatus = appointmentStatus;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.appointmentLocationCity = appointmentLocationCity;
        this.appointmentLocationPostalCode = appointmentLocationPostalCode;
        this.appointmentLocationAddress = appointmentLocationAddress;
        this.appointmentLocationName = appointmentLocationName;
        this.patientUUID = patientUUID;
        this.appointmentId = appointmentId;
    }
}
