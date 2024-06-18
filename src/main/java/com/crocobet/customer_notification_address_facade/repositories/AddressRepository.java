package com.crocobet.customer_notification_address_facade.repositories;

import com.crocobet.customer_notification_address_facade.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByCustomerId(Long customerId);
}
