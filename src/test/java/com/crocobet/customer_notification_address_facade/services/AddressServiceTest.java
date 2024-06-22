package com.crocobet.customer_notification_address_facade.services;

import com.crocobet.customer_notification_address_facade.exceptions.AddressNotFoundException;
import com.crocobet.customer_notification_address_facade.model.Address;
import com.crocobet.customer_notification_address_facade.model.CommunicationType;
import com.crocobet.customer_notification_address_facade.model.Customer;
import com.crocobet.customer_notification_address_facade.model.dtos.AddressDto;
import com.crocobet.customer_notification_address_facade.repositories.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private AddressService addressService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAddressesForCustomer() {
        Customer customer = new Customer(1L, "testUser", null, null);
        Address address1 = new Address(1L, customer, CommunicationType.EMAIL, "test@test.com");
        Address address2 = new Address(2L, customer, CommunicationType.SMS, "123456789");

        when(addressRepository.findByCustomerId(1L)).thenReturn(Arrays.asList(address1, address2));

        List<Address> addresses = addressService.getAllAddressesForCustomer(1L);

        assertNotNull(addresses);
        assertEquals(2, addresses.size());
        assertEquals("test@test.com", addresses.get(0).getContactValue());
        assertEquals(CommunicationType.SMS, addresses.get(1).getAddressType());
        verify(addressRepository, times(1)).findByCustomerId(1L);
    }

    @Test
    void testAddAddress() {
        AddressDto addressDto = new AddressDto(1L, "EMAIL", "test@test.com");
        Customer customer = new Customer(1L, "testUser", null, null);

        when(customerService.getCustomerById(1L)).thenReturn(customer);
        when(addressRepository.save(any(Address.class))).thenAnswer(invocation -> {
            Address savedAddress = invocation.getArgument(0);
            savedAddress.setId(1L);
            return savedAddress;
        });

        Address addedAddress = addressService.addAddress(addressDto);

        assertNotNull(addedAddress);
        assertEquals(1L, addedAddress.getId());
        assertEquals("test@test.com", addedAddress.getContactValue());
        assertEquals(CommunicationType.EMAIL, addedAddress.getAddressType());
        assertEquals(customer, addedAddress.getCustomer());
        verify(customerService, times(1)).getCustomerById(1L);
        verify(addressRepository, times(1)).save(any(Address.class));
    }

    @Test
    void testUpdateAddress() {
        AddressDto addressDto = new AddressDto(1L, "SMS", "987654321");
        Address existingAddress = new Address(1L, new Customer(), CommunicationType.EMAIL, "test@test.com");

        when(addressRepository.findById(1L)).thenReturn(Optional.of(existingAddress));
        when(addressRepository.save(any(Address.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Address updatedAddress = addressService.updateAddress(addressDto);

        assertNotNull(updatedAddress);
        assertEquals(1L, updatedAddress.getId());
        assertEquals("987654321", updatedAddress.getContactValue());
        assertEquals(CommunicationType.SMS, updatedAddress.getAddressType());
        verify(addressRepository, times(1)).findById(1L);
        verify(addressRepository, times(1)).save(any(Address.class));
    }

    @Test
    void testUpdateAddress_AddressNotFound() {
        AddressDto addressDto = new AddressDto(1L, "SMS", "987654321");

        when(addressRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AddressNotFoundException.class, () -> addressService.updateAddress(addressDto));
        verify(addressRepository, times(1)).findById(1L);
        verify(addressRepository, never()).save(any(Address.class));
    }

    @Test
    void testDeleteAddress_AddressNotFound() {
        when(addressRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AddressNotFoundException.class, () -> addressService.deleteAddress(1L));
        verify(addressRepository, times(1)).findById(1L);
        verify(addressRepository, never()).deleteById(any(Long.class));
    }
}