package com.crocobet.customer_notification_address_facade.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationSuccessRate {
    private double percentage;
    private long count;

    @Override
    public String toString() {
        return "NotificationSuccessRate{" +
                "percentage=" + percentage +
                "%, count=" + count +
                '}';
    }
}
