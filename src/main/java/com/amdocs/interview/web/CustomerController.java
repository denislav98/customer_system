package com.amdocs.interview.web;

import com.amdocs.interview.domain.dto.CustomerContactDto;
import com.amdocs.interview.domain.dto.CustomerDto;
import com.amdocs.interview.domain.enity.Customer;
import com.amdocs.interview.domain.enity.CustomerContact;
import com.amdocs.interview.service.ICustomerContactService;
import com.amdocs.interview.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final ICustomerService customerService;
    private final ICustomerContactService customerContactService;

    @Autowired
    public CustomerController(ICustomerService customerService, ICustomerContactService customerContactService) {
        this.customerService = customerService;
        this.customerContactService = customerContactService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable(value = "id") Long customerId, @RequestParam(defaultValue = "false") boolean withContacts) {
        return ResponseEntity.ok().body(customerService.findCustomerById(customerId, withContacts));
    }

    @GetMapping("/contact")
    public ResponseEntity<Customer> getCustomerFromContactDetails(@RequestParam String medium, @RequestParam String details) {
        CustomerContact customerContact = customerContactService.findCustomerContactByMediumAndDetails(medium, details);
        return ResponseEntity.ok().body(customerContact.getCustomer());
    }

    @PostMapping("/create")
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerDto customerDto) {
        return ResponseEntity.ok().body(customerService.createCustomer(customerDto));
    }

    @PutMapping("/create/contact/{id}")
    public ResponseEntity<CustomerContact> createCustomerContact(@PathVariable(value = "id") Long customerId, @RequestBody CustomerContactDto customerContactDto) {
        Customer customer = customerService.findCustomerById(customerId, true);
        return ResponseEntity.ok().body(customerContactService.createCustomerContact(customer, customerContactDto));
    }
}
