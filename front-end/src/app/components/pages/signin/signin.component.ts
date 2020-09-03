import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Usuario } from 'src/app/models/usuario.model';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent implements OnInit {


  Toast = Swal.mixin({
    toast: true,
    position: 'top-end',
    showConfirmButton: false,
    timer: 3000,
    timerProgressBar: true,
    onOpen: (toast) => {
      toast.addEventListener('mouseenter', Swal.stopTimer)
      toast.addEventListener('mouseleave', Swal.resumeTimer)
    }
  })




  usuario: Usuario
  loginForm: FormGroup;
  constructor(private auth: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      'username': new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(30)]),
      'password': new FormControl('', [Validators.required, Validators.minLength(5), Validators.maxLength(50)])
    })
  }



  fieldTextType: boolean;



  toggleFieldTextType() {
    this.fieldTextType = !this.fieldTextType;
  }


  async onSubmit() {

    Swal.fire({
      allowOutsideClick: false,
      icon: 'info',
      text: 'Please wait...'
    });
    Swal.showLoading();

    this.usuario = this.loginForm.value;
    (await this.auth.loginin(this.usuario)).subscribe(resp => {
      Swal.close();
      this.Toast.fire({
        icon: 'success',
        title: 'Signed in successfully'
      });
      this.router.navigate(['/profile', this.loginForm.value.username])
    }, (err) => {
      console.log(err)
      if (err.error.message == "User not verified") {
        Swal.close();
        Swal.fire({
          allowOutsideClick: false,
          icon: 'error',
          title: `${err.error.status}`,
          text: `${err.error.message}`
        }).then((result) => {
          if (result.value) {
            this.router.navigate(['confirm-mail', this.loginForm.value.username])
          }
        });


      } else {

        Swal.fire({
          allowOutsideClick: false,
          icon: 'error',
          title: `${err.error.status}`,
          text: `${err.error.message}`
        });
      }
    });
  }
}
