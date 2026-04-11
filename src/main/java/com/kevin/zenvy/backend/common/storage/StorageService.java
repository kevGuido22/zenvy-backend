package com.kevin.zenvy.backend.common.storage;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String upload(MultipartFile file, String fileKey);
    void delete(String fileKey);
}
