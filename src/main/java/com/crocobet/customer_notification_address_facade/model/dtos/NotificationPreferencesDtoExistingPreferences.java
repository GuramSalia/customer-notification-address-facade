package com.crocobet.customer_notification_address_facade.model.dtos;

import lombok.Data;

@Data
public class NotificationPreferencesDtoExistingPreferences {
    private Long id;
    private Long customerId;
    private Boolean optInSms;
    private Boolean optInEmail;
    private Boolean optInPromotionalMessages;
}
