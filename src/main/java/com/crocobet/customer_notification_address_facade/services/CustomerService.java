package com.crocobet.customer_notification_address_facade.services;

import com.crocobet.customer_notification_address_facade.exceptions.CustomerNotFoundException;
import com.crocobet.customer_notification_address_facade.model.Customer;
import com.crocobet.customer_notification_address_facade.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository
                .findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
    }

    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, Customer customerDetails) {
        Customer customer = customerRepository
                .findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        customer.setUsername(customerDetails.getUsername());
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        Customer customer = customerRepository
                .findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        customerRepository.delete(customer);
    }

    public List<Customer> searchCustomersByName(String keyword) {
        return customerRepository.findByUsernameContaining(keyword);
    }

    public List<Customer> searchCustomersByContactInfo(String contact) {
        return customerRepository.findByContactInfo(contact);
    }

    public long countCustomersByOptedInEmail(boolean optedIn) {
        // Example of reporting method
        return customerRepository.countByNotificationPreferencesOptInEmail(optedIn);
    }

    public long countCustomersByOptedInSms(boolean optedIn) {
        return customerRepository.countByNotificationPreferencesOptInSms(optedIn);
    }

    public long countCustomersByOptedInPromotionalMessages(boolean optedIn) {
        return customerRepository.countByNotificationPreferencesOptInPromotionalMessages(optedIn);
    }

    public Page<Customer> findCustomersByOptedInEmail(boolean optedIn, Pageable pageable) {
        return customerRepository.findByNotificationPreferencesOptInEmail(optedIn, pageable);
    }

    public Page<Customer> findCustomersByOptedInsms(boolean optedIn, Pageable pageable) {
        return customerRepository.findByNotificationPreferencesOptInSms(optedIn, pageable);
    }

    public Page<Customer> findCustomersByOptedInPromotionalMessages(boolean optedIn, Pageable pageable) {
        return customerRepository.findByNotificationPreferencesOptInPromotionalMessages(optedIn, pageable);
    }
}
