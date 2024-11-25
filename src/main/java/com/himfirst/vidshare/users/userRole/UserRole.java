package com.himfirst.vidshare.users.userRole;

import com.himfirst.vidshare.users.User;
import com.himfirst.vidshare.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( schema="users")
public class UserRole extends BaseEntity implements Serializable {
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
