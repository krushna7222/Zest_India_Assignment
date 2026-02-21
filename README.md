📌 Zest Java Backend Assignment

A secure and production-ready Spring Boot 3 REST API implementing:

JWT Authentication (Access + Refresh Token)

Role-Based Authorization (ADMIN / USER)

Product Management

Order Management

Refresh Token Rotation

Swagger Documentation

Global Exception Handling

H2 / MySQL Database Support

🚀 Tech Stack Used
Technology	Version
Java	17+
Spring Boot	3.2.5
Spring Security	6
JWT	Custom Implementation
JPA / Hibernate	Latest
Database	MySQL / H2
Swagger	Springdoc OpenAPI 2.5.0
Maven	Build Tool
🔐 Authentication System

This project uses JWT-based Authentication:

✔ Access Token

Short expiry

Sent in Authorization header

Format:

Authorization: Bearer <access_token>
✔ Refresh Token

Stored in database

Supports token rotation

Used to generate new access token

👥 Role-Based Authorization
Role	Access
ADMIN	Admin APIs
USER	User APIs
PUBLIC	Product listing
📂 API Documentation (Swagger)

After running the application, open:

http://localhost:8500/swagger-ui/index.html

Swagger provides:

API testing

JWT Authorization support

Request/Response structure

⚙️ How to Setup the Project
1️⃣ Clone Repository
git clone <your-repo-url>
cd zest-java-backend-assignment
2️⃣ Configure Database

Update application.properties:

For MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/zest_assignment
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
OR for H2 (Embedded)
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.h2.console.enabled=true
3️⃣ Build Project
mvn clean install
4️⃣ Run Application
mvn spring-boot:run

Application runs at:

http://localhost:8500
🧪 How to Test APIs
Step 1: Login
POST /api/v1/auth/login

Copy:

accessToken

refreshToken

Step 2: Use Access Token

In Postman → Headers:

Authorization: Bearer <access_token>
Step 3: Refresh Token
POST /api/v1/auth/refresh-token

Body:

{
  "refreshToken": "your_refresh_token"
}
📦 Main Features Implemented
✔ User Authentication

Login

Logout

Refresh Token

✔ Admin APIs

Secured by ROLE_ADMIN

✔ Product APIs

Public product listing

Admin product management

✔ Order APIs

Authenticated order placement

User-specific order retrieval

✔ Security

BCrypt password hashing

Stateless session

Custom 401 / 403 JSON responses

Global exception handling

🏗 Project Architecture
controller → service → repository → entity

DTO layer for request/response

Clean separation of concerns

Centralized API response structure

🔒 Security Configuration

Stateless authentication

JWT filter

Role-based access control

CORS enabled for frontend integration

🧠 Learning Highlights

This project demonstrates:

Spring Security with JWT

Refresh Token rotation

Secure REST API design

Swagger integration with Spring Boot 3

Production-style exception handling


👨‍💻 Author
Krushna Shahane
Java Full Stack Developer

<img width="1600" height="968" alt="Screenshot 2026-02-20 091745" src="https://github.com/user-attachments/assets/364ee2e9-9db1-4bda-b826-99d444defa7e" />

<img width="1918" height="1022" alt="2" src="https://github.com/user-attachments/assets/91891935-d0bc-4e8d-b8b0-727cae497f54" />

<img width="1918" height="1022" alt="3" src="https://github.com/user-attachments/assets/3c2afd1e-0d22-474f-bda8-78f897fd73b5" />

<img width="1918" height="867" alt="image" src="https://github.com/user-attachments/assets/0b905205-b287-4c90-9088-b153da8b7b05" />

<img width="1919" height="708" alt="image" src="https://github.com/user-attachments/assets/28697752-688c-4c4f-b3f9-2f648e9223f9" />

<img width="1919" height="971" alt="image" src="https://github.com/user-attachments/assets/1fee2e56-9c21-4f35-9340-685afcb61e5d" />





