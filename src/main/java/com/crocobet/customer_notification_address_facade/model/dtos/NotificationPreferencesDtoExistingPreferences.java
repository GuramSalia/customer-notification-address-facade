package com.crocobet.customer_notification_address_facade.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationPreferencesDtoExistingPreferences {
    private Long id;
    private Long customerId;
    private Boolean optInSms;
    private Boolean optInEmail;
    private Boolean optInPromotionalMessages;
}
