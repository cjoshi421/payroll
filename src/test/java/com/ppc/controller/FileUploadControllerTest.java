package com.ppc.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.controller.FileUploadController;
import com.example.demo.service.FileProcessingService;


import java.io.IOException;

@ExtendWith(MockitoExtension.class)
public class FileUploadControllerTest {

	@InjectMocks
	private FileUploadController fileUploadController;
	
	@Mock
	private FileProcessingService fileProcessingService;

	@Test 
	public void testUploadFile_ValidCSVFile_Success() throws IOException {
		String fileName = "test.csv";
		String content = "CSV File Content";
		MultipartFile file = new MockMultipartFile(fileName, fileName, "text/csv", content.getBytes());
		String result = fileUploadController.uploadFile(file);
		assertEquals("File processed successfully.", result);
		verify(fileProcessingService).processFile(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
	}

	@Test
	public void testUploadFile_InvalidFileType_Failure() throws IOException {
		String fileName = "test.pdf";
		String content = "Text File Content";
		MultipartFile file = new MockMultipartFile(fileName, fileName, "text/plain", content.getBytes());
		String result = fileUploadController.uploadFile(file);
		assertEquals("Invalid file type. Only .csv and .txt files are allowed", result);
		verify(fileProcessingService, never()).processFile(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
	}
}
