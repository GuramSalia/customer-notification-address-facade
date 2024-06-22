package com.crocobet.customer_notification_address_facade.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ADDRESSES")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    @JsonIgnore
    private Customer customer;

    @Column(name = "TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private CommunicationType addressType;

    @Column(name = "CONTACT_VALUE", nullable = false)
    private String contactValue;

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", customerId=" + customer.getId() +
                ", addressType=" + addressType +
                ", contactValue='" + contactValue + '\'' +
                '}';
    }
}
