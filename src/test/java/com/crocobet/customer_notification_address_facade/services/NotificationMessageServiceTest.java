package com.crocobet.customer_notification_address_facade.services;

import com.crocobet.customer_notification_address_facade.exceptions.MessageNotFoundException;
import com.crocobet.customer_notification_address_facade.model.NotificationMessage;
import com.crocobet.customer_notification_address_facade.repositories.NotificationMessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class NotificationMessageServiceTest {
    @Mock
    private NotificationMessageRepository notificationMessageRepository;

    @InjectMocks
    private NotificationMessageService notificationMessageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllNotificationMessages() {
        NotificationMessage message1 = new NotificationMessage(1L, "Message 1");
        NotificationMessage message2 = new NotificationMessage(2L, "Message 2");
        List<NotificationMessage> mockMessages = Arrays.asList(message1, message2);

        when(notificationMessageRepository.findAll()).thenReturn(mockMessages);

        List<NotificationMessage> messages = notificationMessageService.getAllNotificationMessages();

        assertNotNull(messages);
        assertEquals(2, messages.size());
        assertEquals("Message 1", messages.get(0).getMessage());
        assertEquals("Message 2", messages.get(1).getMessage());
        verify(notificationMessageRepository, times(1)).findAll();
    }

    @Test
    void testGetNotificationMessageById() {
        // Mock data
        NotificationMessage message = new NotificationMessage(1L, "Test Message");

        when(notificationMessageRepository.findById(1L)).thenReturn(Optional.of(message));
        when(notificationMessageRepository.findById(2L)).thenReturn(Optional.empty());

        NotificationMessage foundMessage = notificationMessageService.getNotificationMessageById(1L);
        assertNotNull(foundMessage);
        assertEquals("Test Message", foundMessage.getMessage());

        assertThrows(MessageNotFoundException.class, () -> notificationMessageService.getNotificationMessageById(2L));
        verify(notificationMessageRepository, times(1)).findById(1L);
        verify(notificationMessageRepository, times(1)).findById(2L);
    }

    @Test
    void testAddNotificationMessage() {

        NotificationMessage newMessage = new NotificationMessage(null, "New Message");
        NotificationMessage savedMessage = new NotificationMessage(1L, "New Message");

        when(notificationMessageRepository.save(any(NotificationMessage.class))).thenReturn(savedMessage);

        NotificationMessage addedMessage = notificationMessageService.addNotificationMessage(newMessage);

        assertNotNull(addedMessage);
        assertEquals(1L, addedMessage.getId());
        assertEquals("New Message", addedMessage.getMessage());
        verify(notificationMessageRepository, times(1)).save(any(NotificationMessage.class));
    }

    @Test
    void testUpdateNotificationMessage() {

        NotificationMessage existingMessage = new NotificationMessage(1L, "Existing Message");

        when(notificationMessageRepository.save(any(NotificationMessage.class))).thenReturn(existingMessage);

        NotificationMessage updatedMessage = notificationMessageService.updateNotificationMessage(existingMessage);

        assertNotNull(updatedMessage);
        assertEquals(1L, updatedMessage.getId());
        assertEquals("Existing Message", updatedMessage.getMessage());
        verify(notificationMessageRepository, times(1)).save(any(NotificationMessage.class));
    }

    @Test
    void testDeleteNotificationMessage() {

        notificationMessageService.deleteNotificationMessage(1L);

        verify(notificationMessageRepository, times(1)).deleteById(1L);
    }
}