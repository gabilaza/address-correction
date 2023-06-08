package com.chill.mapper;

import com.chill.entity.*;
import com.chill.graph.Chain;
import com.chill.graph.Vertex;
import com.chill.payload.request.AddressRequest;
import com.chill.payload.response.AddressResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressMapperTest {

    private final AddressMapper addressMapper = new AddressMapper();
    @Test
    void givenAddressRequest_whenMappedToAddress_thenAddressCorrectlyCreated() {
        AddressRequest addressRequest = new AddressRequest("USA", "New York", "New York", "10001");

        Address address = addressMapper.mapToAddress(addressRequest);

        assertEquals("USA", address.getCountry().getName());
        assertEquals("New York", address.getState().getName());
        assertEquals("New York", address.getCity().getName());
        assertEquals("10001", address.getPostalCode().getName());
    }

    @Test
    void givenAddress_whenMappedToAddressResponse_thenAddressResponseCorrectlyCreated() {
        Country country = new Country("USA");
        State state = new State("New York", country);
        City city = new City("New York", state);
        PostalCode postalCode = new PostalCode("10001", city);
        Address address = new Address(country, state, city, postalCode);

        AddressResponse addressResponse = addressMapper.mapToAddressResponse(address);

        assertEquals("USA", addressResponse.getCountry());
        assertEquals("New York", addressResponse.getState());
        assertEquals("New York", addressResponse.getCity());
        assertEquals("10001", addressResponse.getPostalCode());
    }

    @Test
    void givenAddress_whenMappedToString_thenCorrectAddressStringReturned() {
        Country country = new Country("USA");
        State state = new State("New York", country);
        City city = new City("New York", state);
        PostalCode postalCode = new PostalCode("10001", city);
        Address address = new Address(country, state, city, postalCode);

        String addressString = addressMapper.mapToString(address);

        assertEquals("USA New York New York 10001", addressString);
    }

    @Test
    void givenChainOfVertices_whenMappedToAddress_thenAddressCorrectlyCreated() {
        Country country = new Country("USA");
        State state = new State("New York", country);
        City city = new City("New York", state);
        PostalCode postalCode = new PostalCode("10001", city);

        Chain<Vertex<String>> chain = new Chain<>();
        chain.addElement(country);
        chain.addElement(state);
        chain.addElement(city);
        chain.addElement(postalCode);

        Address address = addressMapper.mapToAddress(chain);

        assertEquals("USA", address.getCountry().getName());
        assertEquals("New York", address.getState().getName());
        assertEquals("New York", address.getCity().getName());
        assertEquals("10001", address.getPostalCode().getName());
    }

}