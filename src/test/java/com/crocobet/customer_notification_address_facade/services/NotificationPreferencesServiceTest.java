package com.crocobet.customer_notification_address_facade.services;

import com.crocobet.customer_notification_address_facade.exceptions.CustomerNotFoundException;
import com.crocobet.customer_notification_address_facade.exceptions.NotificationPreferencesNotFoundException;
import com.crocobet.customer_notification_address_facade.model.Customer;
import com.crocobet.customer_notification_address_facade.model.NotificationPreferences;
import com.crocobet.customer_notification_address_facade.model.dtos.NotificationPreferencesDtoExistingPreferences;
import com.crocobet.customer_notification_address_facade.model.dtos.NotificationPreferencesDtoNewPreferences;
import com.crocobet.customer_notification_address_facade.repositories.NotificationPreferencesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class NotificationPreferencesServiceTest {
    @Mock
    private NotificationPreferencesRepository notificationPreferencesRepository;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private NotificationPreferencesService notificationPreferencesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPreferencesForCustomer() {
        NotificationPreferences preferences = new NotificationPreferences();
        when(notificationPreferencesRepository.findByCustomerId(1L)).thenReturn(preferences);

        NotificationPreferences foundPreferences = notificationPreferencesService.getPreferencesForCustomer(1L);

        assertNotNull(foundPreferences);
        verify(notificationPreferencesRepository, times(1)).findByCustomerId(1L);
    }

    @Test
    void testAddPreferences() {
        NotificationPreferencesDtoNewPreferences dto = new NotificationPreferencesDtoNewPreferences(1L, true, true,
                                                                                                    true);
        Customer customer = new Customer();
        when(customerService.getCustomerById(1L)).thenReturn(customer);
        when(notificationPreferencesRepository.save(any(NotificationPreferences.class))).thenAnswer(invocation -> {
            NotificationPreferences savedPreferences = invocation.getArgument(0);
            savedPreferences.setId(1L);
            return savedPreferences;
        });

        NotificationPreferences addedPreferences = notificationPreferencesService.addPreferences(dto);

        assertNotNull(addedPreferences);
        assertEquals(customer, addedPreferences.getCustomer());
        assertTrue(addedPreferences.isOptInSms());
        assertTrue(addedPreferences.isOptInEmail());
        assertTrue(addedPreferences.isOptInPromotionalMessages());
        verify(notificationPreferencesRepository, times(1)).save(any(NotificationPreferences.class));
    }

    @Test
    void testAddPreferences_CustomerNotFound() {
        NotificationPreferencesDtoNewPreferences dto = new NotificationPreferencesDtoNewPreferences(1L, true, true,
                                                                                                    true);
        when(customerService.getCustomerById(1L)).thenReturn(null);

        assertThrows(CustomerNotFoundException.class, () -> notificationPreferencesService.addPreferences(dto));
    }

    @Test
    void testUpdatePreferences() {
        NotificationPreferences existingPreferences = new NotificationPreferences();
        when(notificationPreferencesRepository.findById(1L)).thenReturn(Optional.of(existingPreferences));
        when(notificationPreferencesRepository.save(any(NotificationPreferences.class))).thenReturn(existingPreferences);

        NotificationPreferencesDtoExistingPreferences dto = new NotificationPreferencesDtoExistingPreferences(
                1L, 1L, true, true, true
        );
        NotificationPreferences updatedPreferences = notificationPreferencesService.updatePreferences(1L, dto);

        assertNotNull(updatedPreferences);
        assertTrue(updatedPreferences.isOptInSms());
        assertTrue(updatedPreferences.isOptInEmail());
        assertTrue(updatedPreferences.isOptInPromotionalMessages());
        verify(notificationPreferencesRepository, times(1)).findById(1L);
        verify(notificationPreferencesRepository, times(1)).save(any(NotificationPreferences.class));
    }

    @Test
    void testUpdatePreferences_NotFound() {
        when(notificationPreferencesRepository.findById(1L)).thenReturn(Optional.empty());

        NotificationPreferencesDtoExistingPreferences dto = new NotificationPreferencesDtoExistingPreferences(
                1L, 1L, true, true, true
        );

        assertThrows(NotificationPreferencesNotFoundException.class,
                     () -> notificationPreferencesService.updatePreferences(1L, dto));
    }

    @Test
    void testDeletePreferences() {
        NotificationPreferences preferences = new NotificationPreferences();
        Customer customer = new Customer();
        preferences.setCustomer(customer);
        when(notificationPreferencesRepository.findById(1L)).thenReturn(Optional.of(preferences));

        notificationPreferencesService.deletePreferences(1L);

        verify(notificationPreferencesRepository, times(1)).findById(1L);
        verify(notificationPreferencesRepository, times(1)).delete(preferences);
        verify(customerService, times(1)).updateCustomer(customer);
    }

    @Test
    void testDeletePreferences_NotFound() {
        when(notificationPreferencesRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotificationPreferencesNotFoundException.class,
                     () -> notificationPreferencesService.deletePreferences(1L));
    }
}