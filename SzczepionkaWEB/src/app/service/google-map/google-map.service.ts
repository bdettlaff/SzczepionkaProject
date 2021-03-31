import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class GoogleMapService {

  constructor(private http: HttpClient) {
  }

  CallGeoAPI(code: number): Observable<any> {
    const apiURL = `https://maps.googleapis.com/maps/api/geocode/json?address=${code}&key=AIzaSyAgDUII_kvGfCJNmu4qhhzjl8YNzblV9Ng`;
    return this.http.get(apiURL);
  }
}
