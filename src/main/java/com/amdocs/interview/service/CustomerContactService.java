package com.amdocs.interview.service;

import com.amdocs.interview.domain.dto.CustomerContactDto;
import com.amdocs.interview.domain.enity.Customer;
import com.amdocs.interview.domain.enity.CustomerContact;
import com.amdocs.interview.domain.enums.ContactMedium;
import com.amdocs.interview.repository.ICustomerContactRepository;
import com.amdocs.interview.service.exception.CustomerContactNotFoundException;
import com.amdocs.interview.service.exception.DuplicateContactMediumException;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import static com.amdocs.interview.domain.enums.ContactMedium.FAX;
import static com.amdocs.interview.domain.enums.ContactMedium.PHONE;
import static java.lang.String.format;

@Service
public class CustomerContactService implements ICustomerContactService {

    private static final String INVALID_CUSTOMER_CONTACT_DETAILS_EXCEPTION_MSG = "Invalid Customer Contact details '%s' for provided Medium: %s.";
    private static final String CUSTOMER_CONTACT_NOT_FOUND_EXCEPTION_MSG = "Customer Contact with medium '%s' and details '%s' not found.";
    private static final String DUPLICATE_CONTACT_MEDIUM_EXCEPTION_MSG = "Duplicate medium '%s' given.";

    private static final int MIN_CONTACT_DETAILS_LENGTH = 3;
    private static final int MAX_CONTACT_DETAILS_LENGTH = 10;

    private final ICustomerContactRepository contactRepository;

    @Autowired
    public CustomerContactService(ICustomerContactRepository repository) {
        this.contactRepository = repository;
    }

    @Override
    public CustomerContact findCustomerContactByMediumAndDetails(String medium, String details) {
        ContactMedium contactMedium = contactMediumFromString(medium);
        return contactRepository.findCustomerContactByContactMediumAndContactDetails(contactMedium, details)
                .orElseThrow(() -> new CustomerContactNotFoundException(format(CUSTOMER_CONTACT_NOT_FOUND_EXCEPTION_MSG, medium, details)));
    }

    @Override
    public CustomerContact createCustomerContact(Customer customer, CustomerContactDto customerContactDto) {
        try {
            CustomerContact customerContact = new CustomerContact();
            customerContact.setCustomer(customer);

            ContactMedium contactMedium = contactMediumFromString(customerContactDto.getContactMedium());
            customerContact.setContactMedium(contactMedium);

            String contactDetails = customerContactDto.getContactDetails();
            validateContactDetailsForMedium(contactDetails, contactMedium);
            customerContact.setContactDetails(contactDetails);

            return contactRepository.save(customerContact);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateContactMediumException(format(DUPLICATE_CONTACT_MEDIUM_EXCEPTION_MSG,
                    customerContactDto.getContactMedium()), e.getCause());
        }
    }

    private ContactMedium contactMediumFromString(String contactMedium) {
        return ContactMedium.valueOf(contactMedium.toUpperCase());
    }

    private void validateContactDetailsForMedium(String contactDetails, ContactMedium contactMedium) {
        if (contactMedium.equals(PHONE) || contactMedium.equals(FAX)) {
            assertValidNumericContactDetails(contactDetails, contactMedium);
        } else {
            assertValidEmailDetails(contactDetails, contactMedium);
        }
    }

    private void assertValidEmailDetails(String contactDetails, ContactMedium medium) {
        if (!EmailValidator.getInstance().isValid(contactDetails)) {
            throw new IllegalArgumentException(getInvalidCustomerContactDetailsMsg(contactDetails, medium));
        }
    }

    private void assertValidNumericContactDetails(String contactDetails, ContactMedium medium) {
        try {
            int contactDetailsLength = contactDetails.length();

            if (contactDetailsLength < MIN_CONTACT_DETAILS_LENGTH || contactDetailsLength > MAX_CONTACT_DETAILS_LENGTH) {
                throw new IllegalArgumentException(getInvalidCustomerContactDetailsMsg(contactDetails, medium));
            }

            Integer.parseInt(contactDetails);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(getInvalidCustomerContactDetailsMsg(contactDetails, medium));
        }
    }

    private String getInvalidCustomerContactDetailsMsg(String contactDetails, ContactMedium medium) {
        return format(INVALID_CUSTOMER_CONTACT_DETAILS_EXCEPTION_MSG, contactDetails, medium.name());
    }
}
