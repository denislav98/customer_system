package com.amdocs.interview.service;

import com.amdocs.interview.domain.dto.CustomerDto;
import com.amdocs.interview.domain.enity.Customer;
import com.amdocs.interview.domain.enums.CustomerCategory;
import com.amdocs.interview.domain.enums.LanguageCode;
import com.amdocs.interview.repository.ICustomerRepository;
import com.amdocs.interview.service.exception.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.amdocs.interview.domain.enums.CustomerCategory.BUSINESS;
import static com.amdocs.interview.domain.enums.CustomerCategory.RESIDENTIAL;
import static com.amdocs.interview.domain.enums.LanguageCode.BG;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Service
public class CustomerService implements ICustomerService {

    private static final String FIRST_NAME_NOT_PROVIDED_EXCEPTION_MSG = format("First Name is mandatory for customer with category '%s'", RESIDENTIAL);
    private static final String LAST_NAME_NOT_PROVIDED_EXCEPTION_MSG = format("Last Name is mandatory for customer with category '%s'", RESIDENTIAL);
    private static final String COMPANY_NAME_NOT_PROVIDED_EXCEPTION_MSG = format("Company Name is mandatory for customer with category '%s'", BUSINESS);
    private static final String INVALID_CUSTOMER_CATEGORY_EXCEPTION_MSG = "Invalid Customer Category '%s'. Customer Category should be: 0 (Residential) or 1 (Business).";
    private static final String CUSTOMER_NOT_FOUND_EXCEPTION_MSG = "Customer with id '%s' not found.";

    private final ICustomerRepository repository;

    @Autowired
    public CustomerService(ICustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Customer findCustomerById(Long id, boolean withContacts) {
        return getCustomer(id, withContacts)
                .orElseThrow(() -> new CustomerNotFoundException(format(CUSTOMER_NOT_FOUND_EXCEPTION_MSG, id)));
    }

    private Optional<Customer> getCustomer(Long id, boolean withContacts) {
        return withContacts //
                ? repository.findById(id) //
                : repository.getCustomerWithoutContacts(id);
    }

    @Override
    public Customer createCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();

        customer.setCategory(getCustomerCategoryFromDto(customerDto));
        customer.setLanguageCode(getLanguageCode(customerDto));

        validateAdditionalCustomerData(customerDto, customer.getCategory());

        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setCompanyName(customerDto.getCompanyName());

        return repository.save(customer);
    }

    private void validateAdditionalCustomerData(CustomerDto customerDto, CustomerCategory category) {
        if (category.equals(RESIDENTIAL)) {
            assertNotNull(customerDto.getFirstName(), FIRST_NAME_NOT_PROVIDED_EXCEPTION_MSG);
            assertNotNull(customerDto.getLastName(), LAST_NAME_NOT_PROVIDED_EXCEPTION_MSG);
        } else {
            assertNotNull(customerDto.getCompanyName(), COMPANY_NAME_NOT_PROVIDED_EXCEPTION_MSG);
        }
    }

    private CustomerCategory getCustomerCategoryFromDto(CustomerDto customerDto) {
        String category = customerDto.getCategory();
        try {
            CustomerCategory customerCategory = CustomerCategory.get(Integer.parseInt(category));

            if (customerCategory == null) {
                throw new IllegalArgumentException(getInvalidCategoryExceptionMsg(category));
            }

            return customerCategory;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(getInvalidCategoryExceptionMsg(category));
        }
    }

    private String getInvalidCategoryExceptionMsg(String category) {
        return format(INVALID_CUSTOMER_CATEGORY_EXCEPTION_MSG, category);
    }

    private void assertNotNull(String value, String exceptionMsg) {
        if (isEmpty(value)) {
            throw new IllegalArgumentException(exceptionMsg);
        }
    }

    private LanguageCode getLanguageCode(CustomerDto customerDto) {
        return LanguageCode.valueOf(defaultIfEmpty(customerDto.getLanguageCode().toUpperCase(), BG.name()));
    }
}
