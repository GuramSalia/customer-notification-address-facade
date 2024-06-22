package com.crocobet.customer_notification_address_facade.services;

import com.crocobet.customer_notification_address_facade.exceptions.AddressNotFoundException;
import com.crocobet.customer_notification_address_facade.model.Address;
import com.crocobet.customer_notification_address_facade.model.CommunicationType;
import com.crocobet.customer_notification_address_facade.model.Customer;
import com.crocobet.customer_notification_address_facade.model.dtos.AddressDto;
import com.crocobet.customer_notification_address_facade.repositories.AddressRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Slf4j
@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final CustomerService customerService;

    @Autowired
    public AddressService(
            AddressRepository addressRepository,
            CustomerService customerService
    ) {
        this.addressRepository = addressRepository;
        this.customerService = customerService;
    }

    @Transactional(readOnly = true)
    public List<Address> getAllAddressesForCustomer(Long customerId) {
        return addressRepository.findByCustomerId(customerId);
    }

    @Transactional
    public Address addAddress(AddressDto addressDetails) {
        Address address = new Address();
        Customer customer = customerService.getCustomerById(addressDetails.getId());
        CommunicationType addressType = CommunicationType.valueOf(addressDetails.getAddressType());
        address.setCustomer(customer);
        address.setAddressType(addressType);
        address.setContactValue(addressDetails.getContactValue());
        return addressRepository.save(address);
    }

    @Transactional
    public Address updateAddress(AddressDto addressDetails) {
        Address address = addressRepository
                .findById(addressDetails.getId())
                .orElseThrow(() -> new AddressNotFoundException("Address not found"));
        CommunicationType addressType = CommunicationType.valueOf(addressDetails.getAddressType());
        address.setAddressType(addressType);
        address.setContactValue(addressDetails.getContactValue());
        return addressRepository.save(address);
    }

    @Transactional
    public void deleteAddress(Long id) {
        log.info("\n\n-------Deleting address with id: {}\n\n", id);
        Address address = addressRepository
                .findById(id)
                .orElseThrow(() -> new AddressNotFoundException("Address not found"));
        Customer customer = address.getCustomer();
        log.info("\n\n-------Deleting address for customer: {}\n\n", customer);
        customer.getContactInfo().remove(address);
        customerService.updateCustomer(customer);
    }
}
