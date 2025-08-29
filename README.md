# 🏦 Virtual Bank System

The **Virtual Bank System** is a simplified banking platform built using **Java Spring Boot Microservices** with **a Backend-for-Frontend (BFF)** layer and **WSO2 API Gateway.**
This project demonstrates modern **distributed system architecture,** including **security, scalability, API management, and logging via Kafka.**

The system provides features such as:

- User authentication & profile management
- Account creation, balance management, and transfers

- Transaction initiation & execution

- BFF service for optimized frontend responses

- Centralized API management via **WSO2 API Gateway**

- Kafka-based centralized logging service
---
## 🎯 Project Goals
- Build a **secure, scalable, and modular banking system.**
- Learn and implement:
  - **Java Microservices** (independent & loosely coupled)
  - **Backend-for-Frontend (BFF) Pattern**
  - **WSO2 API Gateway for API management**
  - **Kafka Logging** for centralized log collection
- Provide **hands-on experience** with modern enterprise architecture.

## 🏗️ Core Architecture
### 🔹 Microservices
Each banking domain is **implemented as an independent Spring Boot microservice:**

- **User Service →** Handles authentication, registration, profiles

- **Account Service →** Manages accounts, balances, scheduled inactivation job

- **Transaction Service →** Transfers, deposits, withdrawals, transaction history

- **BFF Service →** Aggregates data across microservices for frontend (portal/mobile)

- **Logging Service (Kafka Consumer) →** Collects and stores logs

### 🔹 Backend for Frontend (BFF)

- Provides optimized APIs for **Portal** and **Mobile App**

- Aggregates responses from multiple services into one response

- Reduces frontend complexity

### 🔹 WSO2 API Gateway

- Acts as a **single entry point** for external consumers

- Provides:

  - Authentication & Authorization

  - API publishing & discovery

  - Request/Response transformations

  - Throttling & rate limiting

  - Analytics & monitoring

  - Routing

## ⚙️ Technology Stack
- **Language:** Java 17

- **Framework:** Spring Boot (Microservices)

- **Build Tool:** Maven 

- **Database:** H2 

- **API Gateway:** WSO2 API Manager

- **Messaging/Logging:** Apache Kafka

- **API Testing:** Postman
## 📌 Microservices Breakdown
### 🔹 1. User Service
Handles:

- User registration

- Login & authentication

- Profile management

**Key Endpoints:**

- `POST /users/register` → Register new user

- `POST /users/login` → Authenticate user

- `GET /users/{userId}/profile` → Retrieve profile
  
### 🔹 2. Account Service

Handles:

- Account creation

- Balance management

- Transfer updates

- Scheduled job to inactivate stale accounts

**Key Endpoints:**

- `POST /accounts` → Create account

- `GET /accounts/{accountId}` → Get account details

- `PUT /accounts/transfer` → Transfer balance

- `GET /users/{userId}/accounts` → List all user accounts

### 🔹 3. Transaction Service

Handles:

- Fund transfers

- Transaction initiation & execution

- Transaction history retrieval

**Key Endpoints:**

- `POST /transactions/transfer/initiation` → Initiate transfer

- `POST /transactions/transfer/execution` → Execute transfer

- `GET /accounts/{accountId}/transactions` → Get transaction history
  
### 🔹 4. BFF Service

Handles:

- API aggregation for frontend apps

- Data transformation

- Workflow orchestration

**Key Endpoint:**

- `GET /bff/dashboard/{userId}` → Fetch profile, accounts, and recent transactions

### 🔹 5. Logging Microservice (Kafka Consumer)

- Consumes log messages from Kafka Topic

- Stores request/response logs in dump table for monitoring and auditing

## 🔐 Security
- Passwords stored **hashed** in DB

- WSO2 API Gateway handles **auth & rate limiting**

- Role-based access for endpoints

## 📅 Scheduled Jobs
- **Inactivate stale accounts** → Runs **every 1 hour** in Account Service

- Criteria: If no transaction > 24 hours → Status set to `INACTIVE`

## 📦 Installation & Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/Nada-Mohamad123/virtual-bank-system.git
   cd virtual-bank-system

### Development Team
- 👩‍💻 [Nada-Mohamad123](https://github.com/Nada-Mohamad123) 
- 👩‍💻 [soha-Refaat](https://github.com/soha-Refaat)




