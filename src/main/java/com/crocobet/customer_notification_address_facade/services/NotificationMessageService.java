package com.crocobet.customer_notification_address_facade.services;

import com.crocobet.customer_notification_address_facade.exceptions.MessageNotFoundException;
import com.crocobet.customer_notification_address_facade.model.NotificationMessage;
import com.crocobet.customer_notification_address_facade.repositories.NotificationMessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NotificationMessageService {

    private final NotificationMessageRepository notificationMessageRepository;

    public NotificationMessageService(NotificationMessageRepository notificationMessageRepository) {
        this.notificationMessageRepository = notificationMessageRepository;
    }

    @Transactional(readOnly = true)
    public List<NotificationMessage> getAllNotificationMessages() {
        return notificationMessageRepository.findAll();
    }

    @Transactional(readOnly = true)
    public NotificationMessage getNotificationMessageById(Long id) {
        return notificationMessageRepository
               .findById(id)
               .orElseThrow(() -> new MessageNotFoundException("Notification message with ID " + id + " not found"));
    }

    @Transactional
    public NotificationMessage addNotificationMessage(NotificationMessage notificationMessage) {
        return notificationMessageRepository.save(notificationMessage);
    }

    @Transactional
    public NotificationMessage updateNotificationMessage(NotificationMessage notificationMessage) {
        return notificationMessageRepository.save(notificationMessage);
    }

    @Transactional
    public void deleteNotificationMessage(Long id) {
        notificationMessageRepository.deleteById(id);
    }
}
