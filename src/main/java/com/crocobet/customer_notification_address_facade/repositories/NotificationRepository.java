package com.crocobet.customer_notification_address_facade.repositories;

import com.crocobet.customer_notification_address_facade.model.Notification;
import com.crocobet.customer_notification_address_facade.model.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByRecipient(String recipient);

    List<Notification> findByStatus(NotificationStatus status);

    List<Notification> findByRecipientInAndStatus(List<String> recipients, NotificationStatus status);

    long countByStatus(NotificationStatus status);

    long countByRecipientInAndStatus(List<String> recipients, NotificationStatus status);
}
