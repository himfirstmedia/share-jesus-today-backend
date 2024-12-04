package com.himfirst.vidshare.content.video;

import com.himfirst.vidshare.base.BaseService;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

public interface VideoService extends BaseService<Video, UUID> {
    String uploadVideo(File uploadDir, MultipartFile file);

    VideoModel uploadVideo(MultipartFile file, String title, String caption, String user);

    List<VideoModel> getAll();
}
