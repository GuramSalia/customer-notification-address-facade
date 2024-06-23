package com.crocobet.customer_notification_address_facade.security;

import com.crocobet.customer_notification_address_facade.model.User;
import com.crocobet.customer_notification_address_facade.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Autowired
    public CustomAuthenticationProvider(

            PasswordEncoder passwordEncoder,
            UserService userService
    ) {

        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("CustomAuthenticationProvider ... authenticating");

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userService.getUserByUsername(username);
        if (user == null) {
            return null;
        }

        boolean passwordMatches = passwordEncoder.matches(password, user.getPassword());
        log.info("\n\nCustomAuthenticationProvider, role: {}\n\n", user.getRole());

        if (user.getId() > 0 && passwordMatches) {
            return new UsernamePasswordAuthenticationToken(username, password, getGrantedAuthorities(user.getRole()));
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private List<GrantedAuthority> getGrantedAuthorities(String role) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        return grantedAuthorities;
    }
}
