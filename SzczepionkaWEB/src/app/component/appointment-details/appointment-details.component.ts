import { Component, Inject, OnInit } from '@angular/core';
import { AppointmentDetailsService } from '../../service/appointment-details/appointment-details.service';
import { ActivatedRoute, } from '@angular/router';
import { DOCUMENT } from '@angular/common';
import { AppointmentDetails } from '../../model/AppointmentDetails';

@Component({
  selector: 'app-appointment-details',
  templateUrl: './appointment-details.component.html',
  styleUrls: ['./appointment-details.component.css']
})
export class AppointmentDetailsComponent implements OnInit {

  appointment!: AppointmentDetails;
  patientUUID: any;
  isSecondAppointmentExist = false;

  appointmentStatusMapping = new Map([
    ['PLANNED', 'ZAPLANOWANA'],
    ['CANCELLED', 'ODWOŁANA'],
    ['POSTPONED', 'PRZEŁOŻONA'],
    ['NOT_PLANNED', 'NIEZAPLANOWANA'],
    ['DONE', 'ZREALIZOWANA'],
  ]);

  constructor(private appointmentService: AppointmentDetailsService,
              private route: ActivatedRoute,
              @Inject(DOCUMENT) private document: Document) {
  }

  ngOnInit(): void {
    this.patientUUID = this.route.snapshot.paramMap.get('patientUUID');
    this.getAppointment();
  }

  getAppointment(): void {
    this.appointmentService.getAppointment(this.patientUUID).subscribe(
      data => {
        this.appointment = data;
        this.replaceStatusDescription();
      }, error => {
        console.log(error);
      }, () => {
        if (this.appointment.secondAppointmentDate){
          this.isSecondAppointmentExist = true;
        }
      });
  }

  private replaceStatusDescription(): void {
    for (const [key, value] of this.appointmentStatusMapping) {
      if (this.appointment.firstAppointmentStatus === key) {
        this.appointment.firstAppointmentStatus = value;
      }

      if (this.appointment.secondAppointmentStatus === key) {
        this.appointment.secondAppointmentStatus = value;
      }
    }
  }

  cancelAppointment(): void {
    if (confirm('Czy na pewno chcesz odwołać pierwszą wizytę?')) {
      if (this.appointment.secondAppointmentStatus) {
        alert('Nie możesz odwołać wizyty, gdy druga wizyta została już ustalona.');
      } else {
        this.appointmentService.cancelAppointment(1, this.appointment.appointmentId).subscribe(
          data => {
          }, error => {
            console.log(error);
          }, () => {
            window.location.reload();
          });
      }
    }
  }

  viewVaccinationDetails(): void {
    if (this.appointment.vaccinationBrandt === 'ASTRA_ZENECA') {
      this.document.location.href = 'https://ec.europa.eu/health/documents/community-register/2021/20210129150842/anx_150842_pl.pdf';
    } else if (this.appointment.vaccinationBrandt === 'PFIZER') {
      this.document.location.href = 'http://zozmswia.gda.pl/wp-content/uploads/Comirnaty-PIL-Poland_21_01_08.pdf';
    } else if (this.appointment.vaccinationBrandt === 'MODERNA') {
      this.document.location.href = 'https://ec.europa.eu/health/documents/community-register/2021/20210106150575/anx_150575_pl.pdf';
    }
  }

  secondAppointment(): void {
    this.appointmentService.newSecondAppointment(this.appointment.appointmentId).subscribe(
      data => {
      }, error => {
        console.log(error);
      }, () => {
        this.getAppointment();
        window.location.reload();
      });
  }

  cancelSecondAppointment(): void {
    if (confirm('Czy na pewno chcesz odwołać drugą wizytę?')) {
      this.appointmentService.cancelAppointment(2, this.appointment.appointmentId).subscribe(
        data => {
        }, error => {
          console.log(error);
        }, () => {
          window.location.reload();
        });
    }

  }

}
