package com.himfirst.vidshare.person;

import com.himfirst.vidshare.base.BaseService;

import java.util.UUID;

public interface PersonService extends BaseService<Person, UUID> {

    PersonModel signUp(PersonModel person);

}
