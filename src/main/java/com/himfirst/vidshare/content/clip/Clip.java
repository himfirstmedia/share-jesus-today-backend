package com.himfirst.vidshare.content.clip;

import com.himfirst.vidshare.base.BaseEntity;
import com.himfirst.vidshare.content.video.Video;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( schema="content")
public class Clip extends BaseEntity implements Serializable {
    @Column(nullable = false)
    private String url;
    @Column(nullable = false)
    private int duration;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Video video;
}
