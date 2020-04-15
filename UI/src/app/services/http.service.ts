import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})

export class HttpService {
  constructor(private http: HttpClient) {}

  get(serviceName: string, queryParam: string) {
    const headers = new HttpHeaders();
    headers.append('Access-Control-Allow-Origin', '*');
    headers.append('Access-Control-Request-Method', 'GET');
    const options = {headers, withCredentials: true};
    const url = environment.apiUrl + serviceName + queryParam;
    return this.http.get(url, options);
  }

  getWithoutParams(serviceName: string){
    const headers = new HttpHeaders();
    headers.append('Access-Control-Allow-Origin', '*');
    headers.append('Access-Control-Request-Method', 'GET');
    const options = {headers, withCredentials: true};
    const url = environment.apiUrl + serviceName;
    return this.http.get(url, options);
  }
  post(serviceName: string, data: any) {
    const headers = new HttpHeaders({ 'Content-Type': 'application/x-www-form-urlencoded' });
    headers.append('Access-Control-Allow-Origin', '*');
    headers.append('Access-Control-Request-Method', 'POST');
    const options = {headers, withCredentials: true};
    const url = environment.apiUrl + serviceName;
    return this.http.post(url, data, options);
  }
}
