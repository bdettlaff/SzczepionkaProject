import { Component, OnInit } from '@angular/core';
import { AppointmentDetailsService } from '../../service/appointment-details/appointment-details.service';
import { ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-appointment-details',
  templateUrl: './appointment-details.component.html',
  styleUrls: ['./appointment-details.component.css']
})
export class AppointmentDetailsComponent implements OnInit {

  appointment: any;
  patientUUID: any;

  constructor(private appointmentService: AppointmentDetailsService, private route: ActivatedRoute) {

  }

  ngOnInit(): void {
    this.patientUUID = this.route.snapshot.paramMap.get('patientUUID');
    this.getAppointment();
  }

  getAppointment(): void {
    this.appointmentService.getAppointment(this.patientUUID).subscribe(
      data => {
        this.appointment = data;
        console.log(this.appointment);
      }, error => {
        console.log(error);
      }, () => {
      });
  }

  cancelAppointment(): void {
  }

  viewVaccinationDetails(): void {

  }

}
