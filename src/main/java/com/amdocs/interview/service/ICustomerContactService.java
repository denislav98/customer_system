package com.amdocs.interview.service;

import com.amdocs.interview.domain.dto.CustomerContactDto;
import com.amdocs.interview.domain.enity.Customer;
import com.amdocs.interview.domain.enity.CustomerContact;

public interface ICustomerContactService {

    CustomerContact findCustomerContactByMediumAndDetails(String medium, String details);

    CustomerContact createCustomerContact(Customer customer, CustomerContactDto customerContactDto);
}
