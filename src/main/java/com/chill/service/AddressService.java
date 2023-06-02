package com.chill.service;

import com.chill.entity.Address;

import com.chill.mapper.AddressMapper;
import com.chill.normalize.Spellchecker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressMapper addressMapper;

    private final Spellchecker spellchecker;

    public Address correctAddress(List<String> suggestionList) {
        return addressMapper.mapToAddress(suggestionList);
    }

    public List<String> normalizeAddress(Address address) {
        String addressStr = addressMapper.mapToString(address);
        addressStr = spellchecker.normalize(addressStr);

        return spellchecker.spellcheck(addressStr);
    }
}
