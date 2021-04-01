import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Coordinate } from '../../model/Coordinate';

@Injectable({
  providedIn: 'root'
})
export class GoogleMapService {

  constructor(private http: HttpClient) {
  }

  callGeoAPI(code: string): Observable<any> {
    const apiURL = `https://maps.googleapis.com/maps/api/geocode/json?address=${code}&key=#`;
    return this.http.get(apiURL);
  }

  // sendCoordinates(coordinates: Coordinate[]): Observable<any>{
  sendCoordinates(coordinates: Coordinate[]): any {
    console.log(coordinates);
    return coordinates;
  }
}
