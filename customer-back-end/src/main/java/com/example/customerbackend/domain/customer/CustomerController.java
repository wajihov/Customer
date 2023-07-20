package com.example.customerbackend.domain.customer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("")
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto) {
        CustomerDto dto = customerService.createCustomer(customerDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) throws Exception {
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> findCustomerById(@PathVariable Long id) throws Exception {
        CustomerDto customerDto = customerService.findCustomerById(id);
        return new ResponseEntity<>(customerDto, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<CustomerDto>> findCustomers() {
        List<CustomerDto> dtoList = customerService.customerDtoList();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable Long id, @RequestBody CustomerDto customerDto) {
        CustomerDto dto = customerService.updateCustomer(id, customerDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
