package com.crocobet.customer_notification_address_facade.repositories;

import com.crocobet.customer_notification_address_facade.model.NotificationPreferences;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationPreferencesRepository extends JpaRepository<NotificationPreferences, Long> {
    NotificationPreferences findByCustomerId(Long customerId);
}
