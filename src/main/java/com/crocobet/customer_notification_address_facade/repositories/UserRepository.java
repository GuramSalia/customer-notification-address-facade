package com.crocobet.customer_notification_address_facade.repositories;

import com.crocobet.customer_notification_address_facade.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
