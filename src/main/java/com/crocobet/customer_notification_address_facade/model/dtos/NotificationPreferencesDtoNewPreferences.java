package com.crocobet.customer_notification_address_facade.model.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NotificationPreferencesDtoNewPreferences {
    private Long customerId;
    private Boolean optInSms;
    private Boolean optInEmail;
    private Boolean optInPromotionalMessages;
}
