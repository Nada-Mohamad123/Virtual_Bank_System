# 🏦 Account Service  

The **Account Service** is a core module of the Virtual Bank System responsible for managing user bank accounts. It provides APIs for creating, retrieving, and updating bank accounts, as well as scheduled jobs to manage account statuses.  

---

## 🚀 Features  

- Create and manage unique bank accounts.  
- Retrieve account details by account ID or user ID.  
- Transfer balances between accounts.  
- Maintain account balances and status.  
- Scheduled job to inactivate stale accounts.  

---

## 📌 Endpoints  

### 1. Create New Account  
**POST** `/accounts`  

**Request Example**  
```json
{
  "userId": "a1b2c3d4-e5f6-7890-1234-567890abcdef",
  "accountType": "SAVINGS",
  "initialBalance": 100.00
}
```
**Response (201 Created)**  
```json
{
  "accountId": "f1e2d3c4-b5a6-9876-5432-10fedcba9876",
  "accountNumber": "1234567890",
  "message": "Account created successfully."
}
```
**Error (400 Bad Request)**  
```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid account type or initial balance."
}
```
### 2. Get Account by ID  
**GET** `/accounts/{accountId}`  


**Response (200 Ok)**  
```json
{
  "accountId": "f1e2d3c4-b5a6-9876-5432-10fedcba9876",
  "accountNumber": "1234567890",
  "accountType": "SAVINGS",
  "balance": 100.00,
  "status": "ACTIVE"
}
```
**Error (404 Not Found)**  
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Account with ID f1e2d3c4-b5a6-9876-5432-10fedcba9876 not found."
}
```
### 3. Get Accounts by User 
**GET** `/users/{userId}/accounts`  


**Response (200 OK)**  
```json
[
  {
    "accountId": "f1e2d3c4-b5a6-9876-5432-10fedcba9876",
    "accountNumber": "1234567890",
    "accountType": "SAVINGS",
    "balance": 100.00,
    "status": "ACTIVE"
  },
  {
    "accountId": "g7h8i9j0-k1l2-3456-7890-abcdef123456",
    "accountNumber": "0987654321",
    "accountType": "CHECKING",
    "balance": 500.50,
    "status": "ACTIVE"
  }
]
```
**Error (404 Not Found)**  
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "No accounts found for user ID a1b2c3d4-e5f6-7890-1234-567890abcdef."
}
```
### 4. Transfer Between Accounts  
**PUT** `/accounts/transfer`  

**Request Example**  
```json
{
  "fromAccountId": "f1e2d3c4-b5a6-9876-5432-10fedcba9876",
  "toAccountId": "a1b2c3d4-e5f6-7890-1234-567890abcdef",
  "amount": 100.00
}
```
**Response (200 OK)**  
```json
{
  "message": "Account updated successfully."
}
```
---
## 🕒 Scheduled Job – Inactivate Stale Accounts
- **Module:** Account Service
- **Purpose:** Automatically mark accounts as **INACTIVE** if idle for over **24 hours.**
- **Criteria:**
- - Account is currently **ACTIVE.**
  - Last transaction occurred **more than one day ago.**
- **Schedule:** Runs **every 1 hour.**
## ⚙️ Tech Stack
- **Language:** Java / Spring Boot 

- **Database:** H2

- **Scheduler:** Spring Scheduler


