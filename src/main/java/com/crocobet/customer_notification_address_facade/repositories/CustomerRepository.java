package com.crocobet.customer_notification_address_facade.repositories;

import com.crocobet.customer_notification_address_facade.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
