import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-confirmed-email',
  templateUrl: './confirmed-email.component.html',
  styleUrls: ['./confirmed-email.component.css']
})
export class ConfirmedEmailComponent implements OnInit {
  token: string;
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
  constructor(private router: ActivatedRoute, private auth: AuthService, private _router: Router) {
    this.router.queryParams.subscribe(params => {
      this.token = params['token'];
      console.log(this.token)
    });
  }

  async ngOnInit() {

    (await this.auth.confirmedEmail(this.token)).subscribe((resp: any) => {
      Swal.fire({
        allowOutsideClick: false,
        icon: 'success',
        title: 'Success',
        text: 'confirmed account'
      });

    }, (err) => {
      console.log(err)
      Swal.fire({
        allowOutsideClick: false,
        icon: 'error',
        title: err.error.status,
        text: err.error.message
      }).then((result) => {
        if (result.value) {
          this._router.navigate(['signin'])
        }
      });

    });
  }

}
