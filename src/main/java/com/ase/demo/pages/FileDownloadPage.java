package com.ase.demo.pages;

import com.microsoft.playwright.Download;
import com.microsoft.playwright.Page;
import java.nio.file.Path;
import java.util.List;

public class FileDownloadPage extends BasePage {
    private static final String DOWNLOAD_LINKS = "a[href*='download']";

    public FileDownloadPage(Page page) {
        super(page);
    }

    public List<String> getAvailableFiles() {
        return page.locator(DOWNLOAD_LINKS).allTextContents();
    }

    public Download downloadFile(String fileName) {
        // Start waiting for download before clicking
        Download download = page.waitForDownload(() -> {
            page.click("text=" + fileName);
        });
        return download;
    }

    public Download downloadFileByIndex(int index) {
        List<String> files = getAvailableFiles();
        if (index >= 0 && index < files.size()) {
            return downloadFile(files.get(index));
        }
        throw new IndexOutOfBoundsException("File index out of range: " + index);
    }

    public boolean isDownloadLinksVisible() {
        return page.locator(DOWNLOAD_LINKS).count() > 0;
    }

    public int getDownloadLinksCount() {
        return page.locator(DOWNLOAD_LINKS).count();
    }
}