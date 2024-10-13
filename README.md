# LogifutureTask

# Ananas.rs Login Test Automation

## Project Overview
This project contains automated tests for the login functionality of the Ananas.rs web application. The tests are implemented using Selenium WebDriver, TestNG, and Extent Reports for detailed reporting.

## Prerequisites
- Java 8+
- Maven
- Chrome, Firefox, and Edge browsers
- Git (for version control)
- TestNG (integrated through Maven)
- Extent Reports for reporting

## How to Run the Tests

1.  Clone the project from GitHub:
  
   git clone https://github.com/username/repository.git
   
2. Navigate to the project directory:

   cd repository
   
3. Run the tests on Chrome browser:

   mvn clean test -DsuiteXmlFile=testng-chrome.xml
   
4. Run the tests in parallel on Firefox and Edge:

   mvn clean test -DsuiteXmlFile=testng-parallel.xml
   
5. View the reports: 
	After the tests are executed, the report is generated in the extent-reports/extent-report.html. Open this       file in 	your browser to view the detailed test results.


Project Structure

/src/main/java - Contains the main classes, including the Page Object Model (POM) classes.

/src/test/java - Contains the test classes and the implementation of the test cases.

/testng.xml - Configuration file for running tests on different browsers.


Parallel Execution

The tests can be executed in parallel across multiple browsers using the testng-parallel.xml file.


Screenshots and Reports

Screenshots of failed tests are saved in the screenshots directory. The Extent Report is generated in the extent-reports folder, providing a detailed view of test results.

