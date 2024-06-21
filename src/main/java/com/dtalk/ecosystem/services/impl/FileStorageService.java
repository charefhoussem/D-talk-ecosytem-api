package com.dtalk.ecosystem.services.impl;

import com.dtalk.ecosystem.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class FileStorageService {

    private static final String UPLOAD_DIR = "uploads/";
    public String saveFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new ResourceNotFoundException("Failed to store empty file.");
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path uploadPath = Paths.get(UPLOAD_DIR);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Use current system date and time to create a unique filename
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String uniquePart = LocalDateTime.now().format(formatter);

        String uniqueFilename = uniquePart + "-" + fileName;
        Path filePath = uploadPath.resolve(uniqueFilename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return filePath.toString().replace("\\", "/"); // Normalize path separators
    }


}
