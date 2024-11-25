package com.himfirst.vidshare.person;

import com.himfirst.vidshare.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonRepo extends BaseRepository<Person, UUID> {

    Person findByEmailIgnoreCase(String email);

}
