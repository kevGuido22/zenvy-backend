package com.kevin.zenvy.backend.common.storage;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class TestStorageService implements StorageService{
    @Override
    public String upload(MultipartFile file, String fileKey) {
        return "https://fake-storage.com/" + fileKey;
    }

    @Override
    public void delete(String fileKey) {

    }
}
