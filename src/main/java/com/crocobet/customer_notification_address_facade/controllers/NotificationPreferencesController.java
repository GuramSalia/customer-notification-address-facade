package com.crocobet.customer_notification_address_facade.controllers;

import com.crocobet.customer_notification_address_facade.model.Customer;
import com.crocobet.customer_notification_address_facade.model.NotificationPreferences;
import com.crocobet.customer_notification_address_facade.model.dtos.NotificationPreferencesDtoExistingPreferences;
import com.crocobet.customer_notification_address_facade.model.dtos.NotificationPreferencesDtoNewPreferences;
import com.crocobet.customer_notification_address_facade.services.CustomerService;
import com.crocobet.customer_notification_address_facade.services.NotificationPreferencesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/preferences")
public class NotificationPreferencesController {
    private final NotificationPreferencesService notificationPreferencesService;
    private final CustomerService customerService;

    @Autowired
    public NotificationPreferencesController(
            NotificationPreferencesService notificationPreferencesService,
            CustomerService customerService
    ) {
        this.notificationPreferencesService = notificationPreferencesService;
        this.customerService = customerService;
    }

    @GetMapping("/customer/{customerId}")
    public NotificationPreferences getPreferencesForCustomer(@PathVariable Long customerId) {
        log.info("\n\n------- in NotificationPreferencesController receiving customerId: {}\n\n", customerId);

        Customer customer = customerService.getCustomerById(customerId);
        NotificationPreferences preferencesForCustomer =
                customer.getNotificationPreferences();
        log.info("\n\n------- in NotificationPreferencesController prefernce: {}\n\n", preferencesForCustomer);
        return preferencesForCustomer;
    }

    @PostMapping
    public NotificationPreferences createPreferences(@RequestBody NotificationPreferencesDtoNewPreferences preferencesDto) {
        log.error("\n\n---------- preferencesDto Id in Preferences controller: {}\n\n", preferencesDto);
        log.error("\n\n---------- customer Id in Preferences controller: {}\n\n", preferencesDto.getCustomerId());
        return notificationPreferencesService.addPreferences(preferencesDto);
    }

    @PutMapping("/{id}")
    public NotificationPreferences updatePreferences(
            @PathVariable Long id,
            @RequestBody NotificationPreferencesDtoExistingPreferences preferencesDetails
    ) {
        return notificationPreferencesService.updatePreferences(id, preferencesDetails);
    }

    @DeleteMapping("/{id}")
    public void deletePreferences(@PathVariable Long id) {
        notificationPreferencesService.deletePreferences(id);
    }
}
