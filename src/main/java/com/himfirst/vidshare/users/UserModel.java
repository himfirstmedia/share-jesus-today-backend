package com.himfirst.vidshare.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.himfirst.vidshare.users.userRole.UserRole;
import com.himfirst.vidshare.base.BaseModel;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserModel extends BaseModel {
    @NotNull(message = "userName must be provided")
    @Size(min = 3, message = "userName must be equal or greater than 3 characters")
    private String userName;
    @JsonIgnoreProperties("user")
    private Set<UserRole> roles;
}
