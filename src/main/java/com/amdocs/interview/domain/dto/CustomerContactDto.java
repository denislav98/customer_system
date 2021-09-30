package com.amdocs.interview.domain.dto;

public class CustomerContactDto {

    private final String contactMedium;

    private final String contactDetails;

    public CustomerContactDto(String contactMedium, String contactDetails) {
        this.contactMedium = contactMedium;
        this.contactDetails = contactDetails;
    }

    public String getContactMedium() {
        return contactMedium;
    }

    public String getContactDetails() {
        return contactDetails;
    }
}
