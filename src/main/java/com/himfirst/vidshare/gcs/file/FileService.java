package com.himfirst.vidshare.gcs.file;

import com.google.cloud.storage.Blob;
import com.google.firebase.cloud.StorageClient;
import com.himfirst.vidshare.exceptions.ApiResponseException;
import com.himfirst.vidshare.security.MyUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class FileService {
    @Autowired
    MyUserDetailsService myUserDetailsService;

    private final Logger logger = LoggerFactory.getLogger(FileService.class);

    public String uploadImage(MultipartFile myFile, String userName) throws IOException {

        logger.info("converting image....");
        InputStream file = new BufferedInputStream(myFile.getInputStream());
        logger.info("getting image name....");
        String fileName = generateFileName(myFile);
        String name = "qcProfile/" + userName + "/" + fileName;
        logger.info("uploading image....");
        StorageClient.getInstance().bucket()
                .create(name, file);
        logger.info("updating profile....");
        //return bucket(name);
        return name;

    }

    public String uploadVideo(MultipartFile myFile, UUID userName) throws IOException {
        logger.info("Validating video file....");
        if (!Objects.requireNonNull(myFile.getContentType()).startsWith("video/")) {
            throw new ApiResponseException("Invalid video file type: " + myFile.getContentType());
        }
        logger.info("converting video....");
        InputStream file = new BufferedInputStream(myFile.getInputStream());
        logger.info("getting video name....");
        String fileName = generateFileName(myFile);
        String name = "shared/videos/" + userName + "/" + fileName;
        logger.info("uploading video....");
        StorageClient.getInstance().bucket()
                .create(name, file);
        logger.info("success, updating user data....");
        return name;
    }

//    private String generateFileName(MultipartFile multiPart) {
//
//        return new Date().getTime() + "-" + Objects.requireNonNull(multiPart.getOriginalFilename())
//                .replace(" ", "_");
//    }

    private String generateFileName(MultipartFile multiPart) {
        // Validate file name
        String originalFilename = multiPart.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new ApiResponseException("File name is invalid.");
        }

        // Generate unique file name
        return new Date().getTime() + "-" + originalFilename
                .replaceAll("[^a-zA-Z0-9._-]", "_");
    }


    public URL signedUrl(String videoName) {
        logger.info("getting video....");
        StorageClient.getInstance().bucket().get(videoName).getBucket();
        return StorageClient.getInstance().bucket().get(videoName).signUrl(14, TimeUnit.DAYS);

    }

    public String publicUrl(String videoName) {
        logger.info("getting public URL for video...");
        Blob videoBlob = StorageClient.getInstance().bucket().get(videoName);

        if (videoBlob == null) {
            return "";
        }

        // Return the media link (public URL)
        return videoBlob.getMediaLink();
    }

    public Blob getBolb(String videoName) {
        logger.info("getting public URL for video...");
        Blob videoBlob = StorageClient.getInstance().bucket().get(videoName);

        if (videoBlob == null) {
           throw new ApiResponseException("video not found");
        }
        return videoBlob;
    }


}
