package com.b2.service;

import com.b2.entity.Address;
import com.b2.exception.address.AddressExistsException;
import com.b2.exception.address.AddressNotFoundException;
import com.b2.service.AddressService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.TestInstance.Lifecycle;


@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@Transactional
class AddressServiceTest {

    @Autowired
    private AddressService addressService;

    @Test
    void givenUserCreated_whenLoadUserByUsername_thenUserDetailsMatch() {
    }

}
