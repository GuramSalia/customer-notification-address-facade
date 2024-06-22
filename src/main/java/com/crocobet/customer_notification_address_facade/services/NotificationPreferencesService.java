package com.crocobet.customer_notification_address_facade.services;

import com.crocobet.customer_notification_address_facade.exceptions.CustomerNotFoundException;
import com.crocobet.customer_notification_address_facade.exceptions.NotificationPreferencesNotFoundException;
import com.crocobet.customer_notification_address_facade.model.Customer;
import com.crocobet.customer_notification_address_facade.model.NotificationPreferences;
import com.crocobet.customer_notification_address_facade.model.dtos.NotificationPreferencesDtoExistingPreferences;
import com.crocobet.customer_notification_address_facade.model.dtos.NotificationPreferencesDtoNewPreferences;
import com.crocobet.customer_notification_address_facade.repositories.NotificationPreferencesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class NotificationPreferencesService {
    private final NotificationPreferencesRepository notificationPreferencesRepository;
    private final CustomerService customerService;

    @Autowired
    public NotificationPreferencesService(
            NotificationPreferencesRepository notificationPreferencesRepository,
            CustomerService customerService
    ) {
        this.notificationPreferencesRepository = notificationPreferencesRepository;
        this.customerService = customerService;
    }

    @Transactional(readOnly = true)
    public NotificationPreferences getPreferencesForCustomer(Long customerId) {
        return notificationPreferencesRepository.findByCustomerId(customerId);
    }

    @Transactional
    public NotificationPreferences addPreferences(NotificationPreferencesDtoNewPreferences preferencesDto) {
        log.error("\n\n---------- customer Id in Preferences service: {}\n\n", preferencesDto.getCustomerId());
        NotificationPreferences preferences = new NotificationPreferences();
        Customer customer = customerService.getCustomerById(preferencesDto.getCustomerId());

        if (customer == null) {
            throw new CustomerNotFoundException("customer with id {} not found" + preferencesDto.getCustomerId());
        }

        NotificationPreferences existingPreferences = customer.getNotificationPreferences();
        if (existingPreferences != null) {
            throw new IllegalStateException("Customer already has preferences");
        }

        preferences.setCustomer(customer);
        preferences.setOptInSms(preferencesDto.getOptInSms());
        preferences.setOptInEmail(preferencesDto.getOptInEmail());
        preferences.setOptInPromotionalMessages(preferencesDto.getOptInPromotionalMessages());
        return notificationPreferencesRepository.save(preferences);
    }

    @Transactional
    public NotificationPreferences updatePreferences(
            Long id,
            NotificationPreferencesDtoExistingPreferences preferencesDetails
    ) {

        NotificationPreferences preferences = notificationPreferencesRepository
                .findById(id)
                .orElseThrow(() -> new NotificationPreferencesNotFoundException("Preferences not found"));
        preferences.setOptInSms(preferencesDetails.getOptInSms());
        preferences.setOptInEmail(preferencesDetails.getOptInEmail());
        preferences.setOptInPromotionalMessages(preferencesDetails.getOptInPromotionalMessages());
        return notificationPreferencesRepository.save(preferences);
    }

    @Transactional
    public void deletePreferences(Long id) {
        NotificationPreferences preferences = notificationPreferencesRepository
                .findById(id)
                .orElseThrow(() -> new NotificationPreferencesNotFoundException("Preferences not found"));
        Customer customer = preferences.getCustomer();
        customer.setNotificationPreferences(null);
        customerService.updateCustomer(customer);
        notificationPreferencesRepository.delete(preferences);
    }
}
