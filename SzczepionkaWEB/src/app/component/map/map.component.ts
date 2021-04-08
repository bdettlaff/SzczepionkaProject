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

/*  resultMock: Location[] = [{
    id: 1,
    country: 'Poland',
    city: 'Lodz',
    postalCode: '91-053',
    address: 'Bazarowa 9',
    name: 'Centra Medyczne Medyceusz',
    numberOfAvailableVaccines: 5,
    vaccineName: 'Moderna'
  },
    {
      id: 2,
      country: 'Poland',
      city: 'Lodz',
      postalCode: '91-001',
      address: 'Drewnowska 58',a
      name: 'Centrum Medyczne Enel-Med',
      numberOfAvailableVaccines: 12,
      vaccineName: 'Pfizer'
    },
    {
      id: 3,
      country: 'Poland',
      city: 'Lodz',
      postalCode: '93-034',
      address: 'Milionowa 2G',
      name: 'Centrum Medyczne LUX MED',
      numberOfAvailableVaccines: 28,
      vaccineName: 'AstraZeneca'
    }];*/

  constructor(private registerService: RegisterService, private googleMapService: GoogleMapService) {
  }

  ngOnInit(): void {
    this.registerForm = this.registerService.getRegisterForm();
    this.sendPostCode();
  }

  sendPostCode(): any {
    this.googleMapService.sendPostCode(this.registerForm.zipcode).subscribe(
      data => {
        this.result = data;
        console.log(this.result);
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
