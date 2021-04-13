import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AppointmentDetails } from '../../model/AppointmentDetails';
import {Registration} from "../../model/Registration";

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
export class GoogleMapService {

  constructor(private http: HttpClient) {
  }

  sendPostCode(postCode: string): Observable<any>{
    return this.http.get<AppointmentDetails>('http://localhost:8080//location/' + postCode, httpOptions);
  }

  makeAppointment(registrationForm: Registration, locationId: number) {
    return this.http.post<Registration>('http://localhost:8080/appointment/' + locationId, JSON.stringify(registrationForm), httpOptions);
  }
}
