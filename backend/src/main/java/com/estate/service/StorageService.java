package com.estate.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class StorageService {

    private final String uploadDir = "uploads";
    private final Path uploadPath = Paths.get(uploadDir);

    public String store(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        InputStream inputStream = file.getInputStream();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

        return file.getOriginalFilename();
    }

    public byte[] retrieve(String fileName) throws IOException {
        Path filePath = uploadPath.resolve(fileName);
        return Files.readAllBytes(filePath);
    }

}
