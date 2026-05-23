package com.shekhar.shopeasy.util;

import com.shekhar.shopeasy.exception.InvalidImageException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileUploadUtil {

    private static final String UPLOAD_DIR =
            System.getProperty("user.dir") +
            "/src/main/resources/static/images/profile-images/";

    private FileUploadUtil() {
    }

    public static String saveProfileImage(
            MultipartFile file
    ) throws IOException {

        // =========================
        // NULL CHECK
        // =========================

        if (file == null || file.isEmpty()) {
            return null;
        }

        // =========================
        // CONTENT TYPE CHECK
        // =========================

        String contentType = file.getContentType();

        if (contentType == null ||
                !contentType.startsWith("image/")) {

            throw new InvalidImageException(
                    "Only image files allowed"
            );
        }

        // =========================
        // SIZE CHECK
        // =========================

        long maxSize = 2 * 1024 * 1024;

        if (file.getSize() > maxSize) {

            throw new InvalidImageException(
                    "Image size must be less than 2MB"
            );
        }

        // =========================
        // CREATE DIRECTORY
        // =========================

        File uploadFolder = new File(UPLOAD_DIR);

        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }

        // =========================
        // FILE EXTENSION
        // =========================

        String originalName =
                file.getOriginalFilename();

        String extension = "";

        if (originalName != null &&
                originalName.contains(".")) {

            extension = originalName.substring(
                    originalName.lastIndexOf(".")
            );
        }

        // =========================
        // UNIQUE FILE NAME
        // =========================

        String fileName =
                UUID.randomUUID() + extension;

        // =========================
        // SAVE FILE
        // =========================

        File destination =
                new File(UPLOAD_DIR + fileName);

        file.transferTo(destination);

        return fileName;
    }
}