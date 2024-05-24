package com.example.hotelsproject.model.utils;

import com.example.hotelsproject.model.entities.Hotel;

public class HotelValidator {

    public boolean validate (Hotel info) {
        String name = info.getName();
        String address = info.getAddress();
        return checkName(name) && checkAddress(address);
    }

    private boolean checkName(String name) {
        int nameLength = name.length();
        return nameLength >= 2 && nameLength <= 20;
    }

    private boolean checkAddress(String address) {
        int addressLength = address.length();
        return addressLength >= 4 && addressLength <= 80;
    }
}
