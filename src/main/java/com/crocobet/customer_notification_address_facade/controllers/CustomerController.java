package com.crocobet.customer_notification_address_facade.controllers;

import com.crocobet.customer_notification_address_facade.model.Customer;
import com.crocobet.customer_notification_address_facade.model.dtos.CustomerDtoExistingCustomer;
import com.crocobet.customer_notification_address_facade.model.dtos.CustomerDtoNewCustomer;
import com.crocobet.customer_notification_address_facade.services.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Slf4j
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
    public Customer createCustomer(@RequestBody CustomerDtoNewCustomer customer) {
        return customerService.addCustomer(customer);
    }

    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody CustomerDtoExistingCustomer customerDetails) {
        return customerService.updateCustomer(customerDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }

// todo: implement

    @GetMapping("/search/{keyword}")
    public List<Customer> searchCustomersByName(@PathVariable String keyword) {
        return customerService.searchCustomersByName(keyword);
    }


    @GetMapping("/search/contact/{contact}")
    public List<Customer> searchCustomersByContactInfo(@PathVariable String contact) {
        return customerService.searchCustomersByContactInfo(contact);
    }

    @GetMapping("/report/opted-in-email/{optedIn}")
    public long countCustomersOptedInEmail(@PathVariable boolean optedIn) {
        return customerService.countCustomersByOptedInEmail(optedIn);
    }

    @GetMapping("/report/opted-in-sms/{optedIn}")
    public long countCustomersOptedInSms(@PathVariable boolean optedIn) {
        return customerService.countCustomersByOptedInSms(optedIn);
    }

    @GetMapping("/report/opted-in-promotional-messages/{optedIn}")
    public long countCustomersOptedInPromotionalMessages(@PathVariable boolean optedIn) {
        return customerService.countCustomersByOptedInPromotionalMessages(optedIn);
    }

//    @GetMapping("/report/pagination/opted-in-email/{optedIn}")
//    public Page<Customer> getCustomersByOptedInEmail(
//            @PathVariable boolean optedIn,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(defaultValue = "id,asc") String[] sort) {
//        log.info("\n\n=== sort: {}\n\n", Arrays.toString(sort));
//
//        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
//        return customerService.findCustomersByOptedInEmail(optedIn, pageable);
//    }

    @GetMapping("/report/pagination/opted-in-email/{optedIn}")
    public Page<Customer> getCustomersByOptedInEmail(
            @PathVariable boolean optedIn,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "testDefault") String sortDirection) {
        log.info("\n\n=== Request parameters: optedIn={}, page={}, size={}, sortField={}, sortDirection={}\n\n",
                 optedIn, page, size, sortField, sortDirection);
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortField);
        log.info("\n\n=== sort: {}\n\n", sort);
        Pageable pageable = PageRequest.of(page, size, sort);
        return customerService.findCustomersByOptedInEmail(optedIn, pageable);
    }

    @GetMapping("/report/pagination/opted-in-sms/{optedIn}")
    public Page<Customer> getCustomersByOptedInSms(
            @PathVariable boolean optedIn,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id-def") String sortField,
            @RequestParam(defaultValue = "asc-def") String sortDirection) {
        log.info("\n\n=== Request parameters: optedIn={}, page={}, size={}, sortField={}, sortDirection={}\n\n",
                 optedIn, page, size, sortField, sortDirection);
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortField);
        log.info("\n\n=== sort: {}\n\n", sort);
        Pageable pageable = PageRequest.of(page, size, sort);
        return customerService.findCustomersByOptedInSms(optedIn, pageable);
    }

    @GetMapping("/report/pagination/opted-in-promotional-messages/{optedIn}")
    public Page<Customer> getCustomersByOptedInPromotionalMessages(
            @PathVariable boolean optedIn,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id-def") String sortField,
            @RequestParam(defaultValue = "asc-def") String sortDirection) {
        log.info("\n\n=== Request parameters: optedIn={}, page={}, size={}, sortField={}, sortDirection={}\n\n",
                 optedIn, page, size, sortField, sortDirection);

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortField);
        log.info("\n\n=== sort: {}\n\n", sort);
        Pageable pageable = PageRequest.of(page, size, sort);
        return customerService.findCustomersByOptedInPromotionalMessages(optedIn, pageable);
    }

}
