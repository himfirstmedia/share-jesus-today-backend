package com.himfirst.vidshare.users;

import com.himfirst.vidshare.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepo extends BaseRepository<User, UUID> {

    Optional<User> findByUserName(String userName);

    User findByUserNameIgnoreCase(String userName);

    User findByPersonId(UUID personId);

}
