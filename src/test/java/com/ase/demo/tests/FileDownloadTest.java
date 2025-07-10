package com.ase.demo.tests;

import com.ase.demo.base.TestBase;
import com.ase.demo.pages.FileDownloadPage;
import com.microsoft.playwright.Download;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

public class FileDownloadTest extends TestBase {

    @Test
    @Tag("smoke")
    void testViewAvailableDownloads() {
        step("Navigate to /download", () -> navigateToPath("/download"));

        FileDownloadPage downloadPage = step("Initialize FileDownloadPage", () -> new FileDownloadPage(page));

        step("Check if download links are visible", () ->
                assertThat(downloadPage.isDownloadLinksVisible()).isTrue());

        step("Check that there are available download links", () ->
                assertThat(downloadPage.getDownloadLinksCount()).isGreaterThan(0));

        List<String> availableFiles = step("Get available file names", downloadPage::getAvailableFiles);

        step("Verify available files list is not empty", () ->
                assertThat(availableFiles).isNotEmpty());
    }

    @Test
    void testDownloadByIndex() {
        step("Navigate to /download", () -> navigateToPath("/download"));
        FileDownloadPage downloadPage = new FileDownloadPage(page);

        if (downloadPage.getDownloadLinksCount() > 0) {
            Download download = step("Download first file by index", () ->
                    downloadPage.downloadFileByIndex(0));

            assertThat(download).isNotNull();
            String fileName = download.suggestedFilename();

            Path downloadPath = getDownloadsPath().resolve(fileName);

            step("Save file to: " + downloadPath, () -> download.saveAs(downloadPath));

            step("Verify file exists at download path", () ->
                    assertThat(Files.exists(downloadPath)).isTrue());

            step("Clean up downloaded file", () -> Files.deleteIfExists(downloadPath));
        }
    }

    @Test
    void testMultipleDownloads() {
        step("Navigate to /download", () -> navigateToPath("/download"));
        FileDownloadPage downloadPage = new FileDownloadPage(page);

        List<String> availableFiles = downloadPage.getAvailableFiles();
        int downloadCount = Math.min(3, availableFiles.size());

        for (int i = 0; i < downloadCount; i++) {
            final int index = i;
            step("Downloading file: " + availableFiles.get(index), () -> {
                String fileName = availableFiles.get(index);
                Download download = downloadPage.downloadFile(fileName);
                Path downloadPath = getDownloadsPath().resolve(fileName);

                download.saveAs(downloadPath);
                assertThat(Files.exists(downloadPath)).isTrue();

                Files.deleteIfExists(downloadPath);
            });
        }
    }

    @Test
    void testDownloadFileInfo() {
        step("Navigate to /download", () -> navigateToPath("/download"));
        FileDownloadPage downloadPage = new FileDownloadPage(page);
        List<String> availableFiles = downloadPage.getAvailableFiles();

        if (!availableFiles.isEmpty()) {
            String firstFileName = availableFiles.getFirst();
            Download download = step("Start download for: " + firstFileName, () ->
                    downloadPage.downloadFile(firstFileName));

            step("Check download URL contains 'download'", () ->
                    assertThat(download.url()).contains("download"));

            step("Check suggested filename is not empty", () ->
                    assertThat(download.suggestedFilename()).isNotEmpty());

            step("Cancel the download (simulate abort)", download::cancel);
        }
    }
}
