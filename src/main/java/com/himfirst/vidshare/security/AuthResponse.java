package com.himfirst.vidshare.security;

import com.himfirst.vidshare.users.UserModel;
import com.himfirst.vidshare.person.PersonModel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private final String jwt;
    private PersonModel person;
    private UserModel user;
}
