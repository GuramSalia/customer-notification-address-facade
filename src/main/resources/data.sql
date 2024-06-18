INSERT INTO APP_USERS (USERNAME, PASSWORD, ROLE) VALUES
('john123', '123', 'admin'),
('sam456', '456', 'admin');

INSERT INTO CUSTOMERS (USERNAME) VALUES
('customer1'),
('customer2');

INSERT INTO ADDRESSES (CUSTOMER_ID, TYPE, ADDRESS_VALUE) VALUES
(1, 'EMAIL', 'customer1@example.com'),
(1, 'SMS', '+1234567890'),
(2, 'EMAIL', 'customer2@example.com'),
(2, 'POSTAL', '123 Main St');

INSERT INTO NOTIFICATION_PREFERENCES (CUSTOMER_ID, OPT_IN_SMS, OPT_IN_EMAIL, OPT_IN_PROMOTIONAL_MESSAGES) VALUES
(1, true, true, false),
(2, false, true, true);