package com.ase.demo.pages;

import com.microsoft.playwright.Page;
import java.nio.file.Path;

public class FileUploadPage extends BasePage {
    private static final String FILE_INPUT = "#file-upload";
    private static final String UPLOAD_BUTTON = "#file-submit";
    private static final String UPLOADED_FILES = "#uploaded-files";
    private static final String DRAG_DROP_AREA = "#drag-drop-upload";

    public FileUploadPage(Page page) {
        super(page);
    }

    public void selectFile(Path filePath) {
        page.setInputFiles(FILE_INPUT, filePath);
    }

    public void selectMultipleFiles(Path[] filePaths) {
        page.setInputFiles(FILE_INPUT, filePaths);
    }

    public void clickUpload() {
        page.click(UPLOAD_BUTTON);
    }

    public void uploadFile(Path filePath) {
        selectFile(filePath);
        clickUpload();
    }

    public String getUploadedFileName() {
        return page.textContent(UPLOADED_FILES);
    }

    public boolean isFileUploaded() {
        return page.isVisible(UPLOADED_FILES);
    }

    public String getSelectedFileName() {
        return page.inputValue(FILE_INPUT);
    }

    // For drag and drop functionality (if available)
    public void dragAndDropFile(Path filePath) {
        if (page.isVisible(DRAG_DROP_AREA)) {
            page.setInputFiles(DRAG_DROP_AREA, filePath);
        }
    }
}