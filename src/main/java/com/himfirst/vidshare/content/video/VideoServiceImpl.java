package com.himfirst.vidshare.content.video;

import com.himfirst.vidshare.base.BaseServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
@Transactional
public class VideoServiceImpl extends BaseServiceImpl<Video, UUID> implements VideoService {

    public VideoServiceImpl(VideoRepo videoRepo, ModelMapper modelMapper) {
        super(videoRepo, modelMapper);
    }

    @Override
    public Class<Video> getClassName() {
        return Video.class;
    }

    @Override
    public String uploadVideo(File uploadDir, MultipartFile file) {
        // Create a new file in the upload directory
        File uploadFile = new File(uploadDir.getAbsolutePath() + File.separator + file.getOriginalFilename());

        // Write the uploaded file to the newly created file
        try (FileOutputStream fos = new FileOutputStream(uploadFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "Video uploaded successfully";
    }
}
