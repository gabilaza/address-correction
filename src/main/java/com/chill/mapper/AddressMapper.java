package com.chill.mapper;

import com.chill.entity.Address;
import com.chill.entity.City;
import com.chill.entity.Country;
import com.chill.entity.PostalCode;
import com.chill.entity.State;
import com.chill.graph.Chain;
import com.chill.graph.Vertex;
import com.chill.payload.request.AddressRequest;
import com.chill.payload.response.AddressResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class AddressMapper {

    public Address mapToAddress(AddressRequest addressRequest) {
        Country country = new Country(addressRequest.getCountry());
        State state = new State(addressRequest.getState(), country);
        City city = new City(addressRequest.getCity(), state);
        PostalCode postalCode = new PostalCode(addressRequest.getPostalCode(), city);
        return new Address(country, state, city, postalCode);
    }

    public AddressResponse mapToAddressResponse(Address address) {
        String countryName = address.getCountry() == null ? "" : address.getCountry().getName();
        String stateName = address.getState() == null ? "" : address.getState().getName();
        String cityName = address.getCity() == null ? "" : address.getCity().getName();
        String postalCodeName =
                address.getPostalCode() == null ? "" : address.getPostalCode().getName();
        return new AddressResponse(countryName, stateName, cityName, postalCodeName);
    }

    public String mapToString(Address address) {
        return address.getCountry().getName()
                + " "
                + address.getState().getName()
                + " "
                + address.getCity().getName()
                + " "
                + address.getPostalCode().getName();
    }

    public Address mapToAddress(Chain<Vertex<String>> chain) {
        Address address = new Address();
        for (Vertex<String> vertex : chain.getChain()) {
            if (vertex instanceof Country) {
                address.setCountry((Country) vertex);
            } else if (vertex instanceof State) {
                address.setState((State) vertex);
            } else if (vertex instanceof City) {
                address.setCity((City) vertex);
            } else if (vertex instanceof PostalCode) {
                address.setPostalCode((PostalCode) vertex);
            }
        }
        return address;
    }
}
