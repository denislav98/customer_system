package com.amdocs.interview.domain.enity;

import com.amdocs.interview.domain.enums.ContactMedium;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "customer_contact")
@Getter
@Setter
@NoArgsConstructor
public class CustomerContact extends BaseEntity {

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "contact_medium")
    @Enumerated(EnumType.STRING)
    private ContactMedium contactMedium;

    @Column(name = "contact_details")
    private String contactDetails;
}
