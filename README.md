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
