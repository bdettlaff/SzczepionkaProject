import { Component, Input, OnInit } from '@angular/core';
import { RegisterService } from '../../service/register/register.service';
import { Marker } from '../../model/Marker';
import { GoogleMapService } from '../../service/google-map/google-map.service';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {
  registerForm: any;
  code: number;
  address: string;
  center: Marker;
  polygon: Marker[];
  zoom = 14;
  result: any;

  constructor(private registerService: RegisterService, private googleMapService: GoogleMapService) {
    this.code = 0;
    this.address = '';
    this.center = new Marker(this.lat, this.lng);
    this.polygon = [];
  }

  // initial center position for the map
  lat = 51.780060;
  lng = 19.449341;

  ngOnInit(): void {
    this.registerForm = this.registerService.getRegisterForm();
  }


  // tslint:disable-next-line:typedef
  clickedMarker(label: string, index: number) {
    console.log(`clicked the marker: ${label || index}`);
  }

  // tslint:disable-next-line:typedef
  markerDragEnd(m: Marker, $event: MouseEvent) {
    console.log('dragEnd', m, $event);
  }

  // tslint:disable-next-line:typedef
  searchCode() {
    this.googleMapService.CallGeoAPI(this.code).subscribe(res => {
      this.result = res;
      console.log(this.result);
    }, error => {
      console.log(error);

    }, () => {
      console.log(this.result.results[0]);
      this.address = this.result.results[0].formatted_address;
      this.center = this.result.results[0].geometry.location;
      this.lat = this.center.lat;
      this.lng = this.center.lng;
      console.log(this.lat);
      console.log(this.lng);
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
    });

  }
}
