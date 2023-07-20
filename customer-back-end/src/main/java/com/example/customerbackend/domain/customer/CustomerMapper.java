package com.example.customerbackend.domain.customer;

import com.example.customerbackend.core.utils.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerMapper {

    public CustomerDto toDto(Customer customer) {
        if (customer == null) {
            return null;
        }
        return CustomerDto.builder()
                .id(customer.getId())
                .fullName(customer.getFullName())
                .mobile(customer.getMobile())
                .address(customer.getAddress())
                .build();
    }

    public Customer toEntity(CustomerDto customerDto) {
        if (customerDto == null) {
            return null;
        }
        return Customer.builder()
                .id(customerDto.getId())
                .fullName(customerDto.getFullName())
                .address(customerDto.getAddress())
                .mobile(customerDto.getMobile())
                .build();
    }

    public List<CustomerDto> toDtoList(List<Customer> customers) {
        if (CollectionUtils.isNullOrEmpty(customers)) {
            return null;
        }
        return customers.stream().map(this::toDto).collect(Collectors.toList());
    }

}
