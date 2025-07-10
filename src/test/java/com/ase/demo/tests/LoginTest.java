package com.ase.demo.tests;

import com.ase.demo.base.TestBase;
import com.ase.demo.pages.LoginPage;
import com.ase.demo.pages.SecureAreaPage;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static io.qameta.allure.Allure.step;

public class LoginTest extends TestBase {

    @Test
    @Tag("smoke")
    void testSuccessfulLogin() {
        step("Navigate to Login Page", () -> navigateToPath("/login"));

        step("Login with valid credentials", () -> {
            LoginPage loginPage = new LoginPage(page);
            loginPage.login("tomsmith", "SuperSecretPassword!");
        });

        step("Verify successful login message and logout button", () -> {
            SecureAreaPage secureAreaPage = new SecureAreaPage(page);
            assertThat(secureAreaPage.getSuccessMessage())
                    .contains("You logged into a secure area!");
            assertThat(secureAreaPage.isLogoutButtonVisible()).isTrue();
        });
    }

    @Test
    void testInvalidLogin() {
        step("Navigate to Login Page", () -> navigateToPath("/login"));

        step("Attempt login with invalid credentials", () -> {
            LoginPage loginPage = new LoginPage(page);
            loginPage.login("invalid-user", "invalid-pass");
        });

        step("Verify error message for invalid login", () -> {
            LoginPage loginPage = new LoginPage(page);
            assertThat(loginPage.isErrorDisplayed()).isTrue();
            assertThat(loginPage.getErrorMessage())
                    .contains("Your username is invalid!");
        });
    }

    @Test
    void testEmptyCredentials() {
        step("Navigate to Login Page", () -> navigateToPath("/login"));

        step("Click login without entering credentials", () -> {
            LoginPage loginPage = new LoginPage(page);
            loginPage.clickLogin();
        });

        step("Verify error message for empty credentials", () -> {
            LoginPage loginPage = new LoginPage(page);
            assertThat(loginPage.isErrorDisplayed()).isTrue();
            assertThat(loginPage.getErrorMessage())
                    .contains("Your username is invalid!");
        });
    }
}
