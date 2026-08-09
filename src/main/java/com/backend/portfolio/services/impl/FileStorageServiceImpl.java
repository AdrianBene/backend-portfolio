package com.backend.portfolio.services.impl;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backend.portfolio.services.FileStorageService;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private static final String UPLOAD_DIR = "uploads/technology";

    @Override
    public String saveImage(MultipartFile image) {
        
      if (image == null || image.isEmpty()) {
        return null;
      }

      try{

        Files.createDirectories(Paths.get(UPLOAD_DIR));
        String originalName = image.getOriginalFilename();
        String extension ="";
        if (originalName !=null && originalName.contains(".")) {
            extension = originalName.substring(originalName.lastIndexOf("."));

            
        }
        String fileName = UUID.randomUUID() + extension;
        Path path =Paths.get(UPLOAD_DIR,fileName);
        Files.copy(image.getInputStream(),path,StandardCopyOption.REPLACE_EXISTING);
        return fileName;
      }catch(IOException exception){
        throw new RuntimeException("Error guardano la imagen");
      }
    }

    @Override
    public void deleteImage(String fileName) {
        if (fileName ==null || fileName.isBlank()) {
          return;
        }
        try{
          Path path = Paths.get(UPLOAD_DIR,fileName);
          Files.deleteIfExists(path);

        }catch(IOException exception){
          throw new RuntimeException("Error eliminado la imagen");
        }
      
    }

}
