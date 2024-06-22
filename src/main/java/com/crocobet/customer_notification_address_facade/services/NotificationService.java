package com.crocobet.customer_notification_address_facade.services;

import com.crocobet.customer_notification_address_facade.exceptions.MessageNotFoundException;
import com.crocobet.customer_notification_address_facade.exceptions.NotificationNotFoundException;
import com.crocobet.customer_notification_address_facade.model.*;
import com.crocobet.customer_notification_address_facade.repositories.AddressRepository;
import com.crocobet.customer_notification_address_facade.repositories.NotificationMessageRepository;
import com.crocobet.customer_notification_address_facade.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationMessageRepository messageRepository;
    private final AddressRepository addressRepository;

    @Autowired
    public NotificationService(
            NotificationRepository notificationRepository,
            NotificationMessageRepository messageRepository,
            AddressRepository addressRepository
    ) {
        this.notificationRepository = notificationRepository;
        this.messageRepository = messageRepository;
        this.addressRepository = addressRepository;
    }

    @Transactional
    public Notification sendNotification(Long messageId, String recipient, String notificationType) {
        //simulating sending notification and saving first with pending status
        Notification notification = new Notification();
        NotificationMessage message = messageRepository
                .findById(messageId)
                .orElseThrow(() -> new MessageNotFoundException("Notification not found"));
        notification.setNotificationType(CommunicationType.valueOf(notificationType));
        notification.setRecipient(recipient);
        notification.setSentDateTime(LocalDateTime.now());
        notification.setStatus(NotificationStatus.PENDING); // Initially set to pending
        return notificationRepository.save(notification);
    }

    @Transactional
    public void updateNotificationStatus(Long notificationId, NotificationStatus newStatus) {
        Notification notification = notificationRepository
                .findById(notificationId)
                .orElseThrow(() -> new NotificationNotFoundException("Notification not found"));

        notification.setStatus(newStatus);
        notificationRepository.save(notification);
    }

    @Transactional(readOnly = true)
    public List<Notification> getAllNotificationsForRecipient(String recipient) {
        return notificationRepository.findByRecipient(recipient);
    }

    @Transactional(readOnly = true)
    public List<Notification> getNotificationsByStatus(NotificationStatus status) {
        return notificationRepository.findByStatus(status);
    }

    @Transactional(readOnly = true)
    public Map<NotificationStatus, NotificationSuccessRate> getNotificationDeliverySuccessRates() {
        long total = notificationRepository.count();
        long delivered = notificationRepository.countByStatus(NotificationStatus.DELIVERED);
        long failed = notificationRepository.countByStatus(NotificationStatus.FAILED);
        long pending = notificationRepository.countByStatus(NotificationStatus.PENDING);

        double percentDelivered = (double) delivered / total * 100;
        double percentFailed = (double) failed / total * 100;
        double percentPending = (double) pending / total * 100;

        return Map.of(
                NotificationStatus.DELIVERED, new NotificationSuccessRate(percentDelivered, delivered),
                NotificationStatus.FAILED, new NotificationSuccessRate(percentFailed, failed),
                NotificationStatus.PENDING, new NotificationSuccessRate(percentPending, pending)
        );
    }

    @Transactional(readOnly = true)
    public List<Notification> getNotificationsByStatusForCustomer(Long customerId, NotificationStatus status) {
        List<String> contactValues = addressRepository
                .findByCustomerId(customerId)
                .stream()
                .map(Address::getContactValue)
                .collect(Collectors.toList());
        return notificationRepository.findByRecipientInAndStatus(contactValues, status);
    }

    @Transactional(readOnly = true)
    public Map<NotificationStatus, NotificationSuccessRate> getNotificationDeliverySuccessRatesForCustomer(Long customerId) {
        List<String> contactValues = addressRepository
                .findByCustomerId(customerId)
                .stream()
                .map(Address::getContactValue)
                .collect(Collectors.toList());

        long delivered = notificationRepository.countByRecipientInAndStatus(contactValues,
                                                                            NotificationStatus.DELIVERED);
        long failed = notificationRepository.countByRecipientInAndStatus(contactValues, NotificationStatus.FAILED);
        long pending = notificationRepository.countByRecipientInAndStatus(contactValues, NotificationStatus.PENDING);
        long total = delivered + failed + pending;

        double percentDelivered = (double) delivered / total * 100;
        double percentFailed = (double) failed / total * 100;
        double percentPending = (double) pending / total * 100;

        return Map.of(
                NotificationStatus.DELIVERED, new NotificationSuccessRate(percentDelivered, delivered),
                NotificationStatus.FAILED, new NotificationSuccessRate(percentFailed, failed),
                NotificationStatus.PENDING, new NotificationSuccessRate(percentPending, pending)
        );
    }
}
