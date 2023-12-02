package com.ies.account.service;

import com.ies.account.dto.CustomerDto;
import com.ies.account.dto.CustomerDtoConverter;
import com.ies.account.exception.CustomerNotFoundException;
import com.ies.account.model.Customer;
import com.ies.account.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerDtoConverter customerDtoConverter;

    public CustomerService(CustomerRepository customerRepository, CustomerDtoConverter customerDtoConverter) {
        this.customerRepository = customerRepository;
        this.customerDtoConverter = customerDtoConverter;
    }

    public List<CustomerDto> getAllCustomers(){
        List<Customer> customerList = customerRepository.findAll();

        return customerList.stream().map(this.customerDtoConverter::convertToCustomerDto)
                .collect(Collectors.toList());
    }
    protected Customer findCustomerById(String id){
        return customerRepository.findById(id).orElseThrow(() ->
            new CustomerNotFoundException("Customer could not find by id :" + id));

    }
    public CustomerDto getCustomerById(String customerId){
        return customerDtoConverter.convertToCustomerDto(findCustomerById(customerId));
    }
}
