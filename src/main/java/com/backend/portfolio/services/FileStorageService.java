package com.backend.portfolio.services;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String saveImage(MultipartFile image,String folder);
    void deleteImage(String fileName,String folder);

}
