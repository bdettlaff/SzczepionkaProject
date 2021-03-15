import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Registration } from '../../model/Registration';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  constructor(private router: Router) { }

  registerForm: any;

  setRegisterForm(registerForm: Registration): void{
    this.registerForm = registerForm;
    this.router.navigate(['/map']);
  }

  getRegisterForm(): Registration {
    return this.registerForm;
  }

}
