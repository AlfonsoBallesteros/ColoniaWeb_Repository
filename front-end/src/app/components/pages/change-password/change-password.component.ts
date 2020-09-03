import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import Swal from 'sweetalert2';
import { FormGroup, FormControl, Validators } from '@angular/forms';
@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {
  user: any = new Object();
  passwordForm: FormGroup;
  token: string;
  constructor(private router: ActivatedRoute, private data: AuthService, private _router: Router) {
    this.router.queryParams.subscribe(params => {
      this.token = params['token'];
    });
    console.log(this.token)
  }

  ngOnInit(): void {

    Swal.fire({
      allowOutsideClick: false,
      icon: 'info',
      text: 'Please wait...'
    });
    Swal.showLoading();
    if (this.token == null) {
      Swal.close();
      Swal.fire({
        allowOutsideClick: false,
        icon: 'error',
        title: 'Error',
        text: 'No token!'
      }).then((result) => {
        if (result.value) {
          this._router.navigate(['/signin'])
        }
      })
    }
    this.data.verifyPasswordToken(this.token).subscribe((resp: any) => {
      Swal.close();
      Swal.fire({
        allowOutsideClick: false,
        icon: 'success',
        title: 'Success',
        text: `${resp.message}`
      })
      this.user = resp.user;
    }, (err: any) => {
      Swal.close();
      Swal.fire({
        allowOutsideClick: false,
        icon: 'error',
        title: 'Error',
        text: `${err.error.message}`
      }).then((result) => {
        if (result.value) {
          this._router.navigate(['/signin'])
        }
      })
    })

    this.passwordForm = new FormGroup({
      'password': new FormControl('', [Validators.required, Validators.minLength(5), Validators.maxLength(50)]),
      'password2': new FormControl('', [Validators.required, Validators.minLength(5), Validators.maxLength(50)])
    })

  }

  onSubmit() {
    if (this.passwordForm.value.password != this.passwordForm.value.password2) {
      Swal.fire({
        allowOutsideClick: false,
        icon: 'error',
        title: 'Error',
        text: 'Password does not match'
      }).then((result) => {
        if (result.value) {
          this.passwordForm.reset();
        }
      })
    } else {
      this.user.password = this.passwordForm.value.password;
      Swal.fire({
        allowOutsideClick: false,
        icon: 'info',
        text: 'Please wait...'
      });
      Swal.showLoading();
      this.data.changePassword(this.user).subscribe((resp:any) => {
        Swal.close();
        Swal.fire({
          allowOutsideClick: false,
          icon: 'success',
          title: 'Success',
          text: `${resp.message}`
        }).then((result) => {
          if (result.value) {
            this._router.navigate(['/signin'])
          }
        })
      }, (err) => {
        Swal.close();
        Swal.fire({
          allowOutsideClick: false,
          icon: 'error',
          title: 'Error',
          text: `${err.error.message}`
        }).then((result) => {
          if (result.value) {
            this.passwordForm.reset();
          }
        })
      })
    }

  }
}
