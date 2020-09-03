import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-confirm-mail',
  templateUrl: './confirm-mail.component.html',
  styleUrls: ['./confirm-mail.component.css']
})
export class ConfirmMailComponent implements OnInit {
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
  username: string;
  user: any;
  constructor(private router: ActivatedRoute, private auth: AuthService, private _router: Router) { }

  ngOnInit(): void {
    this.username = this.router.snapshot.paramMap.get('username')
    this.auth.getDataUser(this.username).subscribe((resp: any) => {

      this.user = resp.user;
      console.log(this.user);

      if (this.user.verified == true) {
        Swal.close();
        Swal.fire({
          allowOutsideClick: false,
          icon: 'success',
          title: 'Success',
          text: 'User has already been confirmed'
        }).then((result) => {
          if (result.value) {
            this._router.navigate(['signin'])
          }
        });
      }


    }, (err) => {
      Swal.close();
      Swal.fire({
        allowOutsideClick: false,
        icon: 'error',
        title: `${err.error.status}`,
        text: `${err.error.message}`
      }).then((result) => {
        if (result.value) {
          this._router.navigate(['signin'])
        }
      });
    });

  }
  async correo() {
    Swal.fire({
      allowOutsideClick: false,
      icon: 'info',
      text: 'sending email...'
    });
    Swal.showLoading();
    (await this.auth.confCorreo(this.user)).subscribe((resp) => {
      Swal.close();
      Swal.fire({
        allowOutsideClick: false,
        icon: 'success',
        title: 'success',
        text: 'Email sent'
      }).then((result) => {
        if (result.value) {
          this._router.navigateByUrl('/signin');
        }
      })

    }, (err) => {
      Swal.close();
      Swal.fire({
        allowOutsideClick: false,
        icon: 'error',
        title: `${err.error.status}`,
        text: `${err.error.message}`
      });
    });
  }

}
