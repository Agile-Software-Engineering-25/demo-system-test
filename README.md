# Playwright Java System Test Blueprint

[![Smoke Tests Execution](https://github.com/Agile-Software-Engineering-25/demo-system-test/actions/workflows/smoke_test.yml/badge.svg)](https://github.com/Agile-Software-Engineering-25/demo-system-test/actions/workflows/smoke_test.yml)
[![Full Tests Execution](https://github.com/Agile-Software-Engineering-25/demo-system-test/actions/workflows/full_test.yml/badge.svg)](https://github.com/Agile-Software-Engineering-25/demo-system-test/actions/workflows/full_test.yml)

This is a **demo project** that showcases **best practices** for system testing using [Microsoft Playwright](https://playwright.dev/java/) in **Java** with **JUnit 5**, **Allure Reports**, and **GitHub Actions**. Created for a university course.

---

## 🧰 Tech Stack

- Java 21
- Maven
- Playwright (Java)
- JUnit 5
- Allure Reports
- GitHub Actions

## 🚀 Quick Start

### 1. Clone the repository

```bash
git clone https://github.com/Agile-Software-Engineering-25/demo-system-test.git
cd demo-system-test
```
### 2. Install Playwright Browsers
Run once after cloning:

```bash
mvn exec:java -e -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install"
```
### 3. Run tests locally
Run all tests:

```bash
mvn clean test
```
Run smoke tests only:

```bash
mvn clean test -Psmoke
```

### 4. Serve Allure Report locally
```bash
mvn allure:serve
```
This will open a local server in your browser to view the test results.

### 🤖 GitHub Actions
Two workflows are available:

| Workflow       | Trigger           | Description                 |
|----------------|-------------------|-----------------------------|
| smoke_test.yml | Push to main / PR | Fast feedback on core flows |
| full_test.yml  | Manual / schedule | Full test suite run         |

### 📊 Test Reports

During each workflow run, the Allure report is automatically uploaded to the `gh-pages` branch and served via GitHub Pages:

- [Smoke Report](https://agile-software-engineering-25.github.io/demo-system-test/smoke/)
- [Full Report](https://agile-software-engineering-25.github.io/demo-system-test/full/)
