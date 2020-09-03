import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {environment} from 'src/environments/environment.prod'

const URL = environment.url;

@Injectable({
  providedIn: 'root'
})
export class DataService {

  constructor(private httpClient: HttpClient) {}

  public countryData(){
    return this.httpClient.get(`${URL}/api/countries`);
  }
  public gendersData(){
    return this.httpClient.get(`${URL}/api/genders`);
  }
  public doctypesData(){
    return this.httpClient.get(`${URL}/api/doctypes`);
  }
  public networksData(){
    return this.httpClient.get(`${URL}/api/networks`);
  }

}
