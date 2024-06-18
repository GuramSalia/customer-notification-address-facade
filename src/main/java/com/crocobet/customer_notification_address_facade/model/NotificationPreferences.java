package com.crocobet.customer_notification_address_facade.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "NOTIFICATION_PREFERENCES")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationPreferences {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @OneToOne
    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    private Customer customer;

    @Column(name = "OPT_IN_SMS", nullable = false)
    private boolean optInSms;

    @Column(name = "OPT_IN_EMAIL", nullable = false)
    private boolean optInEmail;

    @Column(name = "OPT_IN_PROMOTIONAL_MESSAGES", nullable = false)
    private boolean optInPromotionalMessages;
}
