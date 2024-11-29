package com.himfirst.vidshare.content.video;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/video")
@CrossOrigin
public class VideoController {
    private final VideoService videoService;
    private static final String UPLOAD_DIR = "/path/to/upload/directory";

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadVideo(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Failed to upload video. File is empty.", HttpStatus.OK);
        }
        if (!Objects.equals(file.getContentType(), "video/mp4")) {
            return new ResponseEntity<>("Failed to upload video. Only MP4 files are allowed.", HttpStatus.OK);
        }

        // Create the upload directory if it doesn't exist
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        return new ResponseEntity<>(videoService.uploadVideo(uploadDir, file), HttpStatus.OK);

    }

    @GetMapping("/stream/{videoId}")
    public ResponseEntity<Resource> streamVideo(@PathVariable UUID videoId) {
        String fileName = videoId.toString() + ".mp4";

        // Construct the path to the video file
        Path videoPath = Paths.get(UPLOAD_DIR, fileName);

        try {
            // Read the video file into a byte array
            byte[] videoBytes = Files.readAllBytes(videoPath);

            // Wrap the byte array in a ByteArrayResource
            ByteArrayResource videoResource = new ByteArrayResource(videoBytes);

            // Build the response with the video file
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("video/mp4"));

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(videoBytes.length)
                    .body(videoResource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
