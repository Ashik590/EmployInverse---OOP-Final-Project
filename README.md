# EMPLOYMENT INVERSE — OOP Final Project

A simple service-based marketplace built in Java that connects Clients (who post jobs) with Service Providers (who apply and deliver work). This academic project demonstrates solid object-oriented design, a controlled payment flow, a notification system, and a lightweight file-based database. The GUI is implemented using Java Swing.

---

## Table of Contents
- [About](#about)
- [Run the application](#how-to-run-the-application)
- [Short Overview](#short-overview)
- [Core Features](#core-features)
    - [1. Object-Oriented Design](#1-object-oriented-design)
    - [2. Client Features](#2-client-features)
    - [3. Service Provider Features](#3-service-provider-features)
    - [4. Service (Job Post) Features](#4-service-job-post-features)
    - [5. Notification System](#5-notification-system)
    - [6. Controlled Payment Flow](#6-controlled-payment-flow)
    - [7. File-Based Database System](#7-file-based-database-system)
    - [8. Java Swing UI](#8-java-swing-ui)
- [Project Structure](#project-structure)
- [How It Works (High level)](#how-it-works-high-level)
- [Future Improvements (Suggestions)](#future-improvements-suggestions)
- [Contributing](#contributing)
- [Author](#author)
- [License](#license)

---

## About
EmployInverse is an academic OOP project that models a small service marketplace. It focuses on clean class design, encapsulation, inheritance and polymorphism, while providing a minimal but functional user interface and file-backed persistence.

## How to run the application

Prerequisites
- Java installed (JDK). Java 8+ recommended.

Run the application
1. Open a terminal/command prompt.
2. Change to the `src` directory of the project:
   ```
   cd src
   ```
3. Compile the main entry:
   ```
   javac Main.java
   ```
4. Run the application:
   ```
   java Main
   ```

That's it — the application will launch the signup UI.

---

## Short Overview
Clients post job posts (services). Service Providers apply to those jobs. Clients select a provider, payment is temporarily held, and the job proceeds through well-defined statuses (incompleted → ongoing → delivered → completed / cancellation). The system manages users, services, applications, notifications, and payment flow using object-oriented principles and simple file-based storage.

---

## Core Features

### 1. Object-Oriented Design
- Abstract `Person` class holding shared properties (name, phone, email, username, password, balance) and methods.
- `Client` and `ServiceProvider` extend `Person` to reuse common behavior.
- Encapsulation via getters/setters and balance-manipulation methods.
- Clear inheritance from Person to Client and ServiceProvider, with method overriding and overloading used to demonstrate polymorphism.
- `Service` model represents job posts with independent behavior.

### 2. Client Features
- Create job posts with title, description and price.
- View applicants for jobs they create.
- Accept or remove applicants from a job's applicant list.
- When a provider is selected, the payment is held (escrow-like) until delivery is accepted.
- Request cancellation while a job is ongoing.
- Accept delivery to release payment to the provider.

### 3. Service Provider Features
- Apply to available job posts.
- Track current assignment via `currentJobID`.
- Maintain list of completed job IDs for history.
- Can deliver their assigned job when its status is 'ongoing'.
- Receive notifications for assignment and cancellation requests.
- Accept or deny cancellation requests; when cancellation is accepted, job resets and money returns to client.
- Earn payment only after client accepts delivery.

### 4. Service (Job Post) Features
- Unique ID, title, description, price, and status.
- Maintains list of applicants (usernames).
- Stores both client (owner) and provider (when assigned).
- Supports controlled statuses:
    - `incompleted`, `ongoing`, `delivered`, `completed`, `on-request cancellation`

### 5. Notification System
- Providers receive notifications when assigned or when a client requests cancellation.
- Clients receive notifications when a provider delivers the job.

### 6. Controlled Payment Flow
- When a client selects a provider, the payment is held.
- Only after the client accepts delivery does the payment go to the provider.
- If cancellation is accepted, payment is returned to the client.
- Simple, safe flow appropriate for academic demonstration.

### 7. File-Based Database System
- All data (Clients, Providers, Services) persisted in text files.
- The abstract `FileFunctions` class provides insert/search/update/delete and fetch operations for persisting Clients, ServiceProviders and Services in text files.
- Acts as a lightweight custom database layer separate from UI logic.

### 8. Java Swing UI
- Comprehensive GUI for login, signup, browsing jobs, creating job posts, viewing job details, managing applicants, profiles, and notifications.
- Separate dashboards and screens for Clients and Providers.
- `Main` class initializes and launches the UI.

---

## Project Structure
- src/
    - logic/      — core classes (Person, Client, ServiceProvider, Service, FileFunctions)
    - UI/         — Java Swing components and screens
        - client/   — Client dashboard and views
        - provider/ — Provider dashboard and views
    - Main.java   — application entry point

---

## How It Works (High level)
1. Users sign up as a Client or Service Provider.
2. Clients create Service job posts with details and price.
3. Providers browse open jobs and apply.
4. Client reviews applicants and selects a Provider:
    - The specified payment is held (balance deducted from client and stored temporarily).
5. Provider marks the job as delivered:
    - Client receives notification and inspects delivery.
6. If Client accepts delivery:
    - Held payment is released to the Provider (provider balance increases).
    - Job status set to `completed`.
7. If cancellation is requested and accepted:
    - The job is canceled and money returned to the Client.

---


## Author
Ashik590
