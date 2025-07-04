package com.ase.demo.tests;

import com.ase.demo.base.TestBase;
import com.ase.demo.pages.FileDownloadPage;
import com.microsoft.playwright.Download;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileDownloadTest extends TestBase {

    @Test
    void testViewAvailableDownloads() {
        navigateToPath("/download");

        FileDownloadPage downloadPage = new FileDownloadPage(page);

        assertThat(downloadPage.isDownloadLinksVisible()).isTrue();
        assertThat(downloadPage.getDownloadLinksCount()).isGreaterThan(0);

        List<String> availableFiles = downloadPage.getAvailableFiles();
        assertThat(availableFiles).isNotEmpty();

        // Print available files for debugging (optional)
        System.out.println("Available files for download:");
        availableFiles.forEach(System.out::println);
    }

    @Test
    void testDownloadFirstFile() throws IOException {
        navigateToPath("/download");

        FileDownloadPage downloadPage = new FileDownloadPage(page);
        List<String> availableFiles = downloadPage.getAvailableFiles();

        if (!availableFiles.isEmpty()) {
            String firstFileName = availableFiles.get(0);
            Download download = downloadPage.downloadFile(firstFileName);

            // Verify download started
            assertThat(download).isNotNull();
            assertThat(download.suggestedFilename()).isEqualTo(firstFileName);

            // Save the downloaded file
            Path downloadPath = getDownloadsPath().resolve(firstFileName);
            download.saveAs(downloadPath);

            // Verify file was downloaded and saved
            assertThat(Files.exists(downloadPath)).isTrue();
            assertThat(Files.size(downloadPath)).isGreaterThan(0);

            // Clean up
            Files.deleteIfExists(downloadPath);
        }
    }

    @Test
    void testDownloadByIndex() throws IOException {
        navigateToPath("/download");

        FileDownloadPage downloadPage = new FileDownloadPage(page);

        if (downloadPage.getDownloadLinksCount() > 0) {
            Download download = downloadPage.downloadFileByIndex(0);

            assertThat(download).isNotNull();
            String fileName = download.suggestedFilename();

            // Save to downloads directory
            Path downloadPath = getDownloadsPath().resolve(fileName);
            download.saveAs(downloadPath);

            // Verify download
            assertThat(Files.exists(downloadPath)).isTrue();

            // Clean up
            Files.deleteIfExists(downloadPath);
        }
    }

    @Test
    void testMultipleDownloads() throws IOException {
        navigateToPath("/download");

        FileDownloadPage downloadPage = new FileDownloadPage(page);
        List<String> availableFiles = downloadPage.getAvailableFiles();

        // Download first 3 files (or all if less than 3)
        int downloadCount = Math.min(3, availableFiles.size());

        for (int i = 0; i < downloadCount; i++) {
            String fileName = availableFiles.get(i);
            Download download = downloadPage.downloadFile(fileName);

            Path downloadPath = getDownloadsPath().resolve(fileName);
            download.saveAs(downloadPath);

            assertThat(Files.exists(downloadPath)).isTrue();

            // Clean up
            Files.deleteIfExists(downloadPath);
        }
    }

    @Test
    void testDownloadFileInfo() {
        navigateToPath("/download");

        FileDownloadPage downloadPage = new FileDownloadPage(page);
        List<String> availableFiles = downloadPage.getAvailableFiles();

        if (!availableFiles.isEmpty()) {
            String firstFileName = availableFiles.get(0);
            Download download = downloadPage.downloadFile(firstFileName);

            // Test download properties
            assertThat(download.url()).contains("download");
            assertThat(download.suggestedFilename()).isNotEmpty();

            // Cancel download (don't actually save)
            download.cancel();
        }
    }
}
