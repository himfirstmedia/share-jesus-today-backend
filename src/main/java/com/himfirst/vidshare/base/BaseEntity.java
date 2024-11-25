package com.himfirst.vidshare.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity implements Serializable {

    @Id
    private UUID id;
    @Column(nullable = false, length = 1)
    private boolean active = true;
    @Column(nullable=false, length=50)
    private UUID createdBy;
    @Column(nullable=false)
    private  LocalDateTime createdTimestamp;
    @Column(nullable=false, length=50)
    private UUID updatedBy;
    @Column(nullable=false)
    private LocalDateTime updatedTimestamp=LocalDateTime.now();
}
