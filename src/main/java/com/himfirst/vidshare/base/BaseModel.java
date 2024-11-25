package com.himfirst.vidshare.base;

import com.himfirst.vidshare.config.ValidUuid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseModel {
    private UUID id;
    private boolean active  = true;
    @ValidUuid
    private UUID createdBy;
    @ValidUuid
    private UUID updatedBy;
    private LocalDateTime createdTimestamp;
    private LocalDateTime updatedTimestamp;
}
