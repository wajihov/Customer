import { Component } from '@angular/core';
import { CustomerServiceService } from '../service/customer-service.service';
import { Customer } from '../Models/Customer.model';

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.css']
})
export class CustomerComponent {


  CustomerArray: any[] = [];
  /* isResultLoaded = false;
  isUpdateFormActive = false; */

  customerName: string = "";
  customerAddress: string = "";
  customerMobile: number = 0;

  currentCustomerId: number = 0;

  constructor(private serviceCustomer: CustomerServiceService) { }
  ngOnInit(): void {
    this.getAllCustomer();
  }

  getAllCustomer() {
    this.serviceCustomer.getCustomers().subscribe((resultData: any) => {
      console.log(resultData);
      this.CustomerArray = resultData;
    });
  }

  registerCustomer(customer: Customer) {
    if (this.customerName !== null && this.customerName !== '' && this.customerAddress !== null
      && this.customerAddress != '' && this.customerMobile !== null && this.customerMobile !== 0) {
      console.log("dans register " + customer.address + " / " + customer.fullName + " / " + customer.mobile);
      this.serviceCustomer.saveCustomer(customer).subscribe((resultData: any) => {
        console.log(resultData);
        alert("Customer Registered Successfully");
        this.getAllCustomer();
        this.customerName = '';
        this.customerAddress = '';
        this.customerMobile = 0;
      });
    } else {
      alert("Les valeur sont null ou vide")
    }
  }

  getCustomer(id: number) {
    this.serviceCustomer.getCustomer(id).subscribe((resultData: any) => {
      console.log(resultData);
      this.currentCustomerId = resultData.id;
      this.customerName = resultData.fullName;
      this.customerAddress = resultData.address;
      this.customerMobile = resultData.mobile;
    });
  }

  updateCustomer(id: number, customer: Customer) {
    this.serviceCustomer.updateCustomer(id, customer).subscribe((resultData: any) => {
      console.log(resultData);
      alert("Customer Registered Updated");
      this.getAllCustomer();
      this.customerName = '';
      this.customerAddress = '';
      this.customerMobile = 0;
      this.currentCustomerId = 0;
    });
  }

  deleteCustomer(id: number) {
    this.serviceCustomer.deleteCustomer(id).subscribe((resultData: any) => {
      console.log(resultData);
      alert("Customer Registered Deleted");
      this.getAllCustomer();
      this.customerName = '';
      this.customerAddress = '';
      this.customerMobile = 0;
    });
  }

  setUpdate(data: any) {
    this.customerName = data.fullName;
    this.customerAddress = data.address;
    this.customerMobile = data.mobile;
    this.currentCustomerId = data.id;
  }

  save() {
    const newCustomer: Customer = {
      id: this.currentCustomerId,
      fullName: this.customerName,
      address: this.customerAddress,
      mobile: this.customerMobile
    };

    if (this.currentCustomerId === 0) {
      this.registerCustomer(newCustomer);
    }
    else {
      this.updateCustomer(this.currentCustomerId, newCustomer);
    }

  }

}
