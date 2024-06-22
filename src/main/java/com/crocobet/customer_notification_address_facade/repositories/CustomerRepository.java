package com.crocobet.customer_notification_address_facade.repositories;

import com.crocobet.customer_notification_address_facade.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByUsernameContaining(String keyword);

    @Query("SELECT c FROM Customer c JOIN c.contactInfo a WHERE a.contactValue LIKE %:contact%")
    List<Customer> findByContactInfo(@Param("contact") String contact);

    @Query("SELECT c FROM Customer c JOIN c.notificationPreferences np WHERE np.optInEmail = :optInEmail")
    Page<Customer> findByNotificationPreferencesOptInEmail(boolean optInEmail, Pageable pageable);

    @Query("SELECT c FROM Customer c JOIN c.notificationPreferences np WHERE np.optInSms = :optInSms")
    Page<Customer> findByNotificationPreferencesOptInSms(@Param("optInSms") boolean optInSms, Pageable pageable);

    @Query("SELECT c FROM Customer c JOIN c.notificationPreferences np WHERE np.optInPromotionalMessages = " +
            ":optInPromotionalMessages")
    Page<Customer> findByNotificationPreferencesOptInPromotionalMessages(
            @Param("optInPromotionalMessages") boolean optInPromotionalMessages, Pageable pageable
    );

    long countByNotificationPreferencesOptInEmail(boolean optedIn);

    long countByNotificationPreferencesOptInSms(boolean optedIn);

    long countByNotificationPreferencesOptInPromotionalMessages(boolean optedIn);
}
