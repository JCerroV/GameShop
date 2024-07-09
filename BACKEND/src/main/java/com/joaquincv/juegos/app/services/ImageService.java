package com.joaquincv.juegos.app.services;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
	
	
	String saveImage(MultipartFile file,String title);
	
	
	void deleteImage(String filename)throws IOException;
	
	

}
