package com.crocobet.customer_notification_address_facade.controllers;

import com.crocobet.customer_notification_address_facade.model.Notification;
import com.crocobet.customer_notification_address_facade.model.NotificationStatus;
import com.crocobet.customer_notification_address_facade.model.NotificationSuccessRate;
import com.crocobet.customer_notification_address_facade.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/recipient/{recipient}")
    public List<Notification> getNotificationsByRecipient(@PathVariable String recipient) {
        return notificationService.getAllNotificationsForRecipient(recipient);
    }

    @GetMapping("/status/{status}")
    public List<Notification> getNotificationsByStatus(@PathVariable NotificationStatus status) {
        return notificationService.getNotificationsByStatus(status);
    }

    @GetMapping("/report")
    public Map<NotificationStatus, NotificationSuccessRate> getNotificationDeliverySuccessRates() {
        return notificationService.getNotificationDeliverySuccessRates();
    }

    @GetMapping("/status/customer/{customerId}/{status}")
    public List<Notification> getNotificationsByStatusForCustomer(
            @PathVariable Long customerId, @PathVariable NotificationStatus status
    ) {
        return notificationService.getNotificationsByStatusForCustomer(customerId, status);
    }

    @GetMapping("/report/customer/{customerId}")
    public Map<NotificationStatus, NotificationSuccessRate> getNotificationDeliverySuccessRatesForCustomer(
            @PathVariable Long customerId
    ) {
        return notificationService.getNotificationDeliverySuccessRatesForCustomer(customerId);
    }
}
