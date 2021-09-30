package com.amdocs.interview.domain.dto;

public class CustomerDto {

    private final String category;

    private final String languageCode;

    private final String firstName;

    private final String lastName;

    private final String companyName;

    public CustomerDto(String category, String languageCode, String firstName, String lastName, String companyName) {
        this.category = category;
        this.languageCode = languageCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.companyName = companyName;
    }

    public String getCategory() {
        return category;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCompanyName() {
        return companyName;
    }
}
