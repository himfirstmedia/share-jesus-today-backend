package com.himfirst.vidshare.content.video;

import com.himfirst.vidshare.base.BaseEntity;
import com.himfirst.vidshare.person.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( schema="content")
public class Video  extends BaseEntity implements Serializable {
    @Column(nullable = false)
    private String title;
    private String caption;
    @Column(nullable = false)
    private String url;
    @Column(nullable = false)
    private int duration;
    private String thumbnailUrl;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Visibility visibility = Visibility.PUBLIC;
    private boolean reported = false;
//    private Set<Tag> tags;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Person uploader;

}
