package com.kevin.zenvy.backend.common.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class S3StorageService implements StorageService{

    private final S3Client s3Client;

    @Override
    public String upload(MultipartFile file, String fileKey) {
        try{
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket("zenvy-bucket")
                    .key(fileKey)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(
                    request,
                    RequestBody.fromBytes(file.getBytes())
            );

            return "https://zenvy-bucket.s3.us-east-2.amazonaws.com/" + fileKey;
        }catch (IOException e)
        {
            throw new RuntimeException("Error uploading file to S3", e);
        }
    }

    @Override
    public void delete(String fileKey) {

    }
}
