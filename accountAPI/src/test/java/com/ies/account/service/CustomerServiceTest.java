package com.ies.account.service;

import com.ies.account.dto.CustomerDto;
import com.ies.account.dto.CustomerDtoConverter;
import com.ies.account.exception.CustomerNotFoundException;
import com.ies.account.model.Customer;
import com.ies.account.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.Set;

public class CustomerServiceTest {
    private CustomerService service;
    private CustomerRepository customerRepository;
    private CustomerDtoConverter converter;

    @BeforeEach
    public void setUp(){
        customerRepository = Mockito.mock(CustomerRepository.class);
        converter = Mockito.mock(CustomerDtoConverter.class);
        service = new CustomerService(customerRepository, converter);
    }

    @Test
    public void testFindByCustomerId_whenCustomerIdExists_shouldReturnCustomer(){
        Customer customer = new Customer("id", "name", "surname", Set.of());

        Mockito.when(customerRepository.findById("id")).thenReturn(Optional.of(customer));

        Customer result = service.findCustomerById("id");

        Assertions.assertEquals(result, customer);
    }

    @Test
    public void testFindByCustomerId_whenCustomerIdDoesNotExist_shouldThrowCustomerNotFoundException(){

        Mockito.when(customerRepository.findById("id")).thenReturn(Optional.empty());

        Assertions.assertThrows(CustomerNotFoundException.class, () -> service.findCustomerById("id"));

    }

    @Test
    public void testGetCustomerById_whenCustomerIdExists_shouldReturnCustomer(){
        Customer customer = new Customer("id", "name", "surname", Set.of());
        CustomerDto customerDto = new CustomerDto("id", "name", "surname", Set.of());

        Mockito.when(customerRepository.findById("id")).thenReturn(Optional.of(customer));
        Mockito.when(converter.convertToCustomerDto(customer)).thenReturn(customerDto);

        CustomerDto result = service.getCustomerById("id");

        Assertions.assertEquals(result, customerDto);
    }

    @Test
    public void testGetByCustomerId_whenCustomerIdDoesNotExist_shouldThrowCustomerNotFoundException(){

        Mockito.when(customerRepository.findById("id")).thenReturn(Optional.empty());

        Assertions.assertThrows(CustomerNotFoundException.class, () -> service.getCustomerById("id"));

        Mockito.verifyNoInteractions(converter);

    }
}
