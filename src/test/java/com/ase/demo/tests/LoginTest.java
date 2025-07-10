package com.ase.demo.tests;

import com.ase.demo.base.TestBase;
import com.ase.demo.pages.LoginPage;
import com.ase.demo.pages.SecureAreaPage;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LoginTest extends TestBase {

    @Test
    @Tag("smoke")
    void testSuccessfulLogin() {
        navigateToPath("/login");

        LoginPage loginPage = new LoginPage(page);
        loginPage.login("tomsmith", "SuperSecretPassword!");

        SecureAreaPage secureArea = new SecureAreaPage(page);
        assertThat(secureArea.getSuccessMessage())
                .contains("You logged into a secure area!");
        assertThat(secureArea.isLogoutButtonVisible()).isTrue();
    }

    @Test
    void testInvalidLogin() {
        navigateToPath("/login");

        LoginPage loginPage = new LoginPage(page);
        loginPage.login("invaliduser", "invalidpass");

        assertThat(loginPage.isErrorDisplayed()).isTrue();
        assertThat(loginPage.getErrorMessage())
                .contains("Your username is invalid!");
    }

    @Test
    void testEmptyCredentials() {
        navigateToPath("/login");

        LoginPage loginPage = new LoginPage(page);
        loginPage.clickLogin();

        assertThat(loginPage.isErrorDisplayed()).isTrue();
        assertThat(loginPage.getErrorMessage())
                .contains("Your username is invalid!");
    }
}