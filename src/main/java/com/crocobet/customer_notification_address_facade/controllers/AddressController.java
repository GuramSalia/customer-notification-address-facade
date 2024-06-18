package com.crocobet.customer_notification_address_facade.controllers;

import com.crocobet.customer_notification_address_facade.model.Address;
import com.crocobet.customer_notification_address_facade.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {
    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {this.addressService = addressService;}

    @GetMapping("/customer/{customerId}")
    public List<Address> getAllAddressesForCustomer(@PathVariable Long customerId) {
        return addressService.getAllAddressesForCustomer(customerId);
    }

    @PostMapping
    public Address createAddress(@RequestBody Address address) {
        return addressService.addAddress(address);
    }

    @PutMapping("/{id}")
    public Address updateAddress(@PathVariable Long id, @RequestBody Address addressDetails) {
        return addressService.updateAddress(id, addressDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
    }
}
