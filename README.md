# Credit Application

This project is a credit management system that allows users to create and manage credits with features such as installment calculation, weekend handling, credit listing, pagination, and payment processing. The application is built using Java, Spring Boot, Kafka, Redis, and MySQL, and is containerized with Docker.

## General Features

- Credit Creation: Users can create credits with customizable installment plans.
- Installment Calculation: Installment due dates are calculated by adding 30 days to the creation date. If the due date falls on a weekend, it is moved to the next business day.
- Credit Listing: All credits can be listed, and user-specific credits can be filtered and paginated.
- Payment Processing: Payments can be made either in full or in partial installments.
- Late Fee Calculation: Late payments incur a late fee, calculated automatically by the system.
- Caching: Redis is used to cache frequently accessed data, improving performance.
- Message Queue: Kafka is used for internal communication between microservices, ensuring scalability and reliability.

## Technologies Used

- Java 17: A modern, performant, and up-to-date language used in the application.
- Spring Boot: A framework for building Spring-based applications quickly and easily.
- Docker: A containerization platform for quick deployment and running of the application.
- Elasticsearch: An open-source search engine used for high-performance search, analytics, and data storage.
- MySQL: A relational database management system used for data storage and management.

## Installation
- make build: Builds the Docker image for the application.
- make up: Starts the application in detached mode.
- make down: Stops and removes the containers created by the application.
- make health: Builds the health-check Docker image

## System Architecture
![image](https://github.com/user-attachments/assets/5057102f-2e56-4b4d-9d80-ec0aff41fdb4)

## Additional Features (Optional)

In addition to the core features mentioned above, you can consider adding the following features to enhance the functionality of the movie review application:

- Advanced Security and Authorization System: Implement a robust security and authorization system to protect user data and prevent unauthorized access. Use authentication mechanisms such as OAuth or JSON Web Tokens (JWT) for secure user authentication and authorization.

- Implementing Event-Driven Architecture with Debezium: By incorporating technologies such as Debezium, the system can implement an event-driven architecture. One approach to achieve this is by implementing the Outbox Pattern, where events are persisted in a database table (outbox table) and then consumed asynchronously by event processors. This approach allows for reliable event publishing and can enhance the scalability and reliability of the system.

- Rate Limiter: Implement a rate limiter to prevent abuse and protect the application from excessive requests. This feature can help control the number of requests made by a user within a specific time frame, ensuring fair usage and preventing misuse or system overload.

- Microservice Architecture: Consider refactoring the application into microservices to improve scalability, maintainability, and flexibility. By breaking down the application into smaller, independent services, you can achieve better separation of concerns and enable each service to be developed, deployed, and scaled independently.
