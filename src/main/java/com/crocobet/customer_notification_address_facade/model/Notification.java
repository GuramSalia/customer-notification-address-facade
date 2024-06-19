package com.crocobet.customer_notification_address_facade.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "NOTIFICATIONS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private CommunicationType notificationType;

    @Column(name = "RECIPIENT")
    private String recipient; // contactValue in Address entity


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MESSAGE_ID", nullable = false)
    private NotificationMessage message;

    @Column(name = "SENT_DATE_TIME")
    private LocalDateTime sentDateTime;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private NotificationStatus status;
}
