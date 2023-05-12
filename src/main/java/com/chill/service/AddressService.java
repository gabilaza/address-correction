package com.chill.service;

import com.chill.entity.Address;

import org.springframework.stereotype.Service;

@Service
public class AddressService {
    public Address correctAddress(Address address) {
        return new Address();
    }
}
