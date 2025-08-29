# 📘 Logging Microservice
The **Logging Microservice** is responsible for centralized logging across the system.
It acts as a **Kafka consumer,** listening to a **logging topic,** and stores structured log data in a **local database (dump table)** for auditing, debugging, and monitoring purposes.

Every other microservice sends **reques**t and **response logs** to Kafka via a producer, while this microservice consumes, parses, and persists them.
## 🚀 Features
- Consumes logs from a Kafka topic.

- Parses log messages into structured data.

- Stores logs in a centralized dump_logs table.

- Supports Request and Response log types.
----
## 📤 Log Message Format (Produced by Microservices)
Each microservice sends logs to Kafka in the following **JSON format:**
```json
{
  "message": "<escaped JSON request or response>",
  "messageType": "Request | Response",
  "dateTime": "2025-08-29T14:30:00"
}
```
- **message →** The actual request/response body (escaped JSON)
- **messageType →** `"Request"` or `"Response"`.
- **dateTime →** Timestamp when the log was generated.
## 📥 Logging Flow
- A microservice processes an incoming request.

- Before sending a response, it prepares a log message.

- The log message is published to the **Kafka logging topic.**

- The Logging Microservice consumes this message.

- It parses the log and inserts it into the **dump_logs** table.
  

## 🛠️ Tech Stack 
- Spring Boot

- Apache Kafka

- H2

- JPA/Hibernate
