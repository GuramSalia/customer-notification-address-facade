# System Overview

## Purpose
The Customer Notification Address Facade is designed to centralize and manage customer contact information and notification preferences. It acts as a single source of truth for recipient addresses and delivery statuses, streamlining the process of fetching and updating customer delivery data across different systems within the ecosystem.

## Architecture
The system follows a microservice architecture, consisting of several components:
- **API Gateway:** Handles incoming requests and routes them to the appropriate services.
- **User Management Service:** Manages admin accounts and handles authentication and authorization.
- **Customer Service:** Manages customer data, including creation, updating, and retrieval of customer information.
- **Address Service:** Stores and manages customer addresses.
- **Preference Service:** Manages customer notification preferences.
- **Notification Service:** Tracks notification statuses and handles notification delivery.
- **Database:** PostgreSQL for production and H2 for development, storing all the necessary data.

The services communicate with each other using RESTful APIs, ensuring loose coupling and scalability.

## Key Technologies
- **Java 21:** The programming language used for the implementation.
- **Spring Boot:** Framework used to create stand-alone, production-grade Spring-based applications.
- **Spring Security:** Provides authentication and authorization.
- **Spring Data JPA:** Simplifies database access and management.
- **PostgreSQL:** The primary database used in the production environment.
- **H2 Database:** In-memory database used for development and testing.
- **Swagger/OpenAPI:** For API documentation.
- **Actuator:** For monitoring and managing the application.

Data Model
1. User Attributes:

id: Long (Primary Key)
username: String (Unique, Not Null)
password: String (Not Null)
role: String (Not Null)

Details:
Implements UserDetails interface from Spring Security.
Manages authentication and authorization via Spring Security roles.

2. Customer Attributes:

id: Long (Primary Key)
username: String (Not Null)

Relationships:
contactInfo: One-to-Many relationship with Address entity.
notificationPreferences: One-to-One relationship with NotificationPreferences entity.

Details:
Stores customer-specific information.
Manages associated addresses and notification preferences.

3. Address Attributes:

id: Long (Primary Key)
addressType: Enum CommunicationType (Not Null)
contactValue: String (Not Null)

Relationships:
customer: Many-to-One relationship with Customer entity.
Details:

Represents various contact addresses associated with a customer.
Uses CommunicationType to distinguish between email, SMS, and postal addresses.

4. Notification Attributes:

id: Long (Primary Key)
notificationType: Enum CommunicationType (Not Null)
recipient: String (Not Null)
message: Many-to-One relationship with NotificationMessage entity.
sentDateTime: LocalDateTime
status: Enum NotificationStatus (Not Null)

Relationships:
message: Many-to-One relationship with NotificationMessage entity.

Details:
Tracks notifications sent to customers.
Stores details such as type, recipient, message, timestamp, and status.
5. NotificationMessage
   Attributes:

id: Long (Primary Key)
message: String
Relationships:

None.
Details:

Stores the actual message content for notifications.
6. NotificationPreferences
   Attributes:

id: Long (Primary Key)
optInSms: boolean (Not Null)
optInEmail: boolean (Not Null)
optInPromotionalMessages: boolean (Not Null)
Relationships:

customer: One-to-One relationship with Customer entity.
Details:

Stores customer preferences for receiving notifications.
Controls opt-in status for SMS, email, and promotional messages.

Architecture
1. High-Level Overview
   The Customer Notification Address Facade is designed as a microservice to centralize and manage customer contact information, notification preferences, and communication status. This microservice architecture is chosen for its modularity, scalability, and flexibility in handling various aspects of customer data management and notification delivery.

2. Components
   Backend
   Controllers:

The com.crocobet.customer_notification_address_facade.controllers package houses controllers responsible for handling incoming HTTP requests and delegating them to the appropriate service methods.
Controllers manage endpoints related to user management, customer operations, address management, notification preferences, and reporting.
Services:

Services in com.crocobet.customer_notification_address_facade.services encapsulate business logic and orchestrate interactions between controllers and repositories.
Each service class focuses on specific functionalities such as user authentication, customer CRUD operations, notification tracking, and preference management.
Repositories:

