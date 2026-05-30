# AI-Powered Hospital Management System

Welcome to the AI-Powered Hospital Management System repository! This project is an enterprise-grade backend REST API designed to modernize clinical operations and eliminate administrative bottlenecks. Built with Java 21 and Spring Boot 3.5.3, this system marries traditional hospital workflows—such as secure onboarding, scheduling, and RBAC—with cutting-edge Retrieval-Augmented Generation (RAG) and Automated Data Extraction using Large Language Models via Spring AI.

## Features and Functionalities

### ✨ AI Innovations
The defining feature of this system is the integration of **Spring AI** and **pgvector** to automate complex medical data handling:
* **Patient History RAG (Semantic Search):** Converts patient medical histories into mathematical vector embeddings. Doctors can ask natural language questions (e.g., "Has this patient reacted badly to penicillin before?") and the system retrieves strictly isolated, relevant past notes to formulate an accurate answer.
* **Smart Clinical Note Extraction:** Doctors can input messy, unstructured free-text notes. The system utilizes AI `BeanOutputConverters` to automatically parse and extract structured data (Symptoms, Prescriptions, Summary) directly into relational database tables.
* **AI-Assisted Triage:** Analyzes patient-submitted symptoms against a live database of hospital departments, automatically recommending the correct department and assigning an urgency level.

### ⚙️ 6 Core Workflows
1.  **Hybrid Authentication & Authorization:**
    * Stateless architecture utilizing JSON Web Tokens (JWT).
    * Standard Email/Password login alongside OAuth2 Social Logins (Google, GitHub, Twitter) with auto-registration.
    * Granular, method-level Role-Based Access Control (RBAC) separating generic roles (Admin, Doctor, Patient) into specific authorities (e.g., `appointment:write`, `user:manage`).
2.  **Doctor Onboarding & Management:**
    * Admin-only workflows to upgrade standard users to specialized Doctor accounts.
    * Management of doctor profiles, specializations, and departmental assignments.
3.  **Appointment Lifecycle:**
    * Complete scheduling system allowing patients to book, view, and cancel appointments.
    * Administrative endpoints for reassigning appointments between doctors.
    * JPA Auditing to track appointment statuses and timestamps.
4.  **Department Routing:**
    * CRUD operations for hospital departments.
    * Dynamic mapping of doctors to multiple departments to support complex hospital hierarchies.
5.  **Insurance Association:**
    * Patient-to-Insurance relationship management.
    * Endpoints to securely attach, verify, and remove insurance policies from patient profiles.
6.  **Patient Profile Management:**
    * Self-service endpoints for patients to update demographics (blood group, DOB, gender).
    * Paginated querying for administrators to securely retrieve patient populations without overwhelming the database.

## Technology Stack

The project is developed using the following technologies and tools:
* **Language:** Java 21
* **Backend Framework:** Spring Boot 3.5.3
* **Security:** Spring Security 6, OAuth2, JWT (jjwt)
* **AI Integration:** Spring AI (1.0.0-M5), OpenAI API
* **Database:** PostgreSQL with `pgvector` extension
* **ORM:** Spring Data JPA, Hibernate
* **Utilities:** ModelMapper, Lombok, Validation API
* **Build Tool:** Maven

## How to Run the Application

**Clone the repository:**
```bash
git clone https://github.com/anjali13nair/hospital-management-system.git
cd hospital-management-system
```

**Configure the database and environment variables:**
Ensure you have PostgreSQL installed and the `vector` extension created.

Step 1: Create a database named `hospital_db` in PostgreSQL:
```sql
CREATE DATABASE hospital_db;
\c hospital_db
CREATE EXTENSION vector;
```

Step 2: Create a `.env` file in the root directory (or configure your IDE's Run Configuration) with the following required variables:
```properties
# Database
DB_URL=jdbc:postgresql://localhost:5432/hospital_db
DB_USERNAME=<your-postgres-username>
DB_PASSWORD=<your-postgres-password>
JPA_DDL_AUTO=update

# Authentication Secrets
JWT_SECRET_KEY=<your-secure-jwt-secret>

# OAuth2 Credentials (Optional for local testing without social login)
GOOGLE_CLIENT_ID=<your-google-client-id>
GOOGLE_CLIENT_SECRET=<your-google-client-secret>
GITHUB_CLIENT_ID=<your-github-client-id>
GITHUB_CLIENT_SECRET=<your-github-client-secret>

# AI Configuration
OPENAI_API_KEY=<your-openai-api-key>
```

**Build and Run the application:**

Open a terminal and navigate to the root directory of the project.
Run the following command to build the application:
```bash
mvn clean install
```

Start the application using the Spring Boot Maven plugin:
```bash
mvn spring-boot:run
```

**Access the application:**
Once the application starts successfully, the REST API will be accessible at:
`http://localhost:8080`

**Test the application:**
Use an API testing tool like **Postman** to interact with the endpoints (e.g., `/auth/signup`, `/api/v1/oauth2/authorization/google`, `/ai/patient/{id}/ask`).

## Future Enhancements

Here are some features and improvements planned for future updates:
* **Doctor Availability & Scheduling:** Implement full scheduling logic with available time slots and holiday management to prevent double-booking.
* **Flyway/Liquibase Migrations:** Replace `ddl-auto` with formal database version control for production readiness.
* **Integration Testing:** Implement a robust test suite using Testcontainers for isolated PostgreSQL and pgvector testing.
* **Frontend Integration:** Build a dedicated React or Angular frontend client to consume these APIs.

## Contribution

We welcome contributions to make this project better! Here's how you can contribute:
1.  Fork the repository by clicking the "Fork" button at the top of this page.
2.  Clone your forked repository:
    `git clone https://github.com/<your-username>/hospital-management-system.git`
3.  Create a new branch for your feature or bug fix.
4.  Make your changes and commit them with descriptive messages.
5.  Push your changes to your forked repository.
6.  Submit a Pull Request detailing the changes you made.

## Contact

If you have any questions, suggestions, or feedback, feel free to reach out:
* **GitHub:** [@anjali13nair](https://github.com/anjali13nair)
* **Email:** anjalinnair13@gmail.com
* **Portfolio:** [anjali-nair.dev](https://anjali-nair.netlify.app/)
* **LinkedIn:** [Anjali Nair](https://www.linkedin.com/in/anjalinair13/)
