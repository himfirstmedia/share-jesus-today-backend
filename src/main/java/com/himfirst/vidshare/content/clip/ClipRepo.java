package com.himfirst.vidshare.content.clip;

import com.himfirst.vidshare.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClipRepo extends BaseRepository<Clip, UUID> {
}