Repository classes located in com.crocobet.customer_notification_address_facade.repositories manage data access and persistence using Spring Data JPA.
These repositories define methods for querying and manipulating entities like users, customers, addresses, notifications, and notification messages.
Database
H2 for Development:

During development, an in-memory H2 database is utilized (application-dev.properties) for rapid prototyping and testing.
H2 provides quick setup and teardown of data, facilitating agile development practices.
PostgreSQL for Production:

In production (application-prod.properties), PostgreSQL is employed as the relational database management system.
PostgreSQL ensures data durability, scalability, and robust transactional support required for handling production-level workloads.
External Dependencies
The application integrates with external systems for various functionalities, including:
Authentication: Leveraging Spring Security for user authentication and authorization.
NotificationService in package com.crocobet.customer_notification_address_facade.services has two methods: sendNotification and updateNotificationStatus which simulate  integration with external APIs or services for sending notifications via email, SMS, or other communication channels.
3. Design Patterns
   Repository Pattern:

Repositories abstract database interactions, providing a uniform interface for data access operations across entities.
This pattern enhances maintainability and facilitates unit testing of data access logic.
Service Layer Pattern:

Services encapsulate business logic and coordinate complex operations involving multiple repositories.
They ensure separation of concerns and promote reusability of business rules across different parts of the application.
DTO Pattern:

DTOs (Data Transfer Objects) located in com.crocobet.customer_notification_address_facade.dto facilitate data exchange between frontend (HTML forms) and backend services.
They help in decoupling the frontend from backend domain models and provide a clear contract for data transfer.4. Security
Authentication and Authorization
Authentication in the Customer Notification Address Facade is handled using form-based authentication with username-password credentials. Passwords are securely encrypted using BCryptPasswordEncoder to protect user credentials from unauthorized access.

Authorization
Role-based access control (RBAC) ensures that authenticated users are authorized to access resources based on their assigned roles and permissions. Access to different parts of the application is managed through Spring Security configurations, specifying which endpoints are accessible to users with specific roles.

Implementation
The security configuration (SecurityConfig.java) defines security rules and mechanisms:
```

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(req -> req
                    // Permit access to specific endpoints without authentication
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
                    .requestMatchers("/login").permitAll() // Allow access to login page
                    .requestMatchers("/", "/home").permitAll() // Allow access to home page
                    .requestMatchers("/assets/**").permitAll() // Allow access to assets like CSS and JS
                    .anyRequest().authenticated() // Require authentication for all other requests
            )
            .formLogin(formLogin -> formLogin
                    .loginPage("/login") // Specify custom login page
                    .defaultSuccessUrl("/dashboard") // Redirect to dashboard after successful login
                    .failureUrl("/login?error=true").permitAll()) // Handle login failure
            .logout(logout -> logout
                    .logoutSuccessUrl("/login?logout=true") // Redirect to login page after logout
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .addLogoutHandler((request, response, authentication) -> {
                        SecurityContextHolder.clearContext(); // Clear security context on logout
                    })
                    .permitAll())
            .headers(headers -> headers.frameOptions(Customizer.withDefaults()).disable()); // Disable frame options

        return http.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(); 
    }
}
```
This configuration ensures secure user authentication, authorization based on roles, and protection against common security threats like cross-site scripting (XSS) and cross-site request forgery (CSRF).


5. APIs and Integrations
   RESTful APIs:

The microservice exposes RESTful APIs (@RestController annotated classes) that adhere to industry best practices for resource management.
APIs support operations such as user login, customer CRUD operations, address management, notification preferences, and reporting functionalities.
Integration Points:


6. Scalability and Performance
   Scalability:

Designed for horizontal scalability, the microservice architecture supports load balancing and clustering to handle increased traffic and data volume.
Vertical scalability considerations include optimizing database queries and enhancing service performance through caching mechanisms.
Performance Considerations:

Performance optimizations include efficient database indexing, minimizing network latency in API calls, and asynchronous processing for non-blocking operations.
Caching strategies are employed to reduce repetitive data fetching and improve response times for frequently accessed resources.
