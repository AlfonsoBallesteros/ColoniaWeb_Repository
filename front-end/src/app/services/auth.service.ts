import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Usuario } from '../models/usuario.model';
import { map } from 'rxjs/operators';
import {environment} from 'src/environments/environment.prod'

const URL = environment.url;


@Injectable({
  providedIn: 'root'
})
export class AuthService {

 
  urlUp: string = `${URL}/api/usuarios`;
  urlchangePassword = `${URL}/api/reset-password-confirm`;
  urlconfCorreo: string = `${URL}/api/confirm-account-email`;
  urlIn: string = `${URL}/api/auth`;
  userData:string = `${URL}/api/usuarios/username`
  urlconfirmed:string = `${URL}/api/confirm-account`
  userToken: string;
  userName: string;
  urlresetPasswordEmail:string = `${URL}/api/reset-password-email`;
  constructor(private http: HttpClient) {
    this.readToken();
  }

  logout() {
    localStorage.removeItem('jwt');
    localStorage.removeItem('expira');
 

    this.userToken='';

  }

  async loginup(usuario: Usuario) {

    return await this.http.post(
      this.urlUp,
      usuario
    ).pipe(
      map(resp => {
        
        return resp;
      })
    );
  }

  async confCorreo(usuario: any) {
    let {email, password} = usuario
    let user = {email, password}
    console.log(user)
    return await this.http.post(
      this.urlconfCorreo,
      user
    ).pipe(
      map(resp => {
        
        return resp;
      })
    );
  }

  resetPasswordEmail(email:string) {
    return this.http.post(
      this.urlresetPasswordEmail,
      email
    ).pipe(
      map(resp => {
        return resp;
      })
    );
  }

  verifyPasswordToken(token:string){
    return this.http.get(
      `${URL}/api/confirm-token-reset?token=${token}`
    ).pipe(
      map(resp => {
        return resp;
      })
    );
  }

  changePassword(user){
    return this.http.post(
      this.urlchangePassword,
      user
    ).pipe(
      map(resp => {
        return resp;
      })
    );
  }



  async confirmedEmail(token:string){
    return await this.http.get(
      `${this.urlconfirmed}?token=${token}`
    ).pipe(
      map(resp => {
        return resp;
      })
    );
  }



  async loginin(usuario: any) {
    
    return await this.http.post(
      this.urlIn,
      usuario
    ).pipe(
      map(resp => {
        let token:any = resp;
        this.saveToken(token.token.jwt);
        return resp;
      })
    );
  }


  private saveToken(idToken: string) {
    this.userToken = idToken;
    localStorage.setItem('jwt', idToken);

    let hoy = new Date();
    hoy.setSeconds(3600);
    localStorage.setItem('expira', hoy.getTime().toString());

  }

  readToken() {
    if (localStorage.getItem('jwt')) {
      this.userToken = localStorage.getItem('jwt');
    } else {
      this.userToken = '';
    }
    return this.userToken;
  }

  auth(): boolean {

    if(this.userToken.length < 2){
      return false
    }
    const expira = Number(localStorage.getItem('expira'));
    const expireDate = new Date();
    expireDate.setTime(expira);
    if(expireDate > new Date()){
      return true;
    } else{
      return false;
    }
  }
  

  public getDataUser(username){
    return this.http.get(
      `${this.userData}/${username}`
    )
  }

  


}
