<<<<<<< HEAD
# Selenium Java Framework — automationexercise.com

A production-grade Selenium 4 + TestNG automation framework targeting the publicly available e-commerce testing site **https://www.automationexercise.com**.

---

## 📋 Tech Stack

| Tool | Version | Purpose |
|------|---------|---------|
| Java | 11+ | Language |
| Selenium | 4.18.1 | Browser automation |
| TestNG | 7.9.0 | Test runner + DataProviders |
| WebDriverManager | 5.8.0 | Auto-manage browser drivers |
| ExtentReports | 5.1.1 | HTML test reports |
| JavaFaker | 1.0.2 | Realistic random test data |
| Maven | 3.9+ | Build & dependency management |

---

## 📁 Project Structure

```
selenium-automationexercise/
├── pom.xml
├── README.md
└── src/
    ├── main/java/com/autoex/
    │   ├── pages/
    │   │   ├── BasePage.java            — Common WebDriver helpers
    │   │   ├── HomePage.java            — Navigation + login state
    │   │   ├── LoginPage.java           — Login + signup forms
    │   │   ├── RegisterPage.java        — Account creation form
    │   │   ├── ProductsPage.java        — Product listing + search
    │   │   ├── ProductDetailPage.java   — Product detail + add-to-cart
    │   │   ├── CartPage.java            — Shopping cart
    │   │   └── CheckoutPage.java        — Checkout + payment
    │   └── utils/
    │       ├── ConfigReader.java        — Reads config.properties
    │       ├── DriverManager.java       — ThreadLocal WebDriver init
    │       ├── ExtentManager.java       — ExtentReports singleton
    │       └── FakerUtil.java           — Random test data factory
    └── test/
        ├── java/com/autoex/
        │   ├── tests/
        │   │   ├── BaseTest.java                      — @Before/@After setup
        │   │   ├── Workflow1_RegistrationLoginTest.java
        │   │   ├── Workflow2_ProductSearchCartTest.java
        │   │   └── Workflow3_CheckoutTest.java
        │   └── dataproviders/
        │       ├── RegistrationDataProvider.java
        │       ├── LoginDataProvider.java
        │       ├── ProductCartDataProvider.java
        │       └── CheckoutDataProvider.java
        └── resources/
            ├── config.properties        — Browser, URL, timeouts
            └── testng.xml               — Test suite definition
```

---

## 🚀 Quick Start

### Prerequisites
- Java 11 or higher (`java -version`)
- Maven 3.9+ (`mvn -version`)
- Google Chrome installed (or set `browser=firefox` / `browser=edge` in config.properties)

### Run All Tests
```bash
mvn clean test
```

### Run a Specific Workflow
```bash
# Workflow 1 — Registration & Login
mvn test -Dgroups="registration,login"

# Workflow 2 — Product Search & Cart
mvn test -Dgroups="products,cart"

# Workflow 3 — Checkout & Order
mvn test -Dgroups="checkout"

# Smoke only (positive cases)
mvn test -Dgroups="positive"
```

### Headless Mode
Edit `src/test/resources/config.properties`:
```properties
headless=true
```

### Change Browser
```properties
browser=firefox    # or: edge
```

---

## 🧪 Test Workflows

### Workflow 1 — User Registration & Login
**File:** `Workflow1_RegistrationLoginTest.java`

| # | Test | Type | DataProvider |
|---|------|------|-------------|
| 1 | Register new account, verify confirmation | ✅ Positive | `registrationPositiveData` (2 rows) |
| 2 | Login with valid credentials | ✅ Positive | `loginPositiveData` |
| 3 | Login → Logout cycle, session cleared | ✅ Positive | — |
| 4 | Login/Register page has both form headings | ✅ Positive | — |
| 5 | Login fails (wrong/missing credentials) | ❌ Negative | `loginNegativeData` (6 rows) |
| 6 | Signup fails (duplicate/empty/invalid) | ❌ Negative | `registrationNegativeData` (5 rows) |
| 7 | Duplicate email registration blocked | ❌ Negative | — |
| 8 | Login with XSS/SQL/whitespace inputs | ⚠️ Edge | `loginEdgeData` (6 rows) |
| 9 | Signup with unicode/XSS/boundary names | ⚠️ Edge | `registrationEdgeData` (7 rows) |
| 10 | Home page accessible without login | ⚠️ Edge | — |

---

### Workflow 2 — Product Search & Shopping Cart
**File:** `Workflow2_ProductSearchCartTest.java`

