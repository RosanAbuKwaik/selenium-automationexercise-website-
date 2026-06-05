AutomationExercise Selenium Framework
This is a comprehensive Test Automation Framework designed to test the AutomationExercise website. The framework follows the Page Object Model (POM) design pattern and is built with scalability and maintainability in mind.

🚀 Key Highlights
Comprehensive Coverage: The framework includes 32 automated test cases, covering full user flows from registration and login to product browsing, cart management, and checkout processes.

Architecture: Implements a robust POM structure combined with Data-Driven Testing techniques.

Tech Stack:

Java 11 & Selenium WebDriver 4.x

TestNG for advanced test orchestration.

ExtentReports for detailed, professional test execution reporting.

GitHub Actions for automated CI/CD integration.

📂 Project Structure

├── src/main/java/com/autoex
│   ├── pages/           # Page Objects (POM)
│   └── utils/           # Utility and Helper classes
├── src/test/java/com/autoex
│   ├── tests/           # 32+ Automated Test Cases
│   └── dataproviders/   # Data-driven test sources
└── src/test/resources/
    └── testng.xml       # Test Suite configuration

CI/CD Integration
This project is fully automated. Every code change pushed to the repository triggers a GitHub Actions workflow, which automatically compiles the project and executes the full test suite of 32 cases to ensure continuous quality.


    
