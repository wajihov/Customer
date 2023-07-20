package com.example.customerbackend.domain.customer;

import com.example.customerbackend.core.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
class CustomerControllerTest {

    @MockBean
    private CustomerService customerService;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MockMvc mockMvc;

    @Test
    void GIVEN_customer_WHEN_customer_created_customerDto_must_be_registered_into_database() throws Exception {
        //GIVEN
        CustomerDto customerDto = CustomerDto.builder()
                .fullName("Jean Francois")
                .address("Rue Alain Savare")
                .mobile("9747125")
                .build();
        CustomerDto customerDtoCreated = CustomerDto.builder()
                .id(1L)
                .fullName(customerDto.getFullName())
                .address(customerDto.getAddress())
                .mobile(customerDto.getMobile())
                .build();

        Mockito.when(customerService.createCustomer(Mockito.any())).thenReturn(customerDtoCreated);
        //WHEN && THEN
        mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(customerDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string(JsonUtils.asJsonString(customerDtoCreated)))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.fullName").value("Jean Francois"))
                .andExpect(jsonPath("$.address").value("Rue Alain Savare"))
                .andExpect(jsonPath("$.mobile").value("9747125"));


    }

    @Test
    void GIVEN_customerId_WHEN_delete_THEN_should_delete_customer_from_database() throws Exception {
        //GIVEN
        Mockito.doNothing().when(customerService).deleteCustomer(Mockito.anyLong());
        //WHEN && THEN
        mockMvc.perform(delete("/api/v1/customers/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customerService, times(1)).deleteCustomer(1L);
    }

    @Test
    void GIVEN_customerId_WHEN_getCustomerById_THEN_should_return_customerDto_from_database() throws Exception {
        //GIVEN
        Long customerId = 5L;
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customerId);
        customerDto.setFullName("Francisco Korea");
        customerDto.setAddress("Street Washington New York USA");
        customerDto.setMobile("9855224411");
        Mockito.when(customerService.findCustomerById(Mockito.anyLong())).thenReturn(customerDto);

        //WHEN && THEN
        mockMvc.perform(get("/api/v1/customers/{id}", customerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(customerId))
                .andExpect(jsonPath("$.fullName").value("Francisco Korea"))
                .andExpect(jsonPath("$.address").value("Street Washington New York USA"))
                .andExpect(jsonPath("$.mobile").value("9855224411"));
    }

    @Test
    void GIVEN_all_customer_WHEN_getAllCustomer_THEN_should_return_listCustomerDto_from_database() throws Exception {
        //GIVEN
        CustomerDto firstCustomerDto = new CustomerDto();
        firstCustomerDto.setId(8L);
        firstCustomerDto.setFullName("John HANDSOME");
        firstCustomerDto.setAddress("Street Wolters London");
        firstCustomerDto.setMobile("8855221144");

        CustomerDto secondCustomerDto = new CustomerDto();
        secondCustomerDto.setId(6L);
        secondCustomerDto.setFullName("Fransisco Alma");
        secondCustomerDto.setAddress("Street fransisco alma London");
        secondCustomerDto.setMobile("7744112255");

        List<CustomerDto> customerDtoList = new ArrayList<>() {
            {
                add(firstCustomerDto);
                add(secondCustomerDto);
            }
        };

        Mockito.when(customerService.customerDtoList()).thenReturn(customerDtoList);
        //WHEN && THEN
        mockMvc.perform(get("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(8L))
                .andExpect(jsonPath("$[0].fullName").value("John HANDSOME"))
                .andExpect(jsonPath("$[0].address").value("Street Wolters London"))
                .andExpect(jsonPath("$[0].mobile").value("8855221144"))
                .andExpect(jsonPath("$[1].id").value(6L))
                .andExpect(jsonPath("$[1].fullName").value("Fransisco Alma"))
                .andExpect(jsonPath("$[1].address").value("Street fransisco alma London"))
                .andExpect(jsonPath("$[1].mobile").value("7744112255"))
                .andExpect(content().string(JsonUtils.asJsonString(customerDtoList)));

    }

    @Test
    void GIVEN_customerDto_WHEN_updateCustomer_THEN_should_return_customerDto_updated_into_database() throws Exception {
        //GIVEN
        Long customerId = 2L;
        CustomerDto customerDto = new CustomerDto();
        customerDto.setFullName("John Francisco");
        customerDto.setAddress("Street fransisco alma London");
        customerDto.setMobile("7410852369");

        CustomerDto customerDtoUpdated = new CustomerDto();
        customerDtoUpdated.setId(customerId);
        customerDtoUpdated.setFullName("David Beckham");
        customerDtoUpdated.setAddress("Street david Beckham Manchester");
        customerDtoUpdated.setMobile("9638527410");

        Mockito.when(customerService.updateCustomer(Mockito.anyLong(), Mockito.any())).thenReturn(customerDtoUpdated);
        //WHEN
        mockMvc.perform(put("/api/v1/customers/{id}", customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(customerDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(customerId))
                .andExpect(jsonPath("$.fullName").value("David Beckham"))
                .andExpect(jsonPath("$.address").value("Street david Beckham Manchester"))
                .andExpect(jsonPath("$.mobile").value("9638527410"))
                .andExpect(content().string(JsonUtils.asJsonString(customerDtoUpdated)));
        //THEN
        verify(customerService, times(1)).updateCustomer(Mockito.anyLong(), Mockito.any());
    }
}

