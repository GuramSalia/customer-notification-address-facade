package com.crocobet.customer_notification_address_facade.services;

import com.crocobet.customer_notification_address_facade.exceptions.NotificationPreferencesNotFoundException;
import com.crocobet.customer_notification_address_facade.exceptions.ResourceNotFoundException;
import com.crocobet.customer_notification_address_facade.model.NotificationPreferences;
import com.crocobet.customer_notification_address_facade.repositories.NotificationPreferencesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationPreferencesService {
    private final NotificationPreferencesRepository notificationPreferencesRepository;

    @Autowired
    public NotificationPreferencesService(NotificationPreferencesRepository notificationPreferencesRepository) {
        this.notificationPreferencesRepository = notificationPreferencesRepository;
    }

    public NotificationPreferences getPreferencesForCustomer(Long customerId) {
        return notificationPreferencesRepository.findByCustomerId(customerId);
    }

    public NotificationPreferences addPreferences(NotificationPreferences preferences) {
        return notificationPreferencesRepository.save(preferences);
    }

    public NotificationPreferences updatePreferences(Long id, NotificationPreferences preferencesDetails) {
        NotificationPreferences preferences = notificationPreferencesRepository
                .findById(id)
                .orElseThrow(() -> new NotificationPreferencesNotFoundException("Preferences not found"));
        preferences.setOptInSms(preferencesDetails.isOptInSms());
        preferences.setOptInEmail(preferencesDetails.isOptInEmail());
        preferences.setOptInPromotionalMessages(preferencesDetails.isOptInPromotionalMessages());
        return notificationPreferencesRepository.save(preferences);
    }

    public void deletePreferences(Long id) {
        NotificationPreferences preferences = notificationPreferencesRepository
                .findById(id)
                .orElseThrow(() -> new NotificationPreferencesNotFoundException("Preferences not found"));
        notificationPreferencesRepository.delete(preferences);
    }
}
