package com.crocobet.customer_notification_address_facade.services;

import com.crocobet.customer_notification_address_facade.exceptions.AddressNotFoundException;
import com.crocobet.customer_notification_address_facade.model.Address;
import com.crocobet.customer_notification_address_facade.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {this.addressRepository = addressRepository;}

    public List<Address> getAllAddressesForCustomer(Long customerId) {
        return addressRepository.findByCustomerId(customerId);
    }

    public Address addAddress(Address address) {
        return addressRepository.save(address);
    }

    public Address updateAddress(Long id, Address addressDetails) {
        Address address = addressRepository
                .findById(id)
                .orElseThrow(() -> new AddressNotFoundException("Address not found"));
        address.setAddressType(addressDetails.getAddressType());
        address.setContactValue(addressDetails.getContactValue());
        return addressRepository.save(address);
    }

    public void deleteAddress(Long id) {
        Address address = addressRepository
                .findById(id)
                .orElseThrow(() -> new AddressNotFoundException("Address not found"));
        addressRepository.delete(address);
    }
}
