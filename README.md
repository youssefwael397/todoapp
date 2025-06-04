# JooTodo - Java Todo Application

A modern Todo application built with Spring Boot, Jakarta EE, and Spring Data JPA, featuring comprehensive exception handling and RESTful API design.

## 🚀 Features

- **CRUD Operations**: Complete Create, Read, Update, Delete functionality for todo items
- **RESTful API**: Well-structured REST endpoints
- **Robust Exception Handling**: Comprehensive global exception handler with proper HTTP status codes
- **Data Validation**: Input validation with detailed error messages
- **Database Integration**: Spring Data JPA with entity management
- **Modern Java**: Built with Java SDK 24 and Jakarta EE
- **Clean Architecture**: Organized package structure following best practices

## 🛠️ Technology Stack

- **Java**: SDK 24
- **Framework**: Spring Boot
- **Web**: Spring MVC
- **Data**: Spring Data JPA
- **Jakarta EE**: Enterprise features with jakarta imports
- **Lombok**: Reducing boilerplate code
- **Build Tool**: Maven

## 📁 Project Structure

## 🚦 Getting Started

### Prerequisites

- Java 24 or higher
- Maven 3.6 or higher
- Your preferred IDE (IntelliJ IDEA recommended)

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd jootodo
   ```

2. **Build the project**
   ```bash
   ./mvnw clean install
   ```

3. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

   Or on Windows:
   ```cmd
   mvnw.cmd spring-boot:run
   ```

4. **Access the application**
   - The application will start on `http://localhost:2025`
   - API endpoints will be available at `http://localhost:2025/api/...`

## 🔧 Configuration

Update `src/main/resources/application.properties` or `application.yml` with your specific configurations:

- Database connection settings
- Server port (default: 2025)
- Logging levels
- Other application-specific properties

## 🌐 API Endpoints

The application provides RESTful endpoints for managing todo items. Common endpoints include:

- `GET /api/todos` - Get all todos
- `GET /api/todos/{id}` - Get todo by ID
- `POST /api/todos` - Create new todo
- `PUT /api/todos/{id}` - Update existing todo
- `DELETE /api/todos/{id}` - Delete todo

## 🛡️ Exception Handling

The application features comprehensive exception handling:

- **404 Not Found**: When resources don't exist
- **400 Bad Request**: For validation errors and malformed requests
- **409 Conflict**: For data integrity violations
- **405 Method Not Allowed**: For unsupported HTTP methods
- **500 Internal Server Error**: For unexpected server errors

All errors return structured JSON responses with:
- HTTP status code
- Error type
- Detailed message
- Request URI

## 🧪 Testing

## 📦 Building for Production

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👨‍💻 Author

**Youssef Wael**

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- Jakarta EE community for enterprise features
- All contributors and maintainers

---

For more information, please refer to the [Spring Boot Documentation](https://spring.io/projects/spring-boot) and [Jakarta EE Documentation](https://jakarta.ee/).
