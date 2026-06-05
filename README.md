AutomationExercise Selenium Framework
This project is a robust Test Automation Framework built for AutomationExercise. It follows the Page Object Model (POM) design pattern and is built using Java, Selenium WebDriver, and TestNG.

🛠 Tech Stack
Java 11

Selenium WebDriver 4.x

TestNG (Test framework)

WebDriverManager (Browser driver management)

ExtentReports (Test reporting)

Maven (Dependency management)

GitHub Actions (CI/CD Pipeline)

📂 Project Structure
Plaintext
├── src/main/java/com/autoex
│   ├── pages/           # Page Object Model classes
│   ├── utils/           # Utility and Helper classes
├── src/test/java/com/autoex
│   ├── tests/           # Test classes (TestNG)
│   └── dataproviders/   # Data-driven test sources
└── src/test/resources/
    └── testng.xml       # Test Suite configuration
🚀 How to Run Locally
Ensure you have JDK 11 and Maven installed on your machine.

Clone the repository:

Bash
Run the tests using Maven:

Bash
mvn test
⚙️ CI/CD Pipeline
The project is fully integrated with GitHub Actions. Every push or pull request to the main branch automatically triggers the test suite defined in testng.xml, ensuring continuous quality assurance.
