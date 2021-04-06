import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class GoogleMapService {

  constructor(private http: HttpClient) {
  }

  // sendCoordinates(coordinates: Coordinate[]): Observable<any>{
  sendPostCode(postCode: string): any {
    console.log(postCode);
    return postCode;
  }
}
