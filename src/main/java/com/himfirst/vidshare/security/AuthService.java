package com.himfirst.vidshare.security;

import com.himfirst.vidshare.exceptions.InvalidValuesException;
import com.himfirst.vidshare.person.PersonModel;
import com.himfirst.vidshare.users.User;
import com.himfirst.vidshare.users.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final MyUserDetailsService myUserDetailsService;
    private final JwtUtil jwtUtil;
    private final ModelMapper modelMapper;

    private final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public AuthService(AuthenticationManager authenticationManager, MyUserDetailsService myUserDetailsService, JwtUtil jwtUtil, ModelMapper modelMapper) {
        this.authenticationManager = authenticationManager;
        this.myUserDetailsService = myUserDetailsService;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
    }

    public String getJwt(AuthRequest authRequest, HttpServletRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())
            );
        } catch (Exception e) {
            logger.info(e.toString());
            throw new InvalidValuesException(e.getMessage());

        }
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(authRequest.getUserName());
//        User user = myUserDetailsService.getUser(authRequest.getUserName());
        return jwtUtil.generateToken(userDetails);


    }

    public boolean checkUser(AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())
            );
        } catch (Exception e) {
            throw new InvalidValuesException("Incorrect old password");

        }
        return true;
    }

    public AuthResponse authenticate(AuthRequest authRequest, HttpServletRequest request) {
        User user = myUserDetailsService.getUser(authRequest.getUserName());
        String jwt = getJwt(authRequest, request);
        return new AuthResponse(jwt, modelMapper.map(user.getPerson(), PersonModel.class), modelMapper.map(user, UserModel.class));

    }

}
