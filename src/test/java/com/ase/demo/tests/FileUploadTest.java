package com.ase.demo.tests;

import com.ase.demo.base.TestBase;
import com.ase.demo.pages.FileUploadPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

public class FileUploadTest extends TestBase {

    private Path testFile;

    @BeforeEach
    void createTestFile() throws IOException {
        testFile = getUploadsPath().resolve("test-upload.txt");
        Files.write(testFile, "This is a test file for upload".getBytes());
    }

    @Test
    @Tag("smoke")
    void testSingleFileUpload() {
        step("Navigate to /upload", () -> navigateToPath("/upload"));
        FileUploadPage uploadPage = new FileUploadPage(page);

        step("Upload test file", () -> uploadPage.uploadFile(testFile));

        step("Check file is uploaded", () ->
                assertThat(uploadPage.isFileUploaded()).isTrue());

        step("Check uploaded file name", () ->
                assertThat(uploadPage.getUploadedFileName()).contains("test-upload.txt"));
    }

    @Test
    void testFileSelectionWithoutUpload() {
        step("Navigate to /upload", () -> navigateToPath("/upload"));
        FileUploadPage uploadPage = new FileUploadPage(page);

        step("Select file without uploading", () -> uploadPage.selectFile(testFile));

        step("Verify file is selected but not uploaded", () -> {
            assertThat(uploadPage.getSelectedFileName()).contains("test-upload.txt");
            assertThat(uploadPage.isFileUploaded()).isFalse();
        });
    }

    @Test
    void testLargeFileUpload() throws IOException {
        Path largeFile = getUploadsPath().resolve("large-test-file.txt");
        byte[] content = new byte[1024 * 1024];
        Files.write(largeFile, content);

        step("Navigate to /upload", () -> navigateToPath("/upload"));
        FileUploadPage uploadPage = new FileUploadPage(page);

        step("Upload large file", () -> uploadPage.uploadFile(largeFile));

        step("Verify large file upload success", () -> {
            assertThat(uploadPage.isFileUploaded()).isTrue();
            assertThat(uploadPage.getUploadedFileName()).contains("large-test-file.txt");
        });

        step("Clean up large file", () -> Files.deleteIfExists(largeFile));
    }

    @Test
    void testImageFileUpload() throws IOException {
        Path imageFile = getUploadsPath().resolve("test-image.png");
        Files.write(imageFile, "fake-png-content".getBytes());

        step("Navigate to /upload", () -> navigateToPath("/upload"));
        FileUploadPage uploadPage = new FileUploadPage(page);

        step("Upload image file", () -> uploadPage.uploadFile(imageFile));

        step("Verify image file uploaded", () -> {
            assertThat(uploadPage.isFileUploaded()).isTrue();
            assertThat(uploadPage.getUploadedFileName()).contains("test-image.png");
        });

        step("Clean up image file", () -> Files.deleteIfExists(imageFile));
    }
}
