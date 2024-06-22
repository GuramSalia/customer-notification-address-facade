package com.crocobet.customer_notification_address_facade.security;

import com.crocobet.customer_notification_address_facade.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public MyUserDetailsService(UserService userService) {this.userService = userService;}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.crocobet.customer_notification_address_facade.model.User user = userService.getUserByUsername(username);
        String role = user.getRole();
        return User.builder()
                   .username(user.getUsername())
                   .password(user.getPassword())
                   .roles(role)
                   .build();
    }
}
