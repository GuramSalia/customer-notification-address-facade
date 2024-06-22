The entire content, including both the project overview and the setup instructions (including the database setup), should be part of the `README.md` file. The `README.md` serves as a comprehensive guide for anyone who wants to understand, set up, and run your project.

Here is the full `README.md` content for clarity:

```markdown
# Customer Notification Address Facade

## Project Overview
The Customer Notification Address Facade is a microservice designed to centralize and manage customer contact information and preferences for notifications. This system acts as a single source of truth for all recipient addresses and delivery statuses, helping other systems in the ecosystem to fetch and update customer delivery data efficiently.

## Features
- **User Management:** Create and manage admin accounts, secure login.
- **Customer Management:** Add, edit, update, remove customers; display customer list.
- **Address Management:** Store and manage customer addresses; update and remove addresses; display address list.
- **Preference Management:** Record and manage notification preferences; update preferences; display preferences.
- **Integration and API:** RESTful API for accessing customer data; secure and authenticated access; batch updates.
- **Notification Tracking:** Track notification status; provide endpoints for querying statuses; generate delivery reports.
- **Search and Filtering:** Search and filter customers based on criteria.
- **Reporting:** Generate customer data reports; provide notification statistics.

## Setup Instructions

### Prerequisites
- Java 21
- Maven
- PostgreSQL (for production)
- H2 Database (for development)

### Clone the Repository
```bash
git clone https://github.com/GuramSalia/customer-notification-address-facade.git
cd customer-notification-address-facade
```

### Database Setup
#### Development (H2 Database)
The `application-dev.properties` file is configured to use an in-memory H2 database.

#### Production (PostgreSQL)
1. Install PostgreSQL and create a database named `CROCO`.
2. Update `spring.datasource.username` and `spring.datasource.password` in `application-prod.properties` with your PostgreSQL credentials.

### Running the Application

#### Build the Project
```bash
mvn clean install
```

#### Run the Application
```bash
mvn spring-boot:run
```

### Accessing the Application
- For development, access the H2 console at `http://localhost:8080/h2-console`.
- The application's main interface will be accessible at `http://localhost:8080`.

## Project Structure
- `src/main/java/com/crocobet`: Contains the main application code.
- `src/main/resources`: Contains application properties and schema SQL.
- `src/test/java/com/crocobet`: Contains test cases.

## Security
- **Spring Security** is used to manage authentication and authorization.
- User credentials are stored securely using Spring Security's crypto module.

## API Documentation
- Swagger/OpenAPI documentation is available at `http://localhost:8080/swagger-ui.html`.

## Actuator Endpoints
- Actuator endpoints are available at `http://localhost:8080/actuator`.

## Further Documentation
For more details on the project's design choices, see the `docs` directory.

