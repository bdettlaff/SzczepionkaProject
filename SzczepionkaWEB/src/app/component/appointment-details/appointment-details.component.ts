import { Component, Inject, OnInit } from '@angular/core';
import { AppointmentDetailsService } from '../../service/appointment-details/appointment-details.service';
import { ActivatedRoute, } from '@angular/router';
import { DOCUMENT } from '@angular/common';
import {AppointmentDetails} from "../../model/AppointmentDetails";

@Component({
  selector: 'app-appointment-details',
  templateUrl: './appointment-details.component.html',
  styleUrls: ['./appointment-details.component.css']
})
export class AppointmentDetailsComponent implements OnInit {

  appointment: any;
  patientUUID: any;

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
      });
  }

  private replaceStatusDescription() {
    if(this.appointment.appointmentStatus == 'PLANNED') {
      this.appointment.appointmentStatus = 'ZAPLANOWANA';
    } else if (this.appointment.appointmentStatus == 'CANCELLED') {
      this.appointment.appointmentStatus = 'ODWOŁANA';
    } else if (this.appointment.appointmentStatus == 'POSTPONED') {
      this.appointment.appointmentStatus = 'PRZEŁOŻONA';
    } else if (this.appointment.appointmentStatus == 'NOT_PLANNED') {
      this.appointment.appointmentStatus = 'NIE ZAPLANOWANA';
    }
  }

  cancelAppointment(): void {
  }

  viewVaccinationDetails(): void {
    if (this.appointment.vaccinationBrandt === 'ASTRA_ZENECA') {
      this.document.location.href = 'https://www.astrazeneca.pl/content/dam/az-pl/ulotki-lekow/PIL_Vaxzevria_2021-03-23.pdf';
    } else if (this.appointment.vaccinationBrandt === 'PFIZER') {
      this.document.location.href = 'http://zozmswia.gda.pl/wp-content/uploads/Comirnaty-PIL-Poland_21_01_08.pdf';
    } else if (this.appointment.vaccinationBrandt === 'MODERNA') {
      this.document.location.href = 'https://ec.europa.eu/health/documents/community-register/2021/20210106150575/anx_150575_pl.pdf';
    }
  }

}
