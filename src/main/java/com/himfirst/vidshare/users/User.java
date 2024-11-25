package com.himfirst.vidshare.users;

import com.himfirst.vidshare.person.Person;
import com.himfirst.vidshare.users.userRole.UserRole;
import com.himfirst.vidshare.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( schema="users")
public class User extends BaseEntity implements Serializable {
    @Column(nullable = false, unique = true)
    private String userName;
    @Column(nullable = false)
    private String password;
    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;
    private boolean isEnabled = true;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Person person;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<UserRole> roles;

}
