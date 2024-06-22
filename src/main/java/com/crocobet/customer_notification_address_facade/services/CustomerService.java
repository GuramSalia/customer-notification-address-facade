package com.crocobet.customer_notification_address_facade.services;

import com.crocobet.customer_notification_address_facade.exceptions.CustomerNotFoundException;
import com.crocobet.customer_notification_address_facade.model.Customer;
import com.crocobet.customer_notification_address_facade.model.dtos.CustomerDtoExistingCustomer;
import com.crocobet.customer_notification_address_facade.model.dtos.CustomerDtoNewCustomer;
import com.crocobet.customer_notification_address_facade.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional(readOnly = true)
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Customer getCustomerById(Long id) {
        return customerRepository
                .findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with ID " + id + " not found"));
    }

    @Transactional
    public Customer addCustomer(CustomerDtoNewCustomer customer) {
        Customer newCustomer = new Customer();
        newCustomer.setUsername(customer.getUsername());
        return customerRepository.save(newCustomer);
    }

    @Transactional
    public Customer updateCustomer(CustomerDtoExistingCustomer customerDetails) {
        log.info("\n\n--------In CustomerService provided customerDetails: {}\n\n", customerDetails);
        Customer customer = customerRepository
                .findById(customerDetails.getId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        log.info("\n\n--------In CustomerService > update customer > existing customer: {}\n\n", customer);
        customer.setUsername(customerDetails.getUsername());
        log.info("\n\n--------In CustomerService > update customer > updated customer: {}\n\n", customer);
        return customerRepository.save(customer);
    }

    @Transactional
    public Customer updateCustomer(Customer customerDetails) {
        Customer customer = customerRepository
                .findById(customerDetails.getId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        customer.setUsername(customerDetails.getUsername());
        return customerRepository.save(customer);
    }

    @Transactional
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository
                .findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        customerRepository.delete(customer);
    }

    @Transactional(readOnly = true)
    public List<Customer> searchCustomersByName(String keyword) {
        return customerRepository.findByUsernameContaining(keyword);
    }

    @Transactional(readOnly = true)
    public List<Customer> searchCustomersByContactInfo(String contact) {
        return customerRepository.findByContactInfo(contact);
    }

    @Transactional(readOnly = true)
    public long countCustomersByOptedInEmail(boolean optedIn) {

        return customerRepository.countByNotificationPreferencesOptInEmail(optedIn);
    }

    @Transactional(readOnly = true)
    public long countCustomersByOptedInSms(boolean optedIn) {
        return customerRepository.countByNotificationPreferencesOptInSms(optedIn);
    }

    @Transactional(readOnly = true)
    public long countCustomersByOptedInPromotionalMessages(boolean optedIn) {
        return customerRepository.countByNotificationPreferencesOptInPromotionalMessages(optedIn);
    }

    @Transactional(readOnly = true)
    public Page<Customer> findCustomersByOptedInEmail(boolean optedIn, Pageable pageable) {
        return customerRepository.findByNotificationPreferencesOptInEmail(optedIn, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Customer> findCustomersByOptedInSms(boolean optedIn, Pageable pageable) {
        return customerRepository.findByNotificationPreferencesOptInSms(optedIn, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Customer> findCustomersByOptedInPromotionalMessages(boolean optedIn, Pageable pageable) {
        return customerRepository.findByNotificationPreferencesOptInPromotionalMessages(optedIn, pageable);
    }
}
