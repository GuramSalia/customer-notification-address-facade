package com.crocobet.customer_notification_address_facade.model.dtos;

import lombok.Data;

@Data
public class AddressDto {
    private long id;
    private String addressType;
    private String contactValue;
}
