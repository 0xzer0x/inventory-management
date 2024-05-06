<div align='center'>
    <img src="https://spring.io/img/projects/spring-boot.svg" height='100'/>
    &nbsp;&nbsp;
    <img src="https://spring.io/img/projects/spring-security.svg" height='100'/>
    &nbsp;&nbsp;
    <img src="https://www.thymeleaf.org/images/thymeleaf.png" height='100'/>
    <h2>📦 Inventory Management System</h2>
</div>

### 🚀 Overview

This repository hosts an Inventory Management System built with Spring MVC, leveraging Thymeleaf for server-side rendering and Bootstrap for styling. The application is containerized using Docker, facilitating easy deployment and scalability. GitHub Actions is utilized for CI/CD pipelines, ensuring smooth integration and deployment processes.

### 🛠️ Technology Stack

- **Gradle**: Dependency management and build automation tool.
- **Spring MVC**: Utilizes Spring MVC for robust backend logic.
- **Spring Security**: Secure authentication and authorization, protecting user data and ensuring only authorized users have access to specific actions.
- **Thymeleaf**: Server-side rendering for the frontend.
- **Bootstrap**: Responsive and modern UI design.
- **Docker**: Containerization for consistent environment across development, testing, and production.
- **GitHub Actions**: Automated CI/CD pipeline for seamless integration and deployment.
- **Unit Testing**: Comprehensive testing strategy to ensure application quality and reliability.

### 🏁 Getting Started

To get started with the Inventory Management System, follow these steps:

1. **Clone the Repository**

```sh
git clone https://github.com/0xzer0x/inventory-management.git
```

2. **Build the Application JAR**

You need to build the application JAR before building the container image. To build the executable JAR file, use the following Gradle command:

```sh
./gradlew bootJar
```

3. **Build and Run with Docker Compose**

Navigate to the project directory and run the following command:

```sh
docker compose up
```

This command will build the Docker images (if not already built) and start the containers. The application will be accessible at `http://localhost:8080`.

### ✅ Running Tests

To run the unit and integration tests, use the following Gradle command:

```sh
./gradlew tests --no-parallel
```

### 👥 Contributing

Contributions to this project are welcome. Please feel free to submit pull requests or open issues for any bugs or feature requests.

### 📜 License

This project is licensed under the GPLv3 License - see the [LICENSE](LICENSE) file for details.
