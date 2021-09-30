package com.amdocs.interview.service;

import com.amdocs.interview.domain.dto.CustomerDto;
import com.amdocs.interview.domain.enity.Customer;

public interface ICustomerService {

    Customer findCustomerById(Long id, boolean withContacts);

    Customer createCustomer(CustomerDto customerDto);
}
