package com.ase.demo.tests;


import com.ase.demo.base.TestBase;
import com.ase.demo.pages.FileUploadPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUploadTest extends TestBase {

    private Path testFile;

    @BeforeEach
    void createTestFile() throws IOException {
        // Create a test file for upload
        testFile = getUploadsPath().resolve("test-upload.txt");
        Files.write(testFile, "This is a test file for upload".getBytes());
    }

    @Test
    @Tag("smoke")
    void testSingleFileUpload() {
        navigateToPath("/upload");

        FileUploadPage uploadPage = new FileUploadPage(page);
        uploadPage.uploadFile(testFile);

        assertThat(uploadPage.isFileUploaded()).isTrue();
        assertThat(uploadPage.getUploadedFileName()).contains("test-upload.txt");
    }

    @Test
    void testFileSelectionWithoutUpload() {
        navigateToPath("/upload");

        FileUploadPage uploadPage = new FileUploadPage(page);
        uploadPage.selectFile(testFile);

        // Verify file is selected but not uploaded yet
        String selectedFile = uploadPage.getSelectedFileName();
        assertThat(selectedFile).contains("test-upload.txt");
        assertThat(uploadPage.isFileUploaded()).isFalse();
    }

    @Test
    void testLargeFileUpload() throws IOException {
        // Create a larger test file (1MB)
        Path largeFile = getUploadsPath().resolve("large-test-file.txt");
        byte[] content = new byte[1024 * 1024]; // 1MB
        Files.write(largeFile, content);

        navigateToPath("/upload");

        FileUploadPage uploadPage = new FileUploadPage(page);
        uploadPage.uploadFile(largeFile);

        assertThat(uploadPage.isFileUploaded()).isTrue();
        assertThat(uploadPage.getUploadedFileName()).contains("large-test-file.txt");

        // Clean up
        Files.deleteIfExists(largeFile);
    }

    @Test
    void testImageFileUpload() throws IOException {
        // Create a simple image file (empty but with correct extension)
        Path imageFile = getUploadsPath().resolve("test-image.png");
        Files.write(imageFile, "fake-png-content".getBytes());

        navigateToPath("/upload");

        FileUploadPage uploadPage = new FileUploadPage(page);
        uploadPage.uploadFile(imageFile);

        assertThat(uploadPage.isFileUploaded()).isTrue();
        assertThat(uploadPage.getUploadedFileName()).contains("test-image.png");

        // Clean up
        Files.deleteIfExists(imageFile);
    }
}
