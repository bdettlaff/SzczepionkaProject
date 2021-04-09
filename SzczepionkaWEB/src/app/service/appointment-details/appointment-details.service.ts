import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AppointmentDetails } from '../../model/AppointmentDetails';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Methods': 'OPTIONS, GET, PUT, PATCH, POST, DELETE',
    'Access-Control-Allow-Headers': 'Access-Control-Allow-Origin, Content-Type, Accept, Accept-Language, Origin, User-Agent'
  })
};

@Injectable({
  providedIn: 'root'
})
export class AppointmentDetailsService {

  constructor(private http: HttpClient) {
  }

  getAppointment(patientUUID: string): Observable<AppointmentDetails> {
    return this.http.get<AppointmentDetails>('http://localhost:8080/appointment/' + patientUUID, httpOptions);
  }
}
