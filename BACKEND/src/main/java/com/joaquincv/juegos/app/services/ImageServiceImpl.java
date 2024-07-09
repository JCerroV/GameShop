package com.joaquincv.juegos.app.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;

@Service
public class ImageServiceImpl implements ImageService{
	
	
	private static final String IMAGE_DIR = "src/main/resources/static/images/";

	@Override
	public String saveImage(MultipartFile file,String title) {
		try {
			Path dirPath = Paths.get(IMAGE_DIR);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }
            title=title.replaceAll("\\s+", "_");
            title=title.replaceAll("[^a-zA-Z0-9\\-_]", "_");
			String fileName = title+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			Path filePath=Paths.get(IMAGE_DIR,fileName);
			
			Files.copy(file.getInputStream(), filePath,StandardCopyOption.REPLACE_EXISTING);
	        return "http://localhost:8080/images/"+fileName;	
		} catch (IOException e) {
			throw new RuntimeException("Error al guardar la imagen");
		}
	}
	
	@Override
	public void deleteImage(String fileName) throws IOException {
        Path filePath = Paths.get(IMAGE_DIR, fileName);
        Files.deleteIfExists(filePath);
    }

}
