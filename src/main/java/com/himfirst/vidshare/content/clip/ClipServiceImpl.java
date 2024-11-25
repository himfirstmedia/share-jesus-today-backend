package com.himfirst.vidshare.content.clip;

import com.himfirst.vidshare.base.BaseServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class ClipServiceImpl extends BaseServiceImpl<Clip, UUID> implements ClipService {

    public ClipServiceImpl(ClipRepo clipRepo, ModelMapper modelMapper) {
        super(clipRepo, modelMapper);
    }

    @Override
    public Class<Clip> getClassName() {
        return Clip.class;
    }
}
