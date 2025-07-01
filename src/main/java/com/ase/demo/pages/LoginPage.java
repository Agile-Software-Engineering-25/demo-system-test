package com.ase.demo.pages;

import com.microsoft.playwright.Page;

public class LoginPage extends BasePage {
    private static final String USERNAME_INPUT = "#username";
    private static final String PASSWORD_INPUT = "#password";
    private static final String LOGIN_BUTTON = "button[type='submit']";
    private static final String ERROR_MESSAGE = "#flash";

    public LoginPage(Page page) {
        super(page);
    }

    public void enterUsername(String username) {
        page.fill(USERNAME_INPUT, username);
    }

    public void enterPassword(String password) {
        page.fill(PASSWORD_INPUT, password);
    }

    public void clickLogin() {
        page.click(LOGIN_BUTTON);
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    public String getErrorMessage() {
        return page.textContent(ERROR_MESSAGE);
    }

    public boolean isErrorDisplayed() {
        return page.isVisible(ERROR_MESSAGE);
    }
}