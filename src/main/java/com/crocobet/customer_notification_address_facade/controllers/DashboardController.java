package com.crocobet.customer_notification_address_facade.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @GetMapping
    public ModelAndView displayDashboard() {
        return new ModelAndView("dashboard.html");
    }
}
