package com.himfirst.vidshare.content.video;

import com.himfirst.vidshare.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VideoRepo extends BaseRepository<Video, UUID> {

}
