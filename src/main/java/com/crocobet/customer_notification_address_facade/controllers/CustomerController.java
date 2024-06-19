package com.crocobet.customer_notification_address_facade.controllers;

import com.crocobet.customer_notification_address_facade.model.Customer;
import com.crocobet.customer_notification_address_facade.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {this.customerService = customerService;}

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.addCustomer(customer);
    }

    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer customerDetails) {
        return customerService.updateCustomer(id, customerDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }

    @GetMapping("/search")
    public List<Customer> searchCustomersByName(@RequestParam String keyword) {
        return customerService.searchCustomersByName(keyword);
    }

    @GetMapping("/search/contact")
    public List<Customer> searchCustomersByContactInfo(@RequestParam String contact) {
        return customerService.searchCustomersByContactInfo(contact);
    }

    @GetMapping("/report/opted-in-email")
    public long countCustomersOptedInEmail(@RequestParam boolean optedIn) {
        return customerService.countCustomersByOptedInEmail(optedIn);
    }

    @GetMapping("/report/opted-in-sms")
    public long countCustomersOptedInSms(@RequestParam boolean optedIn) {
        return customerService.countCustomersByOptedInSms(optedIn);
    }

    @GetMapping("/report/opted-in-promotional-messages")
    public long countCustomersOptedInPromotionalMessages(@RequestParam boolean optedIn) {
        return customerService.countCustomersByOptedInPromotionalMessages(optedIn);
    }

    @GetMapping("/report/pagination/opted-in-email")
    public Page<Customer> getCustomersByOptedInEmail(@RequestParam boolean optedIn, Pageable pageable) {
        return customerService.findCustomersByOptedInEmail(optedIn, pageable);
    }

    @GetMapping("/report/pagination/opted-in-sms")
    public Page<Customer> getCustomersByOptedInSms(@RequestParam boolean optedIn, Pageable pageable) {
        return customerService.findCustomersByOptedInsms(optedIn, pageable);
    }

    @GetMapping("/report/pagination/opted-in-promotional-messages")
    public Page<Customer> getCustomersByOptedInPromotionalMessages(@RequestParam boolean optedIn, Pageable pageable) {
        return customerService.findCustomersByOptedInPromotionalMessages(optedIn, pageable);
    }

}