| # | Test | Type | DataProvider |
|---|------|------|-------------|
| 1 | Products page loads with items | ✅ Positive | — |
| 2 | Search keyword returns matching results | ✅ Positive | `searchPositiveData` (5 rows) |
| 3 | Product detail page shows name & price | ✅ Positive | — |
| 4 | Add product to cart from listings page | ✅ Positive | — |
| 5 | Add products by index, verify added | ✅ Positive | `cartPositiveData` (3 rows) |
| 6 | Add product from detail page, quantity > 1 | ✅ Positive | — |
| 7 | Remove item from cart | ✅ Positive | — |
| 8 | No-match search shows empty state | ❌ Negative | `searchNegativeData` (3 rows) |
| 9 | Checkout without login prompts login | ❌ Negative | — |
| 10 | Empty cart page accessible | ❌ Negative | — |
| 11 | XSS/SQL/long search handled safely | ⚠️ Edge | `searchEdgeData` (6 rows) |
| 12 | Add same product twice | ⚠️ Edge | — |
| 13 | Direct URL to /products works | ⚠️ Edge | — |

---

### Workflow 3 — Checkout & Order Placement
**File:** `Workflow3_CheckoutTest.java`

> ⚠️ **Pre-condition:** Account `test@test.com` / `test123` must exist. Either create it manually on the site or run `testRegistrationSuccess` first (it auto-deletes the account after — so create and keep one manually).

| # | Test | Type | DataProvider |
|---|------|------|-------------|
| 1 | Full checkout: login → product → pay → confirm | ✅ Positive | `checkoutPositiveData` (3 rows) |
| 2 | Delivery address pre-filled from account | ✅ Positive | — |
| 3 | Order summary shows cart items | ✅ Positive | — |
| 4 | Checkout with order comment | ✅ Positive | — |
| 5 | Missing payment fields blocks order | ❌ Negative | `checkoutNegativeData` (5 rows) |
| 6 | Guest cannot access checkout | ❌ Negative | — |
| 7 | Direct /payment URL blocked without order | ❌ Negative | — |
| 8 | XSS/SQL/expired in payment fields | ⚠️ Edge | `checkoutEdgeData` (8 rows) |
| 9 | Back button after order confirmation | ⚠️ Edge | — |

---

## 📊 Reports

After running tests, the HTML report is at:
```
test-output/ExtentReport.html
```
Screenshots of failures are saved to:
```
test-output/screenshots/
```

Open `ExtentReport.html` in any browser to view the full run with pass/fail/skip status, logs, and screenshots.

---

## ⚙️ Configuration Reference

`src/test/resources/config.properties`:

| Property | Default | Description |
|----------|---------|-------------|
| `base.url` | `https://www.automationexercise.com` | Site under test |
| `browser` | `chrome` | `chrome`, `firefox`, or `edge` |
| `implicit.wait` | `10` | Implicit wait in seconds |
| `explicit.wait` | `20` | Explicit (WebDriverWait) in seconds |
| `headless` | `false` | Run browser without UI |
| `screenshot.on.failure` | `true` | Auto-screenshot on test failure |
| `reports.path` | `test-output/ExtentReport.html` | Report output location |

---

## 🏗️ Design Patterns

- **Page Object Model (POM)** — Each page has its own class; tests never access locators directly.
- **ThreadLocal WebDriver** — `DriverManager` stores drivers per thread, enabling parallel execution.
- **TestNG DataProviders** — Test data is cleanly separated in `dataproviders/` package; each method has positive, negative, and edge datasets.
- **Fluent waits** — `BasePage` wraps all interactions with `WebDriverWait` to handle dynamic content.
- **ExtentReports** — Every test logs steps, passes info/warnings/failures, and attaches screenshots automatically via `BaseTest`.

---

## 🔧 Troubleshooting

| Problem | Solution |
|---------|----------|
| `SessionNotCreatedException` | Run `mvn dependency:resolve` to re-download WebDriverManager |
| Workflow 3 tests skip | Create account `test@test.com` / `test123` on the site first |
| Tests fail due to ads | The site shows overlay ads — the framework uses `jsClick` and waits to handle them |
| `StaleElementReferenceException` | Increase `explicit.wait` in config.properties |
| Chrome not found | Install Chrome or switch to `browser=firefox` |
=======
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

CI/CD Integration
This project is fully automated. Every code change pushed to the repository triggers a GitHub Actions workflow, which automatically compiles the project and executes the full test suite of 32 cases to ensure continuous quality.


    
>>>>>>> a389fe6c6714e347e595844876204f702deaa4a6
