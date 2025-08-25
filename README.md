🏦 ACCOUNT SERVICE

====================

The Account Service is a microservice responsible for managing bank accounts. It handles account creation, balance retrieval, and transfers between accounts. It ensures secure handling of account operations.

🚀 FEATURES

Create bank accounts for users

Create bank accounts with unique account numbers

Retrieve accounts by userId or accountId

Transfer funds between accounts securely

Handle errors such as insufficient balance or invalid accounts

📌 API ENDPOINTS
🏦 CREATE BANK ACCOUNT

Endpoint: POST /accounts

<details> <summary>Request Body</summary>
{
  "userId": "b2b3a45c-9ddc-421d-88f0-5b9d0ef9bcef",
  "accountType": "SAVINGS",
  "balance": 1000
}

</details> <details> <summary>✅ Success Response (201 Created)</summary>
{
  "accountId": "7fa2c890-11aa-45d3-9b4a-91ca4e0b5bce",
  "userId": "b2b3a45c-9ddc-421d-88f0-5b9d0ef9bcef",
  "accountType": "SAVINGS",
  "balance": 1000
}

</details> <details> <summary>❌ Error Response (400 Bad Request)</summary>
{
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid account type or initial balance."
}

</details>
👤 GET ACCOUNTS BY USERID

Endpoint: GET /accounts/{userId}/accounts

<details> <summary>✅ Success Response (200 OK)</summary>
[
  {
    "accountId": "7fa2c890-11aa-45d3-9b4a-91ca4e0b5bce",
    "accountNumber": "1234567890",
    "accountType": "SAVINGS",
    "balance": 1000
  }
]

</details> <details> <summary>❌ Error Response (404 Not Found)</summary>
{
  "status": 404,
  "error": "Not Found",
  "message": "User with ID b2b3a45c-9ddc-421d-88f0-5b9d0ef9bcef not found."
}

</details>
🔎 GET ACCOUNT BY ACCOUNTID

Endpoint: GET /accounts/{accountId}

<details> <summary>✅ Success Response (200 OK)</summary>
{
  "accountId": "7fa2c890-11aa-45d3-9b4a-91ca4e0b5bce",
  "accountNumber": "1234567890",
  "accountType": "SAVINGS",
  "balance": 1000
}

</details> <details> <summary>❌ Error Response (404 Not Found)</summary>
{
  "status": 404,
  "error": "Not Found",
  "message": "Account with accountNumber 1234567890 not found."
}

</details>
🔒 SECURITY

Account operations are restricted to authenticated users.

🛠️ TECH STACK

Java / Spring Boot

H2 Database

REST API (Spring Web)
