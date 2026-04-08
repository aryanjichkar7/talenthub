# TalentHub Backend 🚀 — Spring Boot REST API

Enterprise HR System backend with JWT Security & MySQL

---

## 🖥️ Tech Stack
- Java 17
- Spring Boot 3.2
- Spring Security + JWT
- MySQL
- Hibernate + JPA

---

## 🚀 How to Run

Step 1 - Create MySQL database
CREATE DATABASE talenthub_db;

Step 2 - Update application.properties
spring.datasource.password=YOUR_MYSQL_PASSWORD

Step 3 - Run the backend
./mvnw spring-boot:run

Server runs at: http://localhost:8080

---

## 📋 API Endpoints

### Auth (Public)
POST /api/auth/register
POST /api/auth/login

### Employees (Need Token)
GET    /api/employees
POST   /api/employees
PUT    /api/employees/{id}
DELETE /api/employees/{id}

### Attendance
POST /api/attendance/mark/{employeeId}
GET  /api/attendance

### Leaves
POST /api/leaves/apply/{employeeId}
PUT  /api/leaves/approve/{leaveId}
GET  /api/leaves/balance/{employeeId}

### Payroll
POST /api/payroll/generate/{employeeId}
GET  /api/payroll

### Reports
GET /api/reports/summary
GET /api/reports/attendance
GET /api/reports/payroll

---

## 🔐 Using JWT Token
Add this to every request header:
Authorization: Bearer YOUR_TOKEN_HERE

---

## 💾 Database Tables
- employees
- attendance
- leaves
- payroll
- users

---

## 👥 Team Members
- Aryan Jichkar — Frontend Integration
- Soham Akant — Attendance & Leave
- Ashlesha Banpurkar — Payroll & Security
- Penagati Sarayu — Employee Module
- Sanskruti Shinde — Reports

---

## 🏢 Weintern Internship 2026