# 💸 Transaction Service  

The **Transaction Service** is a core module of the Virtual Bank System responsible for handling all financial transactions such as deposits, withdrawals, and transfers. It maintains detailed transaction history and integrates with the Account Service to update balances.  

---

## 🚀 Features  

- Initiate and execute fund transfers between accounts.  
- Maintain transaction records with status and timestamps.  
- Retrieve transaction history for specific accounts.  
- Validate accounts and ensure sufficient funds before executing transfers.  

---

## 📌 API Endpoints 

### 1. Initiate Fund Transfer  
**POST** `/transactions/transfer/initiation`  

**Request Body**  
```json
{
  "fromAccountId": "f1e2d3c4-b5a6-9876-5432-10fedcba9876",
  "toAccountId": "g7h8i9j0-k1l2-3456-7890-abcdef123456",
  "amount": 30.00,
  "description": "Transfer to checking account"
}
```
**Success Response (200 OK)**

```json
{
  "transactionId": "t1r2a3n4-s5a6-7890-1234-567890abcdef",
  "status": "Initiated",
  "timestamp": "2025-07-15T07:16:49.822Z"
}
```
**Error Response (400 Bad Request)**

```json

{
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid 'from' or 'to' account ID."
}
```
### 2. Execute Fund Transfer
**POST** `/transactions/transfer/execution`

**Request Body**

```json

{
  "transactionId": "t1r2a3n4-s5a6-7890-1234-567890abcdef"
}
```
**Success Response (200 OK)**

```json

{
  "transactionId": "t1r2a3n4-s5a6-7890-1234-567890abcdef",
  "status": "Success",
  "timestamp": "2025-07-15T07:16:49.822Z"
}
```
**Error Response (400 Bad Request)**

```json

{
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid 'from' or 'to' account ID or insufficient funds."
}
```
### 3. Get Transaction History for Account
**GET** `/accounts/{accountId}/transactions`

**Success Response (200 OK)**

```json

[
  {
    "transactionId": "1r2a3n4-s536-7890-1234-567890abcd",
    "fromAccountId": "f1e2d3c4-b5a6-9876-5432-10fedcba9876",
    "toAccountId": "f2c3d3c4-b5a6-9876-5432-10fadbba9876",
    "amount": 50.00,
    "description": "Cash deposit",
    "timestamp": "2025-06-30T10:05:00Z",
    "deliveryStatus": "SENT"
  },
  {
    "transactionId": "x1y2z3-4b5c-7890-1234-567890fedcba",
    "fromAccountId": "f1e2d3c4-b5a6-9876-5432-10fedcba9876",
    "toAccountId": "f2c3d3c4-b5a6-9876-5432-10fadbba9876",
    "amount": -30.00,
    "description": "Transfer to checking account",
    "timestamp": "2025-06-30T10:10:00Z",
    "deliveryStatus": "DELIVERED"
  }
]
```
**Error Response (404 Not Found)**

```json

{
  "status": 404,
  "error": "Not Found",
  "message": "No transactions found for account ID f1e2d3c4-b5a6-9876-5432-10fedcba9876."
}
```
## 🛠️ Tech Stack

- **Java 17**
- **Spring Boot**
- **H2**
