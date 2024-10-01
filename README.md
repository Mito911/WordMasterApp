# WordMasterApp

WordMasterApp is a web application for learning vocabulary. It allows users to create, view, and manage words across different categories. The application supports generating tests based on saved words, enabling effective learning of new vocabulary.

## Features

- **Manage Vocabulary**: Ability to add, edit, delete, and view words.
- **Word Categories**: Words can be grouped under specific categories for better organization.
- **Generate Tests**:
  - Generate random words for tests.
  - Generate words added in the last day, week, or month.
  - Ability to choose whether to translate from original to translation or vice versa.
- **Add New Categories**: Create new categories for better vocabulary organization.

## Technology Stack

- **Backend**: Spring Boot (Java) - REST API
- **Frontend**: HTML, CSS, JavaScript
- **Database**: MySQL (using Spring Data JPA)
- **Docker**: The application is prepared to run in a Docker container.
- **Build Tool**: Maven

## Requirements

- **Java 17+**: The application requires Java version 17 or higher.
- **Maven**: For building the project.
- **MySQL**: For storing application data.
- **Docker (optional)**: To run the application in a container.

## How to Run the Application?

### Clone the Repository

```bash
git clone https://github.com/YourAccount/WordMasterApp.git
cd WordMasterApp


Database Configuration
Before running the application, make sure you have a MySQL database and update the src/main/resources/application.properties file:

spring.datasource.url=jdbc:mysql://localhost:3306/wordmasterapp
spring.datasource.username=your_database_username
spring.datasource.password=your_database_password
