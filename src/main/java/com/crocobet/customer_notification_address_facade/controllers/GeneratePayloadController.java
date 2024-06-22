package com.crocobet.customer_notification_address_facade.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/generate-payload")
public class GeneratePayloadController {
    @GetMapping("/user-create")
    public String getUserCreatePayloadPage() {
        return "payload-generator-for-user-create";
    }

    @GetMapping("/user-update")
    public String getUserUpdatePayloadPage() {
        return "payload-generator-for-user-update";
    }

    @GetMapping("/user-delete")
    public String getUserDeletePayloadPage() {
        return "payload-generator-for-user-delete";
    }

    @GetMapping("/user-get-by-username")
    public String getUserGetByUsernamePayloadPage() {
        return "payload-generator-for-user-get-by-username";
    }

    @GetMapping("/user-get-by-id")
    public String getUserGetByIdPayloadPage() {
        return "payload-generator-for-user-get-by-id";
    }

    @GetMapping("/preferences-create")
    public String getPreferencesCreatePayloadPage() {
        return "payload-generator-for-preferences-create";
    }

    @GetMapping("/preferences-update")
    public String getPreferencesUpdatePayloadPage() {
        return "payload-generator-for-preferences-update";
    }

    @GetMapping("/preferences-delete")
    public String getPreferencesDeletePayloadPage() {
        return "payload-generator-for-preferences-delete";
    }

    @GetMapping("/preferences-get-by-customer")
    public String getPreferencesGetByCustomerPayloadPage() {
        return "payload-generator-for-preferences-get-by-customer";
    }

    @GetMapping("/customer-create")
    public String getCustomerCreatePayloadPage() {
        return "payload-generator-for-customer-create";
    }

    @GetMapping("/customer-update")
    public String getCustomerUpdatePayloadPage() {
        return "payload-generator-for-customer-update";
    }

    @GetMapping("/customer-delete")
    public String getCustomerDeletePayloadPage() {
        return "payload-generator-for-customer-delete";
    }

    @GetMapping("/address-create")
    public String getAddressCreatePayloadPage() {
        return "payload-generator-for-address-create";
    }

    @GetMapping("/address-update")
    public String getAddressUpdatePayloadPage() {
        return "payload-generator-for-address-update";
    }

    @GetMapping("/address-delete")
    public String getAddressDeletePayloadPage() {
        return "payload-generator-for-address-delete";
    }

    @GetMapping("/address-get-for-customer")
    public String getAddressGetForCustomerPayloadPage() {
        return "payload-generator-for-address-get-for-customer";
    }

    @GetMapping("/notifications-by-status")
    public String getNotificationsByStatusPayloadPage() {
        return "payload-generator-for-notifications-by-status";
    }

    @GetMapping("/notifications-by-status-and-customer")
    public String getNotificationsByStatusAndCustomerPayloadPage() {
        return "payload-generator-for-notifications-by-status-and-customer";
    }

    @GetMapping("/notifications-by-customer")
    public String getNotificationsByCustomerPayloadPage() {
        return "payload-generator-for-notifications-by-customer";
    }

    @GetMapping("/notifications-by-address")
    public String getNotificationsByAddressPayloadPage() {
        return "payload-generator-for-notifications-by-address";
    }

    // TODO: html documents

    @GetMapping("/customers/search")
    public String getCustomerByNamePayloadPage() {
        return "payload-generator-for-customer-get-by-name";
    }

    @GetMapping("/customers/search/contact")
    public String getCustomerByContactPayloadPage() {
        return "payload-generator-for-customer-get-by-contact";
    }

    @GetMapping("/customers/report/opted-in-email")
    public String getCustomerReportOptedInEmailPayloadPage() {
        return "payload-generator-for-customer-report-opted-is-for-email";
    }

    @GetMapping("/customers/report/opted-in-sms")
    public String getCustomerReportOptedInSmsPayloadPage() {
        return "payload-generator-for-customer-report-opted-is-for-sms";
    }

    @GetMapping("/customers/report/opted-in-promotional-messages")
    public String getCustomerReportOptedInPromotionalMessagesPayloadPage() {
        return "payload-generator-for-customer-report-opted-is-for-promotional-messages";
    }

    @GetMapping("/customers/report/pagination/opted-in-email")
    public String getCustomerReportPaginationOptedInEmailPayloadPage() {
        return "payload-generator-for-customer-report-pagination-opted-is-for-email";
    }

    @GetMapping("/customers/report/pagination/opted-in-sms")
    public String getCustomerReportPaginationOptedInSmsPayloadPage() {
        return "payload-generator-for-customer-report-pagination-opted-is-for-sms";
    }

    @GetMapping("/customers/report/pagination/opted-in-promotional-messages")
    public String getCustomerReportPaginationOptedInPromotionalMessagesPayloadPage() {
        return "payload-generator-for-customer-report-pagination-opted-is-for-promotional-messages";
    }

}
