import { Component, Input, OnInit } from '@angular/core';
import { RegisterService } from '../../service/register/register.service';
import { GoogleMapService } from '../../service/google-map/google-map.service';
import { Location } from '../../model/Location';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {
  registerForm: any;
  result: Location[] = [];
  displayedColumns: string[] = ['country', 'city', 'postalCode', 'address', 'name', 'numberOfAvailableVaccines', 'vaccineName', 'selectLocation'];

  constructor(private registerService: RegisterService, private googleMapService: GoogleMapService) {
  }

  ngOnInit(): void {
    this.registerForm = this.registerService.getRegisterForm();
    if (this.registerForm) {
      this.sendPostCode();
    }
  }

  sendPostCode(): any {
    this.googleMapService.sendPostCode(this.registerForm.zipcode).subscribe(
      data => {
        this.result = data;
      },
      error => {
        console.log(error);
      },
      () => {
      }
    );
  }

  selectLocation(id: number): void {
    console.log(id);
  }

}
