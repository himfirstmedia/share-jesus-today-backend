package com.himfirst.vidshare.content.video;

import com.himfirst.vidshare.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VideoRepo extends BaseRepository<Video, UUID> {
    List<Video> findAllByOrderByCreatedTimestampDesc();

    Page<Video> findByUploaderId(UUID personId, Pageable pageable);

}
