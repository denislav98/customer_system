package com.amdocs.interview.repository;

import com.amdocs.interview.domain.enity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT new Customer (c.category,c.languageCode, c.firstName, c.lastName, c.companyName) FROM Customer c WHERE c.id = :customerId")
    Optional<Customer> getCustomerWithoutContacts(@Param("customerId") Long customerId);
}
