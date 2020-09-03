import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from 'src/app/services/auth.service';
import { ActivatedRoute} from '@angular/router';
@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  username:string;
  constructor(private auth: AuthService, private router: ActivatedRoute) { }
  user:any;
  opci:number=0;
  gInfForm: FormGroup;
  ngOnInit() {
    this.username=this.router.snapshot.paramMap.get('username')
    this.auth.getDataUser(this.username).subscribe((resp:any)=>{
      this.user=resp.user;
      console.log(this.user)
    });
    this.gInfForm = new FormGroup({
      'name': new FormControl('this.user.name', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]),
      'lastname': new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(50)])
    
    })
    
  }

  zona(opc:number){
    this.opci=opc;
    console.log(this.opci)
  }
  
  


}
