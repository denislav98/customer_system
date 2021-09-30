package com.amdocs.interview.web;

import com.amdocs.interview.domain.enity.Customer;

public class ModelMapper {

    public static Customer toCustomerWithoutContacts(Customer customer) {
        return new Customer(customer.getCategory(), //
                customer.getLanguageCode(), //
                customer.getFirstName(), //
                customer.getLastName(), //
                customer.getCompanyName()
        );
    }
}
