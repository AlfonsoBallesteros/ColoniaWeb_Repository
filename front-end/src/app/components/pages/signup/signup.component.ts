import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router'
import { FormGroup, FormControl, Validators, FormArray } from '@angular/forms';
import { AuthService } from 'src/app/services/auth.service';
import { DataService } from 'src/app/services/data.service';
import { FirebaseStorageService } from 'src/app/services/firebase-storage.service';
import { Usuario, Countrie, Doctype, Gender } from 'src/app/models/usuario.model';
import Swal from 'sweetalert2';
import { Observable } from 'rxjs';
//import { AngularFireStorage } from '@angular/fire/storage';
import { finalize } from 'rxjs/operators';
@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {


  
  hoy = new Date().getFullYear();
  countries = [];
  doctypes = [];
  genders = [];
  registerForm: FormGroup;
  usuario: Usuario;
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

  urlImage: Observable<String>;
  show_photo: boolean = false;
  new_image: string = "https://firebasestorage.googleapis.com/v0/b/coloniaweb-78b3d.appspot.com/o/uploads%2Fusers1.jpg?alt=media&token=51a824c9-dacd-4814-82bb-40e8e225fffd";
  porcentaje = 0;
  count_err = 1
  count_succ = 1
  count_upload = 1
  referencia: any;

  constructor(private auth: AuthService, private router: Router, private firebaseStorage: FirebaseStorageService, private dataService: DataService) { }
  ngOnInit() {

    this.dataService.countryData().subscribe((data: Countrie) => {
      this.countries = data.countries;
    })
    this.dataService.doctypesData().subscribe((data: Doctype) => {
      this.doctypes = data.doctypes;
    })
    this.dataService.gendersData().subscribe((data: Gender) => {
      this.genders = data.genders;
    })
    this.usuario = new Usuario();
    this.registerForm = new FormGroup({
      'avatar': new FormControl(''),
      'username': new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(30)]),
      'name': new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]),
      'lastname': new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]),
      'email': new FormControl('', [Validators.required, Validators.minLength(4), Validators.maxLength(100)]),
      'birthdate': new FormControl('', [Validators.required]),
      'country': new FormGroup({
        'id': new FormControl('', [Validators.required])
      }),
      'docType': new FormGroup({
        'id': new FormControl('', [Validators.required])
      }),
      'docId': new FormControl('', [Validators.required, Validators.minLength(5), Validators.maxLength(30)]),
      'cellphone': new FormControl('', [Validators.required, Validators.minLength(5), Validators.maxLength(50)]),
      'ocupation': new FormControl('', [Validators.required, Validators.minLength(5), Validators.maxLength(50)]),
      'password': new FormControl('', [Validators.required, Validators.minLength(5), Validators.maxLength(50)]),
      'password2': new FormControl('', [Validators.required]),
      'gender': new FormGroup({
        'id': new FormControl('', [Validators.required])
      }),
      'roles': new FormArray([new FormGroup({
        'id': new FormControl('2')
      })
      ])
    })

  }

  onUpload(e) {
    if (this.count_upload <= 1) {
      const id = Math.random().toString(36).substring(2);
      const file = e.target.files[0];
      const size = (e.target.files[0].size) / 1024;
      const tipe = e.target.files[0].type;
      const base64 = btoa(file);
      console.log(base64, tipe, Date.now())
      const metadata = {
        contentType: tipe,
        size: size,
        md5Hash: base64,
        name: `${Date.now()}profile_${id}${base64}`
      }

      const filepath = `uploads/profile_${id}`;
      const referencia = this.firebaseStorage.referenciaCloudStorage(filepath);
      let tarea = this.firebaseStorage.tareaCloudStorage(filepath, file, metadata);
      Swal.fire({
        allowOutsideClick: false,
        icon: 'info',
        text: 'Please wait...'
      });
      Swal.showLoading();
      tarea.snapshotChanges().pipe(finalize(() => this.urlImage = referencia.getDownloadURL())).subscribe((url) => {
        this.referencia = url.ref;
        tarea.percentageChanges().subscribe((porcentaje) => {
          this.porcentaje = Math.round(porcentaje);
          if (this.porcentaje == 100) {
            this.show_photo = true;
            url.ref.getDownloadURL().then(URL => {
              this.new_image = URL;
              this.registerForm.patchValue({
                'avatar': URL
              });
              if (this.count_succ <= 1) {
                Swal.fire({
                  icon: 'success',
                  title: 'Image Upload',
                  text: 'Image uploaded successfully'
                });
                this.count_succ = this.count_succ + 1;
                console.log(this.new_image);
              }
            }).catch(err => {
              if (this.count_err <= 1) {
                Swal.fire({
                  icon: 'info',
                  title: 'Please wait...',
                  text: `Wait for the image to load, ${err.code}`,
                }).then(suc => {
                  this.getExecptionDowloand();
                });
                this.count_err = this.count_err + 1;
                console.log(err)
              }
            });
          }
        });
      });
      this.count_upload = this.count_upload + 1;
    } else {
      Swal.fire({
        icon: 'warning',
        title: 'Image upload',
        text: 'You can only upload one image'
      });
    }
  }

  getExecptionDowloand() {
    if (this.new_image == 'https://firebasestorage.googleapis.com/v0/b/coloniaweb-78b3d.appspot.com/o/uploads%2Fusers1.jpg?alt=media&token=51a824c9-dacd-4814-82bb-40e8e225fffd') {
      this.referencia.getDownloadURL().then(url => {
        this.new_image = url;
        this.registerForm.patchValue({
          avatar: this.new_image
        });
        console.log(this.new_image)
      }).catch(err => {
        console.log(err)
      });
    }
  }

  async onSubmit() {
    Swal.fire({
      allowOutsideClick: false,
      icon: 'info',
      text: 'Please wait...'
    });
    Swal.showLoading();

    let dateForm = Number(this.hoy) - 13;
    let date = this.registerForm.value.birthdate;
    date = Number(date.substr(0, 4))

    if (this.registerForm.value.password != this.registerForm.value.password2) {
      return Swal.fire({
        allowOutsideClick: false,
        icon: 'error',
        title: 'Error',
        text: "Password does not match"
      });

    } else if (!this.show_photo) {
      return Swal.fire({
        allowOutsideClick: false,
        icon: 'error',
        title: 'Error',
        text: 'Mandatory image'
      });

    } else if (date > dateForm) {
      return Swal.fire({
        allowOutsideClick: false,
        icon: 'error',
        title: 'Error',
        text: 'Only over 13 years old'
      });
    } else {

      this.usuario = this.registerForm.value;

      (await this.auth.loginup(this.usuario)).subscribe(resp => {

        Swal.close();
        Swal.fire({
          allowOutsideClick: false,
          icon: 'success',
          title: 'success',
          text: 'User created'
        }).then((result) => {
          if (result.value) {
            this.router.navigate(['confirm-mail', this.registerForm.value.username])
          }
        })
      }, (err) => {
        Swal.close();
        Swal.fire({
          allowOutsideClick: false,
          icon: 'error',
          title: 'Error',
          text: `${err.error.response.description}`
        });

      });
      

    }

  }

}
