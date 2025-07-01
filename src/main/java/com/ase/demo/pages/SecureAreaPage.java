package com.ase.demo.pages;

import com.microsoft.playwright.Page;

public class SecureAreaPage extends BasePage {
    private static final String SUCCESS_MESSAGE = "#flash";
    private static final String LOGOUT_BUTTON = "a[href='/logout']";

    public SecureAreaPage(Page page) {
        super(page);
    }

    public String getSuccessMessage() {
        return page.textContent(SUCCESS_MESSAGE);
    }

    public boolean isLogoutButtonVisible() {
        return page.isVisible(LOGOUT_BUTTON);
    }

    public void logout() {
        page.click(LOGOUT_BUTTON);
    }
}