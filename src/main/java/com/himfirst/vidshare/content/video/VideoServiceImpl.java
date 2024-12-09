package com.himfirst.vidshare.content.video;

import com.himfirst.vidshare.base.BaseServiceImpl;
import com.himfirst.vidshare.exceptions.ApiResponseException;
import com.himfirst.vidshare.gcs.file.FileService;
import com.himfirst.vidshare.person.PersonModel;
import com.himfirst.vidshare.person.PersonService;
import com.himfirst.vidshare.users.User;
import com.himfirst.vidshare.users.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class VideoServiceImpl extends BaseServiceImpl<Video, UUID> implements VideoService {
    private final FileService fileService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final VideoRepo videoRepo;

    public VideoServiceImpl(VideoRepo videoRepo, ModelMapper modelMapper, FileService fileService, UserService userService) {
        super(videoRepo, modelMapper);
        this.fileService = fileService;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.videoRepo = videoRepo;
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

    @Override
    public VideoModel uploadVideo(MultipartFile file, String title, String caption, String userName) {
        User user = userService.findByUserName(userName);
        String response = "";
        try {
            response = fileService.uploadVideo(file, user.getId());
            Video uploaded = new Video();
            uploaded.setId(UUID.randomUUID());
            uploaded.setUploader(user.getPerson());
            uploaded.setTitle(title);
            uploaded.setCaption(caption);
            uploaded.setReported(false);
            uploaded.setCreatedTimestamp(LocalDateTime.now());
            uploaded.setCreatedBy(user.getId());
            uploaded.setUpdatedBy(user.getId());
            uploaded.setCreatedTimestamp(LocalDateTime.now());
            uploaded.setVisibility(Visibility.PUBLIC);
            uploaded.setUrl(response);
            uploaded.setDuration(1);
            Video saved = super.save(uploaded);
            return modelMapper.map(saved, VideoModel.class);

        } catch (IOException e) {
            throw new ApiResponseException(e.getMessage());
        }
    }

    @Override
    public List<VideoModel> getAll() {
        List<VideoModel> response = new ArrayList<>();
        List<Video> videos = videoRepo.findAllByOrderByCreatedTimestampDesc();
        for(Video video : videos){
            VideoModel videoModel = modelMapper.map(video, VideoModel.class);
            videoModel.setFullUrl(fileService.publicUrl(video.getUrl()));
            response.add(videoModel);
        }

        return response;
    }

    @Override
    public VideoPage findAll(int page, int size, String sortBy, String sortOrder) {
        Pageable pageable = null;
        if(sortOrder.toUpperCase().contains("ASC")){
            pageable= PageRequest.of(page, size, Sort.by((sortBy)).ascending());
        }else{
            pageable= PageRequest.of(page, size, Sort.by((sortBy)).descending());
        }
        Page<Video> data = videoRepo.findAll(pageable);

        return new VideoPage(data.getTotalPages(), data.getTotalElements(), toModel((data.getContent())));
    }

    @Override
    public VideoPage findByPersonId(UUID personId, int page, int size, String sortBy, String sortOrder) {
        Pageable pageable = null;
        if(sortOrder.toUpperCase().contains("ASC")){
            pageable= PageRequest.of(page, size, Sort.by((sortBy)).ascending());
        }else{
            pageable= PageRequest.of(page, size, Sort.by((sortBy)).descending());
        }
        Page<Video> data = videoRepo.findByUploaderId(personId, pageable);

        return new VideoPage(data.getTotalPages(), data.getTotalElements(), toModel((data.getContent())));
    }

    @Override
    public VideoPage myVideos(String userName, int page, int size, String sortBy, String sortOrder) {
        User user = userService.findByUserName(userName);
        return findByPersonId(user.getPerson().getId(), page, size, sortBy, sortOrder);
    }


    private List<VideoModel> toModel(List<Video> videos) {
        List<VideoModel> response = new ArrayList<>();
        for(Video video : videos){
            VideoModel videoModel = modelMapper.map(video, VideoModel.class);
            videoModel.setFullUrl(fileService.publicUrl(video.getUrl()));
            response.add(videoModel);
        }

        return response;
    }

}
