import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { RegisterService } from '../../service/register/register.service';
import { Registration } from '../../model/Registration';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registerForm!: FormGroup;
  loading = false;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private registerService: RegisterService
    ) {
  }

  ngOnInit(): void {
      this.registerForm = this.formBuilder.group({
      pesel: ['', Validators.required, Validators.minLength(11), Validators.maxLength(11)],
      identificator: ['', Validators.required],
      zipcode: ['', Validators.required, Validators.pattern('[0-9]{2}-[0-9]{3}'), Validators.maxLength(6)],
      referral: ['', Validators.required],
      email: ['', Validators.required]
    });
  }

  onSubmit(): void {
    this.registerService.setRegisterForm(this.mapFormBuilderToRegistrationObject());
  }

  mapFormBuilderToRegistrationObject(): Registration {
    return new Registration(this.registerForm.getRawValue().pesel,
      this.registerForm.getRawValue().identificator,
      this.registerForm.getRawValue().zipcode,
      this.registerForm.getRawValue().referral,
      this.registerForm.getRawValue().email
    );
  }
}
