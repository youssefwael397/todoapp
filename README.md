# 📝 JooTodo - Spring Boot Todo Application

A robust, secure Todo application built with Spring Boot, featuring JWT authentication, role-based authorization, and RESTful APIs.

## 🚀 Features

- **🔐 JWT Authentication** - Secure token-based authentication
- **👥 Role-Based Authorization** - USER and ADMIN roles with different permissions
- **📝 Todo Management** - Full CRUD operations for todos
- **👤 User Management** - User registration, profile management
- **🛡️ Security** - Protected endpoints with Spring Security
- **📊 Database Integration** - PostgreSQL with JPA/Hibernate
- **✅ Input Validation** - Comprehensive request validation
- **🎯 Exception Handling** - Global exception handling with proper error responses

## 🛠️ Tech Stack

- **Backend**: Spring Boot 3.5.0
- **Security**: Spring Security + JWT
- **Database**: PostgreSQL
- **ORM**: Spring Data JPA
- **Java Version**: 24
- **Build Tool**: Maven
- **Additional Libraries**:
  - Lombok
  - ModelMapper
  - Jackson
  - Bean Validation

## 📋 Prerequisites

- Java 21 or higher
- PostgreSQL database
- Maven 3.6+

## ⚙️ Installation & Setup

### 1. Clone the repository

### 2. Database Setup
Create a PostgreSQL database and update the connection details in `src/main/resources/application.yml`:

### 3. Build and Run


The application will start on `http://localhost:2025`

## 🔗 API Endpoints

### 🏠 Public Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/` | Welcome page |
| `POST` | `/auth/register` | User registration |
| `POST` | `/auth/login` | User login |

### 🔐 Authentication Endpoints (`/auth`)
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| `GET` | `/auth/profile` | Authenticated | Get current user profile |

### 👥 User Management (`/users`)
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| `GET` | `/users` | ADMIN | Get all users |
| `GET` | `/users/profile` | USER/ADMIN | Get current user profile |
| `GET` | `/users/{id}` | ADMIN | Get user by ID |
| `PUT` | `/users/profile` | USER/ADMIN | Update current user |
| `PUT` | `/users/{id}` | ADMIN | Update user by ID |
| `DELETE` | `/users/{id}` | ADMIN | Delete user |

### ✅ Todo Management (`/todos`)
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| `GET` | `/todos` | ADMIN | Get all todos |
| `GET` | `/todos/my-todos` | USER/ADMIN | Get current user's todos |
| `GET` | `/todos/{id}` | ADMIN | Get todo by ID |
| `GET` | `/todos/user/{userId}` | ADMIN | Get todos by user ID |
| `POST` | `/todos` | USER/ADMIN | Create new todo |
| `PUT` | `/todos/{id}` | ADMIN/Owner | Update todo |
| `PUT` | `/todos/{id}/complete` | ADMIN/Owner | Toggle completion |
| `DELETE` | `/todos/{id}` | ADMIN/Owner | Delete todo |

