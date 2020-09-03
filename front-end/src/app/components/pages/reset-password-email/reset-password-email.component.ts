import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import Swal from 'sweetalert2';
import { Router } from '@angular/router'
import { AuthService } from 'src/app/services/auth.service';
@Component({
  selector: 'app-reset-password-email',
  templateUrl: './reset-password-email.component.html',
  styleUrls: ['./reset-password-email.component.css']
})
export class ResetPasswordEmailComponent implements OnInit {

  constructor(private data: AuthService, private router: Router) { }

  EmailForm: FormGroup;
  ngOnInit(): void {
    this.EmailForm = new FormGroup({
      'email': new FormControl('', [Validators.required, Validators.minLength(4), Validators.maxLength(100)])
    })
  }

  onSubmit(){
    Swal.fire({
      allowOutsideClick: false,
      icon: 'info',
      text: 'Please wait...'
    });
    Swal.showLoading();
    this.data.resetPasswordEmail(this.EmailForm.value).subscribe((resp:any)=>{
      Swal.close();
      Swal.fire({
        allowOutsideClick: false,
        icon: 'success',
        title: 'Success',
        text: `${resp.message}`
      }).then((result) => {
        if (result.value) {
          this.router.navigate(['/signin'])
        }
      })
    }, (err:any)=>{
      Swal.close();
      Swal.fire({
        allowOutsideClick: false,
        icon: 'error',
        title: `${err.error.status}`,
        text: `${err.error.message}`
      }).then((result) => {
        if (result.value) {
          this.EmailForm.reset();
        }
      })
    })
  }

}
