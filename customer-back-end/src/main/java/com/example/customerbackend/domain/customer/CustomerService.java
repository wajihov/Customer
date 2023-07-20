package com.example.customerbackend.domain.customer;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CustomerService {

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    public CustomerDto createCustomer(CustomerDto customerDto) {
        Customer customer = customerMapper.toEntity(customerDto);
        log.info("Customer = {} ", customer);
        customer = customerRepository.save(customer);
        log.info("the customer is added successfully {}", customerDto.getFullName());
        return customerMapper.toDto(customer);
    }

    public CustomerDto findCustomerById(Long id) throws Exception {
        Customer customer = searchCustomerById(id);
        CustomerDto customerDto = customerMapper.toDto(customer);
        log.info("the customer searched is {}", customerDto.getFullName());
        return customerDto;
    }

    private Customer searchCustomerById(Long id) throws Exception {
        return customerRepository.findById(id).orElseThrow(() ->
                new Exception("Customer not Found"));
    }

    public List<CustomerDto> customerDtoList() {
        List<Customer> customers = customerRepository.findAll();
        log.info("list {} Customers", customers.size());
        return customerMapper.toDtoList(customers);
    }

    public void deleteCustomer(Long id) throws Exception {
        Customer customer = searchCustomerById(id);
        customerRepository.delete(customer);
        log.info("The deletion of the customer name {} is successful", customer.getFullName());
    }

    public CustomerDto updateCustomer(Long id, CustomerDto customerDto) {
        Customer customer = customerMapper.toEntity(customerDto);
        customer.setId(id);
        customer = customerRepository.save(customer);
        log.info("the customer with id {} has been successfully modified", customer.getFullName());
        return customerMapper.toDto(customer);
    }

}
