package com.himfirst.vidshare.users.userRole;

import com.himfirst.vidshare.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRoleRepo extends BaseRepository<UserRole, UUID> {
       UserRole findByRoleAndUserId(Role role, UUID userId);

}
