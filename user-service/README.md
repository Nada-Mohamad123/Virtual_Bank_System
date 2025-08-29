# User Service
The **User Service** is a microservice responsible for managing **user authentication, registration, and profile details.** 
It provides secure handling of user credentials (with password hashing), user registration, login workflows, and basic profile retrieval.

---

## 🚀 Features
- Secure password storage (hashed using industry best practices).
- User registration with conflict handling for existing username/email.
- User login with authentication response.
- Fetch user profile (requires authentication/authorization).
---
# 📌 API Endpoints
## 1. Register User
### Endpoint: `POST /users/register`  
### Request Body
```json
{
  "userName": "john.doe",
  "password": "securePassword123",
  "email": "john.doe@example.com",
  "firstName": "John",
  "lastName": "Doe"
}
```
### Success Response (201 Created)
```json
{
  "userId": "a1b2c3d4-e5f6-7890-1234-567890abcdef",
  "userName": "john.doe",
  "message": "User registered successfully."
}
```
### Error Response (409 Conflict)
```json
{
  "status": 409,
  "error": "Conflict",
  "message": "Username or email already exists."
}
```
## 2. Login User
### Endpoint: `POST /users/login`
### Request Body
```json
{
  "userName": "john.doe",
  "password": "securePassword123"
}
```
### Success Response (200 OK)
```json
{
  "userId": "a1b2c3d4-e5f6-7890-1234-567890abcdef",
  "userName": "john.doe"
}
```
### Error Response (401 Unauthorized)
```json
{
  "status": 401,
  "error": "Unauthorized",
  "message": "Invalid username or password."
}
```
## 3. Get User Profile
### Endpoint: `GET /users/{userId}/profile`
### Success Response (200 OK)
```json
{
  "userId": "a1b2c3d4-e5f6-7890-1234-567890abcdef",
  "userName": "john.doe",
  "email": "john.doe@example.com",
  "firstName": "John",
  "lastName": "Doe"
}
```
### Error Response (404 Not Found)
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "User with ID a1b2c3d4-e5f6-7890-1234-567890abcdef not found."
}
```
---
## 🔒 Security
- Passwords are never **stored in plain text.**
- All passwords are hashed before being persisted in the database.
- Authentication required for profile retrieval.
## 🛠️ Tech Stack
- **Java 17**
- **Spring Boot** 
- **H2**





