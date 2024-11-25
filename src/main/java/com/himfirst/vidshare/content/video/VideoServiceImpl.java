package com.himfirst.vidshare.content.video;

import com.himfirst.vidshare.base.BaseServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
