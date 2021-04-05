import { Component, OnInit } from '@angular/core';
import { AppointmentDetailsService } from '../../service/appointment-details/appointment-details.service';
import { AppointmentDetails } from '../../model/AppointmentDetails';
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
    //53579dae-b861-4186-826a-dc534d66e44e UUID to test component
/*    this.appointment = new AppointmentDetails(
      'patientReferral-123',
      'Moderna',
      'PLANNED',
      '15-05-2021',
      '8:30',
      'Lodz',
      '12-345',
      'Streeeeeeet 11/223',
      'Przychodnia XYZ');*/
  }

  getAppointment(): void {
    this.appointmentService.getAppointment(this.patientUUID).subscribe(
      data => {
        this.appointment = data;

      }, error => {
        console.log(error);
      }, () => {
        console.log(this.appointment);
      });
  }

}
