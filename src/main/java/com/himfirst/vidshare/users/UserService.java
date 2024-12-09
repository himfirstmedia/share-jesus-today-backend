package com.himfirst.vidshare.users;

import com.himfirst.vidshare.base.BaseService;

import java.util.UUID;

public interface UserService extends BaseService<User, UUID> {
    User findByPersonId(UUID personId);
    User findByUserName(String userName);
}
