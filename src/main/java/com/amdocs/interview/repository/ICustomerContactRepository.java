package com.amdocs.interview.repository;

import com.amdocs.interview.domain.enums.ContactMedium;
import com.amdocs.interview.domain.enity.CustomerContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICustomerContactRepository extends JpaRepository<CustomerContact, Long> {

    Optional<CustomerContact> findCustomerContactByContactMediumAndContactDetails(ContactMedium contactMedium, String contactDetails);
}
