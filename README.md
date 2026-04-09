# 🍍 Ananas Login & Search Automation

[![Java Version](https://img.shields.io/badge/Java-17-blue.svg)](https://adoptium.net/)
[![Maven](https://img.shields.io/badge/Maven-3.9+-C71A36.svg)](https://maven.apache.org/)
[![Selenium](https://img.shields.io/badge/Selenium-4.27.0-green.svg)](https://www.selenium.dev/)
[![TestNG](https://img.shields.io/badge/TestNG-7.10.2-red.svg)](https://testng.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

Automated UI tests for the **login functionality** and **search feature** of **[ananas.rs](https://ananas.rs)** – a Serbian e‑commerce platform similar to Amazon.  
The project is written in Java using Selenium WebDriver, TestNG, and Maven, following the **Page Object Model (POM)** design pattern. It generates detailed ExtentReports with screenshots on test failures.

---

## 📋 Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Setup & Configuration](#setup--configuration)
- [Running the Tests](#running-the-tests)
- [Test Reports](#test-reports)
- [Known Issues (Failing Tests)](#known-issues-failing-tests)
- [Contributing](#contributing)
- [License](#license)

---

## ✨ Features

- ✅ **Login Tests** – 19 test cases covering valid/invalid credentials, empty fields, password boundaries, SQL injection, XSS, responsive design, and more.
- ✅ **Search Tests** – valid, empty, and invalid search queries.
- ✅ **Page Object Model** – clean separation of test logic and page elements.
- ✅ **Cross-browser support** – Chrome, Firefox, Edge (via WebDriverManager).
- ✅ **ExtentReports** – detailed HTML reports with screenshots on failure.
- ✅ **Configurable credentials** – sensitive data stored in `config.properties` (excluded from Git).
- ✅ **Parallel execution** – ready-to-use TestNG XML files.

---

## 🛠 Tech Stack

| Tool / Library | Version | Purpose |
|----------------|---------|---------|
| Java | 17 | Programming language |
| Maven | 3.9+ | Build & dependency management |
| Selenium WebDriver | 4.27.0 | Browser automation |
| TestNG | 7.10.2 | Test framework |
| WebDriverManager | 5.9.2 | Automatic driver management |
| ExtentReports | 5.1.2 | HTML reporting |
| SLF4J (no-op) | - | Logging (simple) |

---

## 📁 Project Structure
Ananas-login-automation/
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
│ │ └── TestListener.java
│ └── resources/
│ └── config.properties (not committed – see below)
├── extent-reports/ (generated after test run)
├── screenshots/ (screenshots of failed tests)
├── pom.xml
├── testng.xml
├── testng2.xml (parallel)
└── README.md

text

---

## 📦 Prerequisites

- **Java 17** (JDK) installed and `JAVA_HOME` set.
- **Maven** installed and added to `PATH`.
- **Git** (optional, for cloning).
- **Browsers**: Chrome, Firefox, Edge (latest versions).
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
Run all tests (default Chrome browser)
bash
mvn clean test
Run tests on a specific browser (Chrome, Firefox, Edge)
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

📊 Test Reports
After test execution, an HTML report is generated at:

text
extent-reports/extent-report.html
Open this file in any browser to see:

Pass/fail status of each test

Logs and exception details

Screenshots automatically attached for failed tests

🐞 Known Issues (Failing Tests)
Two tests are intentionally failing because the target application (ananas.rs) does not implement the expected behavior. These are not bugs in the test code – they highlight missing security/validation features.

Test	Expected Behavior	Actual Behavior
testAccountLockoutAfterFailedAttempts	Account lockout after 5 failed login attempts	Generic error message "Email ili lozinka nisu u redu." – account never locks
testMaxPasswordLength	Specific error "Password too long." for 300‑char password	Same generic error as for invalid password
These tests can be temporarily disabled by adding @Test(enabled = false) if a fully green suite is desired.

🤝 Contributing
This is a personal portfolio project, but suggestions and improvements are welcome. Feel free to open an issue or a pull request.

📄 License
This project is licensed under the MIT License – see the LICENSE file for details (optional, you can add a LICENSE file if you want).

📧 Contact
Created by Dragan Stojilković – GitHub Profile

text

---

## ✅ What changed:

- Added **“– a Serbian e‑commerce platform similar to Amazon.”** in the opening paragraph.
- No other changes – the rest of the README remains professional and complete.

You can now **replace your existing `README.md`** with this version, commit, and push:

```bash
git add README.md
git commit -m "Add context: ananas.rs is Serbian Amazon-like platform"
git push origin main