# Congestion Tax Calculator

Welcome the Volvo Cars Congestion Tax Calculator assignment.

This repository contains a developer [assignment](ASSIGNMENT.md) used as a basis for candidate intervew and evaluation.

Clone this repository to get started. Due to a number of reasons, not least privacy, you will be asked to zip your solution and mail it in, instead of submitting a pull-request. In order to maintain an unbiased reviewing process, please ensure to **keep your name or other Personal Identifiable Information (PII) from the code**.


# How to Test and Run the Application

This guide provides instructions to test and run the Spring Boot application using Maven.

## Prerequisites

- **JDK 21 or higher**: Ensure that JDK is installed and configured on your machine.

## Running the Application

### Step 1: Clone the repository

First, build the application to ensure that all dependencies are resolved and code is compiled:

```bash
git clone <repository-url>
cd <repository-directory>
```

### Step 2: Build the Application

Ensure that you are in the root directory of the project:

```bash
./mvnw clean compile
```

### Step 3: Running Tests (Optional)

Run all unit and integration tests to verify the application:

```bash
./mvnw test
```

### Step 4: Run the Application

Start the Spring Boot application with the following command:

```bash
./mvnw spring-boot:run
```

