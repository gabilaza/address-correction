package com.chill.service;

import com.chill.entity.Address;

import com.chill.mapper.AddressMapper;
import com.chill.normalize.Spellchecker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressMapper addressMapper;

    private final Spellchecker spellchecker;

    public Address correctAddress(Address address) {
        return address;
    }

    public Address normalizeAddress(Address address) {
        String addressStr = addressMapper.mapToString(address);
        System.out.println(addressStr);
        addressStr = spellchecker.normalize(addressStr);
        addressStr = spellchecker.spellcheck(addressStr);
        address = addressMapper.mapToAddress(addressStr);
        return address;
    }
}
