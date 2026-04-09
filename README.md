# 🍍 Ananas Login & Search Automation

[![Java Version](https://img.shields.io/badge/Java-17-blue.svg)](https://adoptium.net/)
[![Maven](https://img.shields.io/badge/Maven-3.9+-C71A36.svg)](https://maven.apache.org/)
[![Selenium](https://img.shields.io/badge/Selenium-4.27.0-green.svg)](https://www.selenium.dev/)
[![TestNG](https://img.shields.io/badge/TestNG-7.10.2-red.svg)](https://testng.org/)
[![Log4j2](https://img.shields.io/badge/Log4j2-2.23.1-blue.svg)](https://logging.apache.org/log4j/2.x/)
[![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED.svg)](https://www.docker.com/)
[![Jenkins](https://img.shields.io/badge/Jenkins-Pipeline-orange.svg)](https://www.jenkins.io/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

[![Selenium Tests](https://github.com/Gagasr10/Ananas-login-automation/actions/workflows/test.yml/badge.svg)](https://github.com/Gagasr10/Ananas-login-automation/actions/workflows/test.yml)

Automated UI tests for the **login functionality** and **search feature** of **[ananas.rs](https://ananas.rs)** – a Serbian e‑commerce platform similar to Amazon.  
The project is written in Java using Selenium WebDriver, TestNG, and Maven, following the **Page Object Model (POM)** design pattern.  
It features **professional logging (Log4j2)**, **automatic retry of flaky tests**, **data providers**, **soft assertions**, and **detailed ExtentReports** with screenshots on test failures.  
The entire test suite can be run inside a **Docker container** and integrated into a **Jenkins CI/CD pipeline** or **GitHub Actions**.

---

## 📋 Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Setup & Configuration](#setup--configuration)
- [Running the Tests](#running-the-tests)
  - [Locally with Maven](#locally-with-maven)
  - [Inside Docker Container](#inside-docker-container)
  - [With Jenkins Pipeline](#with-jenkins-pipeline)
- [Test Reports](#test-reports)
- [Known Issues & CI Limitations](#known-issues--ci-limitations)
- [Contributing](#contributing)
- [License](#license)

---

## ✨ Features

- ✅ **19 Login Tests** – valid/invalid credentials, empty fields, password boundaries, SQL injection, XSS, responsive design, and more.
- ✅ **3 Search Tests** – valid, empty, and invalid search queries.
- ✅ **Page Object Model** – clean separation of test logic and page elements.
- ✅ **Cross-browser support** – Chrome, Firefox, Edge (via WebDriverManager).
- ✅ **ExtentReports** – detailed HTML reports with screenshots on failure.
- ✅ **Configurable credentials** – sensitive data stored in `config.properties` (excluded from Git).
- ✅ **Parallel execution** – ready-to-use TestNG XML files.
- ✅ **Professional logging** – Log4j2 with console and file appenders.
- ✅ **Retry mechanism** – flaky tests are automatically retried (up to 2 times).
- ✅ **Data providers** – reduce code duplication for similar test scenarios.
- ✅ **Soft assertions** – collect multiple failures in one test.
- ✅ **Headless mode** – automatically enabled in CI/Docker environments.
- ✅ **Docker support** – run tests in an isolated, reproducible container.
- ✅ **Jenkins pipeline** – ready-to-use `Jenkinsfile` for CI/CD.
- ✅ **GitHub Actions** – workflow for automatic testing on every push.

---

## 🛠 Tech Stack

| Tool / Library       | Version      | Purpose                                    |
|----------------------|--------------|--------------------------------------------|
| Java                 | 17           | Programming language                       |
| Maven                | 3.9+         | Build & dependency management              |
| Selenium WebDriver   | 4.27.0       | Browser automation                         |
| TestNG               | 7.10.2       | Test framework                             |
| WebDriverManager     | 5.9.2        | Automatic driver management                |
| ExtentReports        | 5.1.2        | HTML reporting                             |
| Log4j2               | 2.23.1       | Professional logging                       |
| Docker               | Latest       | Containerization                           |
| Jenkins              | 2.x          | CI/CD pipeline                             |

---

## 📁 Project Structure
Ananas-login-automation/
```
├── src/
│ ├── main/
│ │ └── java/
│ │ └── dragan.stojilkovic.Pages/
│ │ ├── LoginPage.java
│ │ └── SearchBar.java
│ └── test/
│ ├── java/
│ │ └── tests/
│ │ ├── BaseTest.java
│ │ ├── LoginTest.java
│ │ ├── SearchTest.java
│ │ ├── TestListener.java
│ │ ├── RetryAnalyzer.java
│ │ └── RetryListener.java
│ └── resources/
│ ├── config.properties (not committed – see below)
│ └── log4j2.xml
├── extent-reports/ (generated after test run)
├── screenshots/ (screenshots of failed tests)
├── logs/ (automation.log from Log4j2)
├── Dockerfile
├── Jenkinsfile
├── pom.xml
├── testng.xml
├── testng2.xml (parallel)
└── README.md

```

---

## 📦 Prerequisites

- **Java 17** (JDK) installed and `JAVA_HOME` set.
- **Maven** installed and added to `PATH`.
- **Git** (optional, for cloning).
- **Browsers**: Chrome, Firefox, Edge (latest versions) – only needed for local runs.
- **Docker** (optional, for containerised execution).
- **Jenkins** (optional, for CI/CD pipeline).
- **GitHub account** (if you want to clone the repo).

---

## ⚙️ Setup & Configuration

### 1. Clone the repository

```bash
git clone https://github.com/Gagasr10/Ananas-login-automation.git
cd Ananas-login-automation
2. Create config.properties (sensitive data)
This file is excluded from Git for security. You must create it manually.

Create the file src/test/resources/config.properties with the following content:

properties
# Valid credentials (replace with your own test accounts)
valid.email=your_valid_email@example.com
valid.password=your_valid_password
valid.email2=another_valid_email@example.com
valid.password2=another_valid_password

# Base URL
app.url=https://ananas.rs/login

# Timeouts (seconds)
default.wait=10
short.wait=5
⚠️ Never commit real credentials! The .gitignore already excludes this file.

3. Install dependencies
bash
mvn clean install
🚀 Running the Tests
Locally with Maven
Run all tests (default Chrome browser)
bash
mvn clean test
Run tests on a specific browser
bash
mvn clean test -Dbrowser=firefox
Run only LoginTest
bash
mvn clean test -Dtest=LoginTest
Run only SearchTest
bash
mvn clean test -Dtest=SearchTest
Run parallel cross-browser tests
bash
mvn clean test -DsuiteXmlFile=testng2.xml
The testng.xml file is configured for sequential Chrome execution, while testng2.xml runs Firefox and Edge in parallel.

Inside Docker Container
Build the Docker image (includes Java, Maven, Chrome, ChromeDriver):

bash
docker build -t ananas-tests .
Run the tests inside the container:

bash
docker run --rm -v "$(pwd)/extent-reports:/app/extent-reports" -v "$(pwd)/logs:/app/logs" ananas-tests
This will execute mvn clean test inside the container and copy the reports and logs back to your host.

With Jenkins Pipeline
The repository includes a Jenkinsfile that defines a complete CI/CD pipeline:

Agent – uses dockerfile to build the image from the Dockerfile.

Stages – Build, Test.

Post-build actions – publishes ExtentReports and JUnit XML reports, cleans workspace.

To use it:

Create a new Pipeline job in Jenkins.

Set the SCM to your GitHub repository.

Set Script Path to Jenkinsfile.

Run the job – Jenkins will automatically build the Docker image, run the tests, and archive the reports.

📊 Test Reports
After test execution, an HTML report is generated at:

text
extent-reports/extent-report.html
Open this file in any browser to see:

Pass/fail status of each test

Logs and exception details

Screenshots automatically attached for failed tests

Additionally, Log4j2 writes detailed logs to:

Console (real-time)

logs/automation.log (persistent)

🐞 Known Issues & CI Limitations
Application bugs (tests disabled)
The following tests are disabled (@Test(enabled = false)) because the target application does not implement the expected behavior. These are not bugs in the test code – they highlight missing security/validation features.

Test	Expected Behavior	Actual Behavior
testAccountLockoutAfterFailedAttempts	Account lockout after 5 failed login attempts	Generic error message "Email ili lozinka nisu u redu." – account never locks
testMaxPasswordLength	Specific error "Password too long." for 300‑char password	Same generic error as for invalid password
CI environment limitations
In headless CI (GitHub Actions), the following tests are skipped (SkipException) due to unreliable element rendering or unstable login redirects:

testValidLogin and testResponsiveDesign – login redirection not stable in headless Chrome.

All SearchTest tests – search input not reliably found on the homepage in headless mode.

These tests pass successfully when run locally with a visible browser. They are skipped in CI to keep the pipeline green while preserving the valuable test logic for local execution.

🤝 Contributing
This is a personal portfolio project, but suggestions and improvements are welcome. Feel free to open an issue or a pull request.

📄 License
This project is licensed under the MIT License – see the LICENSE file for details (optional, you can add a LICENSE file if you want).

📧 Contact
Created by Dragan Stojilković – Gagasr10
