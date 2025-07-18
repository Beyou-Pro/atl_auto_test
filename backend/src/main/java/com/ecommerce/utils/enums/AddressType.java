package com.ecommerce.utils.enums;

import lombok.Getter;

@Getter
public enum AddressType {
    BILLING("BILLING"),
    SHIPPING("shipping");

    private final String value;

    AddressType(String value) {
        this.value = value;
    }
}

