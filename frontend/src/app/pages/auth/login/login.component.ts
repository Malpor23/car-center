import { Component } from '@angular/core'
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from 'src/app/shared/services/auth/auth.service';

@Component({
  templateUrl: './login.component.html'
})

export class LoginComponent {

  loginForm: FormGroup;
  validateForm: boolean;

  constructor(
    private auth: AuthService,
    private router: Router,
    private toastr: ToastrService,
    private formBuilder: FormBuilder,
    
  ) {
  }

  ngOnInit(): void {
    this.initValidateForm();
    if (this.auth.isAuthenticated()) {
      this.toastr.info('Ya ha iniciado la sesión', 'Car Center', {
        timeOut: 3000, positionClass: 'toast-top-center'
      });
      this.router.navigate(['dashboard']);
    }
  }

  initValidateForm() {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  login(valid: boolean) {
    if (valid) {
      this.validateForm = true;

      setTimeout(() => {
        this.validateForm = false;
      }, 1000);
    }
    this.auth.login(this.loginForm.value.username, this.loginForm.value.password);
    this.loginForm.reset();
  }
  
}