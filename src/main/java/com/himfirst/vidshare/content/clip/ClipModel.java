package com.himfirst.vidshare.content.clip;

import com.himfirst.vidshare.base.BaseModel;
import com.himfirst.vidshare.content.video.VideoModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClipModel extends BaseModel {
    private String name;
    private String description;
    private String url;
    private int duration;
    private VideoModel video;
}
