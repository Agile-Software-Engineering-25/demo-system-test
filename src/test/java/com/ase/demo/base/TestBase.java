package com.ase.demo.base;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestBase {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    protected static final String BASE_URL = "https://the-internet.herokuapp.com";
    protected static final String DOWNLOADS_DIR = System.getProperty("user.dir") + "/downloads";
    protected static final String UPLOADS_DIR = System.getProperty("user.dir") + "/src/test/resources/uploads";

    @BeforeEach
    void setup() {
        // Create directories if they don't exist
        createDirectoryIfNotExists(DOWNLOADS_DIR);
        createDirectoryIfNotExists(UPLOADS_DIR);

        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setSlowMo(1000)); // Slow down for demo purposes

        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(1280, 720)
                .setAcceptDownloads(true)); // Enable downloads

        page = context.newPage();
    }

    @AfterEach
    void teardown() {
        if (page != null) page.close();
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    protected void navigateToPath(String path) {
        page.navigate(BASE_URL + path);
    }

    protected Path getDownloadsPath() {
        return Paths.get(DOWNLOADS_DIR);
    }

    protected Path getUploadsPath() {
        return Paths.get(UPLOADS_DIR);
    }

    protected Path getDownloadPath() {
        return Paths.get(DOWNLOADS_DIR);
    }

    protected Path getUploadPath() {
        return Paths.get(UPLOADS_DIR);
    }

    private void createDirectoryIfNotExists(String dirPath) {
        File directory = new File(dirPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
}
