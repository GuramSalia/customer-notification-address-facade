package com.crocobet.customer_notification_address_facade.services;

import com.crocobet.customer_notification_address_facade.exceptions.CustomerNotFoundException;
import com.crocobet.customer_notification_address_facade.model.Customer;
import com.crocobet.customer_notification_address_facade.model.dtos.CustomerDtoExistingCustomer;
import com.crocobet.customer_notification_address_facade.model.dtos.CustomerDtoNewCustomer;
import com.crocobet.customer_notification_address_facade.repositories.CustomerRepository;
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

class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCustomers() {
        Customer customer1 = new Customer(1L, "user1", null, null);
        Customer customer2 = new Customer(2L, "user2", null, null);
        List<Customer> mockCustomers = Arrays.asList(customer1, customer2);

        when(customerRepository.findAll()).thenReturn(mockCustomers);

        List<Customer> customers = customerService.getAllCustomers();

        assertNotNull(customers);
        assertEquals(2, customers.size());
        assertEquals("user1", customers.get(0).getUsername());
        assertEquals("user2", customers.get(1).getUsername());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void testGetCustomerById() {
        Customer customer = new Customer(1L, "testUser", null, null);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.findById(2L)).thenReturn(Optional.empty());

        Customer foundCustomer = customerService.getCustomerById(1L);
        assertNotNull(foundCustomer);
        assertEquals("testUser", foundCustomer.getUsername());

        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomerById(2L));
        verify(customerRepository, times(1)).findById(1L);
        verify(customerRepository, times(1)).findById(2L);
    }

    @Test
    void testAddCustomer() {
        CustomerDtoNewCustomer newCustomerDto = new CustomerDtoNewCustomer("newUser");

        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> {
            Customer savedCustomer = invocation.getArgument(0);
            savedCustomer.setId(1L);
            return savedCustomer;
        });

        Customer addedCustomer = customerService.addCustomer(newCustomerDto);

        assertNotNull(addedCustomer);
        assertEquals(1L, addedCustomer.getId());
        assertEquals("newUser", addedCustomer.getUsername());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void testUpdateCustomer() {
        CustomerDtoExistingCustomer existingCustomerDto = new CustomerDtoExistingCustomer(1L, "updatedUser");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(new Customer(1L, "existingUser", null, null)));
        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Customer updatedCustomer = customerService.updateCustomer(existingCustomerDto);

        assertNotNull(updatedCustomer);
        assertEquals(1L, updatedCustomer.getId());
        assertEquals("updatedUser", updatedCustomer.getUsername());
        verify(customerRepository, times(1)).findById(1L);
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void testUpdateCustomer_CustomerNotFound() {
        CustomerDtoExistingCustomer existingCustomerDto = new CustomerDtoExistingCustomer(1L, "updatedUser");

        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.updateCustomer(existingCustomerDto));
        verify(customerRepository, times(1)).findById(1L);
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void testDeleteCustomer() {
        Customer customer = new Customer(1L, "toBeDeletedUser", null, null);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        customerService.deleteCustomer(1L);

        verify(customerRepository, times(1)).delete(customer);
    }

    @Test
    void testDeleteCustomer_CustomerNotFound() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.deleteCustomer(1L));
        verify(customerRepository, times(1)).findById(1L);
        verify(customerRepository, never()).delete(any(Customer.class));
    }
}