# E-Commerce Microservices Application

## 1. About the Project
This project is a backend **E-Commerce application built using Spring Boot microservices**.  
It follows an industry-standard architecture using **service discovery, asynchronous messaging, and loosely coupled services**.  
The application is developed incrementally, and only stable, working features are pushed to the repository.

---

## 2. Project Structure

1. **Eureka Discovery Server**  
   Service registry that enables dynamic discovery of all microservices.

2. **API Gateway**  
   Central entry point for routing requests to microservices (security integration planned).

3. **Auth Service**  
   Handles authentication and authorization (Spring Security integration planned).

4. **Product Service**  
   Manages product information and inventory. Added Role based Authentication.

5. **Order Service**  
   Handles order creation and publishes order events to Kafka. Added Role based Authentication.

6. **Payment Service**  
   Consumes order events from Kafka and processes payments.

7. **Notification Service** *(planned)*  
   Listens to payment events and handles user notifications.

---

## 3. Tech Stack Requirements

- Java 17+
- Spring Boot
- Spring Cloud (Eureka, Gateway)
- Spring Data JPA
- Apache Kafka
- Maven

---

## 4. Database

- **MySQL**
- Used for persistent storage of:
  - Products
  - Orders
  - Users (planned)

---

## 5. Kafka & Zookeeper

- **Apache Kafka** is used for asynchronous, event-driven communication.
- **Zookeeper** is used for Kafka cluster coordination.
- Current Kafka topic:
  - `order-created` (produced by Order Service, consumed by Payment Service)

---

## 6. Redis

- Redis is planned for:
  - Caching frequently accessed data (products)
  - Session management during Spring Security integration

---

## 7. Current Status

- Core microservices implemented
- Eureka service discovery working
- Kafka producer and consumer working
- Order → Payment event flow verified
- Security temporarily disabled for faster development

---


# Project Features

- All services register with the Eureka Server
- Every request passes through the API Gateway
- Services allow access based on authentication and authorization
- Kafka is used for asynchronous communication
- Redis is used for cache management
- Spring Session–based authentication is implemented
- Users can view products and create orders
- Payment service issues payment success by default after order creation
- More features will be added in future


## Author
Ashok Gogulapati
