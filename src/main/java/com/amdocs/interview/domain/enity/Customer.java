package com.amdocs.interview.domain.enity;

import com.amdocs.interview.domain.enums.CustomerCategory;
import com.amdocs.interview.domain.enums.LanguageCode;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Customer extends BaseEntity {

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "category")
    private CustomerCategory category;

    @Column(name = "language_code")
    @Enumerated(EnumType.STRING)
    private LanguageCode languageCode;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "company_name")
    private String companyName;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, targetEntity = CustomerContact.class, mappedBy = "customer")
    private Set<CustomerContact> customerContacts;

    public Customer(CustomerCategory customerCategory, LanguageCode languageCode, String firstName, String lastName, String companyName) {
        this.category = customerCategory;
        this.languageCode = languageCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.companyName = companyName;
    }
}
