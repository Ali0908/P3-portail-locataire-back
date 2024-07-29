## Presentation
This project manages the back-end of the "portail-location" application, a web application developed with Spring Boot and using MariaDB as the database. This documentation will guide you through the steps needed to install, configure, and run the project. You will also find the URL to access the API documentation generated by Swagger.
## Prerequisites

Before starting, make sure you have the following tools installed on your machine:

    Java Development Kit (JDK) 17 or higher
    Maven
    MariaDB
    Git
## Installation and Running the Project
## 1. Clone the Repository

Clone the GitHub repository to your local machine using the following command:

git clone https://github.com/Ali0908/P3-portail-locataire-back.git

cd P3-portail-locataire-back.git

## 2. Configure the Database
Installing MariaDB

If MariaDB is not already installed, you can install it by following these steps:

For Linux:

    sudo apt update
    
    sudo apt install mariadb-server
    
    sudo systemctl start mariadb
    
    sudo systemctl enable mariadb

For macOS:

Use Homebrew:

    brew install mariadb
    brew services start mariadb

For Windows:

Download and install MariaDB from the official site.
Database Configuration

Connect to MariaDB:

    mysql -u root -p
Create a new database and user:

    CREATE DATABASE your_database_name;
    CREATE USER 'your_username'@'localhost' IDENTIFIED BY 'your_password';
    GRANT ALL PRIVILEGES ON your_database_name.* TO 'your_username'@'localhost';
    FLUSH PRIVILEGES;

3.Configure the database connection settings in the application.properties or application.yml file of the Spring Boot project:

application.properties:

    spring.datasource.url=jdbc:mariadb://localhost:3306/your_database_name
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    spring.jpa.hibernate.ddl-auto=update

application.yml:
spring:
  datasource:

        url: jdbc:mariadb://localhost:3306/your_database_name
        username: your_username
        password: your_password
      jpa:
        hibernate:
          ddl-auto: update
### 3. Build and Run the Application

Use Maven to build the project and run the application:

    mvn clean install
    mvn spring-boot:run
The application will be running at http://localhost:3001.
### API Documentation

The URL to access the Swagger API documentation is:
http://localhost:3001/swagger-ui/index.html

