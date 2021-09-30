package com.amdocs.interview.domain.enums;

public enum LanguageCode {

    EN("English"), BG("Bulgarian"), DE("Deutsch");

    private final String value;

    LanguageCode(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
