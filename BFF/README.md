# BFF Service (Backend for Frontend)

The **BFF Service** (Backend for Frontend) provides a **simplified and optimized API layer** for the frontend.  
It aggregates data from multiple backend microservices, orchestrates workflows, and transforms responses into a frontend-friendly format.

This service is the main entry point for the frontend application and communicates directly with other microservices in the **Virtual Bank System**.

---

## 🚀 Key Responsibilities
- **Aggregate Data**: Collect and combine responses from multiple microservices into a single response.
- **Orchestrate Business Flows**: Handle workflows that involve multiple services.
- **Data Transformation**: Adapt and reshape backend data for frontend needs.
- **Service Communication**: Use asynchronous calls (`WebClient`) to fetch data efficiently.

---

## 📌 Endpoints

### 1. **`GET /bff/dashboard/{userId}`**
Fetches **user profile**, all associated accounts, and recent transactions for each account.  
This endpoint demonstrates **data aggregation** from multiple microservices.

#### ✅ Process Flow:
1. Call **User Service** → `GET /users/{userId}/profile`
2. Call **Account Service** → `GET /users/{userId}/accounts`
3. For each account, call **Transaction Service** → `GET /accounts/{accountId}/transactions`
4. Combine responses into a single aggregated JSON response.

#### 🔹 Success Response (200 OK)
```json
{
  "userId": "a1b2c3d4-e5f6-7890-1234-567890abcdef",
  "username": "john.doe",
  "email": "john.doe@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "accounts": [
    {
      "accountId": "f1e2d3c4-b5a6-9876-5432-10fedcba9876",
      "accountNumber": "1234567890",
      "accountType": "SAVINGS",
      "balance": 120.00,
      "transactions": [
        {
          "transactionId": "t1r2a3n4-s5a6-7890-1234-567890abcdef",
          "amount": 50.00,
          "toAccountId": "a1b2c3d4-e5f6-7890-1234-567890abcdef",
          "description": "Cash deposit",
          "timestamp": "2025-06-30T10:05:00Z"
        }
      ]
    }
  ]
}
```
#### 🔹 Error Responses (404 Not Found)
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "User not found."
}
```
