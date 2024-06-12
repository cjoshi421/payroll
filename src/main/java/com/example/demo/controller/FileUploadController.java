package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.FileProcessingService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api")
public class FileUploadController {

    @Autowired
    private FileProcessingService fileProcessingService;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            if(fileName == null || (!fileName.endsWith(".csv") && !fileName.endsWith(".txt"))) {
            	return "Invalid file type. Only .csv and .txt files are allowed";
            }
            String orgName = fileName.substring(0, fileName.lastIndexOf('.'));
            Path filePath = Paths.get(System.getProperty("java.io.tmpdir"), fileName);
            Files.write(filePath, file.getBytes());

            fileProcessingService.processFile(filePath.toString(), orgName);
            return "File processed successfully.";
        } catch (IOException e) {
            return "Error processing file: " + e.getMessage();
        }
    }
}
