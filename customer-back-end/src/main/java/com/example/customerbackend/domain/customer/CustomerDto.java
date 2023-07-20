package com.example.customerbackend.domain.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CustomerDto {

    private Long id;
    private String fullName;
    private String address;
    private String mobile;
}
