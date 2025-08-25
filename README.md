📘 Account Service

The Account Service is a microservice responsible for managing bank accounts.
It handles account creation, retrieval.

🚀 Features

Create bank accounts for users.

Retrieve accounts by userId or accountID.


📌 API Endpoints
1. Create Bank Account
Endpoint: POST /accounts
Request Body
{
  "userId": "b2b3a45c-9ddc-421d-88f0-5b9d0ef9bcef",
  "accountType": "SAVINGS",
  "balance": 1000
}

Success Response (201 Created)
{
  "accountId": "7fa2c890-11aa-45d3-9b4a-91ca4e0b5bce",
  "userId": "b2b3a45c-9ddc-421d-88f0-5b9d0ef9bcef",
  "accountType": "SAVINGS",
  "balance": 1000
}

Error Response (400 Bad Request)
{
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid account type or initial balance."
}

2. Get Accounts by UserId
Endpoint: GET /accounts/{userId}/accounts
Success Response (200 OK)
[
  {
    "accountId": "7fa2c890-11aa-45d3-9b4a-91ca4e0b5bce",
    "accountNumber": "1234567890",
    "accountType": "SAVINGS",
    "balance": 1000
  }
]

Error Response (404 Not Found)
{
  "status": 404,
  "error": "Not Found",
  "message": "User with ID b2b3a45c-9ddc-421d-88f0-5b9d0ef9bcef not found."
}

3. Get Account by AccounId
Endpoint: GET /accounts/{accountId}
Success Response (200 OK)
{
  "accountId": "7fa2c890-11aa-45d3-9b4a-91ca4e0b5bce",
  "accountNumber": "1234567890",
  "accountType": "SAVINGS",
  "balance": 1000
}

Error Response (404 Not Found)
{
  "status": 404,
  "error": "Not Found",
  "message": "Account with accountNumber 1234567890 not found."
}

🔒 Security

Account operations are restricted to authenticated users.


🛠️ Tech Stack

Java / Spring Boot

H2 Database

REST API (Spring Web)

