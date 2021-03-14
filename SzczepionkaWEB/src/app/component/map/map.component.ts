import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder} from '@angular/forms';
import {RegisterService} from '../../service/register/register.service';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {

  constructor(private registerService: RegisterService) { }

  registerForm: any;

  ngOnInit(): void {
    this.registerForm = this.registerService.getRegisterForm();
    console.log(this.registerForm);
  }

}
