import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Customer } from '../Models/Customer.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CustomerServiceService {

  backendHost: string = "http://localhost:8080/api/v1/customers/";

  constructor(private http: HttpClient) { }

  public saveCustomer(customer: Customer): Observable<Customer> {
    console.log("Dans Create Customer : " + customer.fullName + " / " + customer.address);

    return this.http.post<Customer>(this.backendHost, customer);
  }

  public getCustomers(): Observable<Array<Customer>> {
    console.log("dans Service");

    return this.http.get<Array<Customer>>(this.backendHost);
  }

  public getCustomer(id: number): Observable<Customer> {
    return this.http.get<Customer>(this.backendHost + id);
  }

  public updateCustomer(id: number, customer: Customer): Observable<Customer> {
    return this.http.put<Customer>(this.backendHost + id, customer);
  }

  public deleteCustomer(id: number) {
    return this.http.delete(this.backendHost + id);
  }

}
