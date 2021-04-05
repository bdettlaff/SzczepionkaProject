import { Component, Input, OnInit } from '@angular/core';
import { RegisterService } from '../../service/register/register.service';
import { Marker } from '../../model/Marker';
import { GoogleMapService } from '../../service/google-map/google-map.service';
import { Coordinate } from '../../model/Coordinate';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {
  registerForm: any;
  code: string;
  address: string;
  center: Marker;
  coordinates: Coordinate[];
  polygon: Marker[];
  result: any;
  lat = 51.780060;
  lng = 19.449341;

  constructor(private registerService: RegisterService, private googleMapService: GoogleMapService) {
    this.code = '';
    this.address = '';
    this.center = new Marker(this.lat, this.lng);
    this.polygon = [];
    this.coordinates = [];
  }

  ngOnInit(): void {
    this.registerForm = this.registerService.getRegisterForm();
  }

  clickedMarker(label: string, index: number): any {
    console.log(`clicked the marker: ${label || index}`);
  }

  markerDragEnd(m: Marker, $event: MouseEvent): any {
    console.log('dragEnd', m, $event);
  }

  searchCode(): any {
    this.googleMapService.callGeoAPI(this.code).subscribe(res => {
      this.result = res;
      console.log(this.result);
    }, error => {
      console.log(error);

    }, () => {
      this.address = this.result.results[0].formatted_address;
      this.center = this.result.results[0].geometry.location;
      this.lat = this.center.lat;
      this.lng = this.center.lng;
      this.coordinates.push(
        new Coordinate(
          this.result.results[0].geometry.bounds.northeast.lat,
          this.result.results[0].geometry.bounds.northeast.lng,
        ),
        new Coordinate(
          this.result.results[0].geometry.bounds.southwest.lat,
          this.result.results[0].geometry.bounds.southwest.lng,
        ));

      this.polygon = [
        {
          lat: this.result.results[0].geometry.bounds.northeast.lat,
          lng: this.result.results[0].geometry.bounds.northeast.lng
        },
        {
          lat: this.result.results[0].geometry.bounds.northeast.lat,
          lng: this.result.results[0].geometry.bounds.southwest.lng
        },
        {
          lat: this.result.results[0].geometry.bounds.southwest.lat,
          lng: this.result.results[0].geometry.bounds.southwest.lng
        },
        {
          lat: this.result.results[0].geometry.bounds.southwest.lat,
          lng: this.result.results[0].geometry.bounds.northeast.lng
        },
      ];
      this.sendCoordinates();
    });

  }

  sendCoordinates(): any {
    this.googleMapService.sendCoordinates(this.coordinates).subscribe(
      () => {
        console.log('Coordinates passed to service.');
      }
    );
  }
}
