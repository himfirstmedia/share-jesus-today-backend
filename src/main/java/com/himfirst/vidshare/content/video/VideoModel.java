package com.himfirst.vidshare.content.video;

import com.himfirst.vidshare.base.BaseModel;
import com.himfirst.vidshare.person.Person;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoModel extends BaseModel {
    @NotNull(message = "Title must be provided")
    private String title;
    @NotNull(message = "Caption must be provided")
    private String caption;
    private String url;
    private int duration;
    private String thumbnailUrl;
    private Visibility visibility = Visibility.PUBLIC;
    private boolean reported = false;
    @NotNull(message = "Uploader must be provided")
    private Person uploader;
}
