-- password is 3 digits at the end of the username
INSERT INTO APP_USERS (USERNAME, PASSWORD, ROLE) VALUES
('john123', '$2a$10$8XTgX6odxEG2REY5AaWhhOQ1lyPCklVxjtE6N8VnZ0hGe/hOD41cW', 'ADMIN'),
('sam456', '$2a$10$NdnSNmRwgnI1QGtb9XBXzein0VHLWNn.SN8eCvflTYmitBNt38IJC', 'ADMIN');

INSERT INTO CUSTOMERS (USERNAME) VALUES
('w-customer1'),
('z-customer2'),
('v-customer3'),
('d-customer4'),
('e-customer5'),
('f-customer6'),
('g-customer7'),
('h-customer8'),
('i-customer9'),
('j-customer10'),
('k-customer11'),
('l-customer12'),
('m-customer13'),
('n-customer14'),
('o-customer15');

INSERT INTO ADDRESSES (CUSTOMER_ID, TYPE, CONTACT_VALUE) VALUES
(1, 'EMAIL', 'customer1@example.com'),
(1, 'SMS', '+1234567890'),
(2, 'EMAIL', 'customer2@example.com'),
(2, 'POSTAL', '123 Main St');

INSERT INTO NOTIFICATION_PREFERENCES (CUSTOMER_ID, OPT_IN_SMS, OPT_IN_EMAIL, OPT_IN_PROMOTIONAL_MESSAGES) VALUES
(1, true, true, false),
(2, false, true, true),
(3, true, false, false),
(4, true, true, false),
(5, true, true, false),
(6, true, true, false),
(7, true, true, false),
(8, true, true, false),
(9, true, true, false),
(10, true, true, false),
(11, true, true, false),
(12, true, true, false),
(13, true, true, false),
(14, true, true, false),
(15, true, true, false);

INSERT INTO NOTIFICATION_MESSAGES (MESSAGE) VALUES
('email message 1'),
('email message 2'),
('SMS message 3'),
('email message 4'),
('SMS message 5'),
('email message 6'),
('email message 7'),
('email message 8');

INSERT INTO NOTIFICATIONS (TYPE, RECIPIENT, MESSAGE_ID, SENT_DATE_TIME, STATUS) VALUES
('EMAIL', 'customer1@example.com', 1, '2023-06-17 10:00:00', 'DELIVERED'),
('EMAIL', 'customer2@example.com', 1, '2023-06-17 10:00:00', 'DELIVERED'),
('EMAIL', 'customer1@example.com', 2, '2023-06-17 10:10:00', 'DELIVERED'),
('EMAIL', 'customer2@example.com', 2, '2023-06-17 10:10:00', 'DELIVERED'),
('SMS', '+1234567890', 3, '2023-06-17 11:00:00', 'DELIVERED'),
('EMAIL', 'customer1@example.com', 4, '2023-06-18 10:00:00', 'PENDING'),
('EMAIL', 'customer2@example.com', 4, '2023-06-18 10:00:00', 'DELIVERED'),
('SMS', '+1234567890', 5, '2023-06-18 11:00:00', 'FAILED'),
('EMAIL', 'customer2@example.com', 6, '2023-06-18 11:20:00', 'FAILED'),
('EMAIL', 'customer2@example.com', 7, '2023-06-18 11:40:00', 'DELIVERED');