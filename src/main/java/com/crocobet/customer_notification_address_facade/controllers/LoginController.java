package com.crocobet.customer_notification_address_facade.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
    @RequestMapping(value = "login", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView displayLoginPage(@RequestParam(value = "error", required = false) String error,
                                         @RequestParam(value = "logout", required = false) String logout,
                                         @RequestParam(value = "register", required = false) String register) {

        ModelAndView modelAndView = new ModelAndView("login.html");
        String errorMessage = null;
        boolean isMessage = false;

        if (error != null) {
            errorMessage = "Username or Password is incorrect!";
            isMessage = true;
        } else if (logout != null) {
            errorMessage = "You have been successfully logged out!";
            isMessage = true;
        } else if (register != null) {
            errorMessage = "You registration is successful. Login with registered credentials!";
            isMessage = true;
        }

        modelAndView.addObject("errorMessage", errorMessage);
        modelAndView.addObject("isMessage", isMessage);

        return modelAndView;
    }

    @GetMapping(value = "/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout=true";
    }
}
