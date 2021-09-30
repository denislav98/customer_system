package com.amdocs.interview.domain.enums;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;

public enum CustomerCategory {

    RESIDENTIAL(0), BUSINESS(1);

    private final int value;
    private static final Map<Integer, CustomerCategory> CATEGORIES;

    static {
        Map<Integer, CustomerCategory> map = new HashMap<>();
        for (CustomerCategory category : CustomerCategory.values()) {
            map.put(category.value, category);
        }
        CATEGORIES = unmodifiableMap(map);
    }

    CustomerCategory(int value) {
        this.value = value;
    }

    public static CustomerCategory get(int value) {
        return CATEGORIES.get(value);
    }

}
