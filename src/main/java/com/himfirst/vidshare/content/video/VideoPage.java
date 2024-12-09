package com.himfirst.vidshare.content.video;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoPage {
    private int totalPages;
    private long totalElements;
    private List<VideoModel> data;

}
