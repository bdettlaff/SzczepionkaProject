import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { RegisterService } from '../../service/register/register.service';
import { Registration } from '../../model/Registration';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registerForm: any;
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
      pesel: [null, Validators.required, Validators.minLength(11), Validators.maxLength(11)],
      identificator: [null, Validators.required],
      zipcode: [null, Validators.required, Validators.pattern('[0-9]{2}-[0-9]{3}')],
      referral: [null, Validators.required],
      email: [null, Validators.required, Validators.pattern('[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$')]
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
