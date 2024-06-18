package com.crocobet.customer_notification_address_facade.controllers;

import com.crocobet.customer_notification_address_facade.model.NotificationPreferences;
import com.crocobet.customer_notification_address_facade.services.NotificationPreferencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/preferences")
public class NotificationPreferencesController {
    private final NotificationPreferencesService notificationPreferencesService;

    @Autowired
    public NotificationPreferencesController(NotificationPreferencesService notificationPreferencesService) {
        this.notificationPreferencesService = notificationPreferencesService;
    }

    @GetMapping("/customer/{customerId}")
    public NotificationPreferences getPreferencesForCustomer(@PathVariable Long customerId) {
        return notificationPreferencesService.getPreferencesForCustomer(customerId);
    }

    @PostMapping
    public NotificationPreferences createPreferences(@RequestBody NotificationPreferences preferences) {
        return notificationPreferencesService.addPreferences(preferences);
    }

    @PutMapping("/{id}")
    public NotificationPreferences updatePreferences(
            @PathVariable Long id,
            @RequestBody NotificationPreferences preferencesDetails
    ) {
        return notificationPreferencesService.updatePreferences(id, preferencesDetails);
    }

    @DeleteMapping("/{id}")
    public void deletePreferences(@PathVariable Long id) {
        notificationPreferencesService.deletePreferences(id);
    }
}
