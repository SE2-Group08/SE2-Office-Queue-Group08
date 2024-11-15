# Project Setup Documentation

## Introduction

This guide will walk you through the process of setting up the environment for this project, including the installation
of dependencies, setting up PostgreSQL with Docker, and running the application.

### Prerequisites

Before starting, ensure you have the following installed on your machine:

- **Docker**: You will use Docker to run the PostgreSQL database container.
- **Java Development Kit (JDK) 17**: Required for running the Java application.
- **Maven**: Used for building and managing the project's dependencies.

---

## 1. Cloning the Repository

First, clone the project repository to your local machine.

```bash
git clone <repository-url>
cd <project-directory>/backend
```

---

## 2. Setting up PostgreSQL with Docker

The PostgreSQL database is managed via Docker. A `docker-compose.yml` file has been provided to simplify the setup.
Here’s how to start the database:

### 2.1 Install Docker

If Docker is not installed, follow the installation steps for your operating
system [here](https://www.docker.com/).

### 2.2 Launching the PostgreSQL Database

We are using a PowerShell script to manage the PostgreSQL container. Here’s how you can use the script to start, stop,
and restart the database.

1. Navigate to the project directory where the [manage-postgres.ps1](manage-postgres.ps1) script is located.

2. To **start** the PostgreSQL database, run the following command in PowerShell:

   ```powershell
   ./manage-database.ps1 start
    ```

3. To **stop** the PostgreSQL database, run:

   ```powershell
   ./manage-database.ps1 stop
    ```
4. To **restart** the PostgreSQL database, run:

   ```powershell
    ./manage-database.ps1 restart
     ```

The PowerShell script will handle starting, stopping, or restarting the database container using Docker Compose. Make
sure Docker is running on your machine before executing the script.

---

## 3. Configuring the Database Connection

The PostgreSQL instance runs on localhost at port 5332. The credentials for the database can be found in
the [application.yml](src/main/resources/application.yml) file.

- Database user: `group08`
- Database name: `oqms`

---

## 4. Building and Running the Application

### 4.1 Installing Java and Maven

Ensure you have JDK 17 or higher and Maven installed.

### 4.2 Running the Application

1. Once the PostgreSQL database is up and running, open the project in your favorite IDE (e.g., IntelliJ IDEA, Eclipse).

2. In the IDE, locate
   the [QueueManagementSystemApplication](src/main/java/it/polito/queuemanagementsystem/QueueManagementSystemApplication.java)
   class, which is
   the main entry point of the application.

3. Run the `main` method in
   the [QueueManagementSystemApplication](src/main/java/it/polito/queuemanagementsystem/QueueManagementSystemApplication.java)
   class directly from your IDE.

4. If the application starts successfully, it will be available at `http://localhost:8080`.

---

## 5. Accessing the Application and API

### 5.1 API Documentation (Swagger)

The application comes with Swagger UI to help you test the API endpoints. To access it, go to:

```
http://localhost:8080/swagger-ui.html
```

Here, you can see all the available endpoints and interact with the API directly from the browser.

### 5.2 Database Management

If you want to manage the PostgreSQL database directly, you can use **pgAdmin** or any PostgreSQL client of your choice.
If you prefer to use pgAdmin:

1. Download and install pgAdmin from [here](https://www.pgadmin.org/download/).
2. Connect to the PostgreSQL database using the credentials mentioned above.

This will allow you to browse the database and run queries from a GUI.

You can also use the command line to interact with the database.

---

## Troubleshooting

### Common Issues

To be added when issues arise.

---

## Conclusion

You’re now ready to start working on the project! Follow this guide to set up the development environment, launch the
PostgreSQL database, and run the application.