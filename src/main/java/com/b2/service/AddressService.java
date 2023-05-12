package com.b2.service;

import com.b2.entity.Address;
import com.b2.exception.address.*;
import com.b2.repository.AddressRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    public Address getAddressById(UUID id) {
        return addressRepository
                        .findById(id)
                        .orElseThrow(() -> new AddressNotFoundException(id));
    }

    public void createAddress(Address address) {
        if(Boolean.TRUE.equals(existsAddressById(address.getId())))
            throw new AddressExistsException(address.getId());
        addressRepository.save(address);
    }

    public void updateAddress(Address address) {
        if (Boolean.FALSE.equals(existsAddressById(address.getId())))
            throw new AddressNotFoundException(address.getId());
        addressRepository.save(address);
    }

    public void deleteAddress(Address address) {
        if (Boolean.FALSE.equals(existsAddressById(address.getId())))
            throw new AddressNotFoundException(address.getId());

        addressRepository.delete(address);
    }

    public Boolean existsAddressById(UUID id) {
        if(id != null)
            return addressRepository.existsById(id);
        return Boolean.FALSE;
    }
}
