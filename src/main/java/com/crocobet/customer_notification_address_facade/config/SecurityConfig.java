package com.crocobet.customer_notification_address_facade.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(req -> req
                    .requestMatchers(
                            "h2-console",
                            "h2-console/",
                            "h2-console/**",
                            "actuator",
                            "actuator/**",
                            "/gym-app/public/**",
                            "/swagger-ui/**",
                            "v3/api-docs/**",
                            "v3/api-docs/swagger-config",
                            "/swagger-ui/**",
                            "/swagger-ui.html",
                            "/favicon.ico").permitAll()
                    .requestMatchers(antMatcher("/h2-console/**")).permitAll()
                    .requestMatchers(antMatcher("/actuator/**")).permitAll()
                    .requestMatchers(antMatcher("/swagger-ui.html")).permitAll()
                    .requestMatchers(antMatcher("/swagger-ui/**")).permitAll()
                    .requestMatchers(antMatcher("v3/api-docs/swagger-config")).permitAll()
                    .requestMatchers(antMatcher("v3/api-docs/**")).permitAll()
                    .requestMatchers("/login").permitAll()
                    .requestMatchers("/assets/**").permitAll()
                    .requestMatchers("/css/**").permitAll()
                    .requestMatchers("/js/**").permitAll()

                    .anyRequest().authenticated()
            );

        http.formLogin(formLogin -> formLogin
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard")
                .failureUrl("/login?error=true").permitAll());
        http.logout(logout -> logout
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true).permitAll());

        http.httpBasic(Customizer.withDefaults());
        http.headers(headers -> headers.frameOptions(Customizer.withDefaults()).disable());

        return http.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {return new BCryptPasswordEncoder();}
}
