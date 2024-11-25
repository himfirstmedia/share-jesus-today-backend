package com.himfirst.vidshare.security;

import com.himfirst.vidshare.users.User;
import com.himfirst.vidshare.users.UserRepo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
   private final UserRepo userRepo;


    public MyUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user =getUser(userName);

        MyUserDetails userDetails=new MyUserDetails(user);
        if(user !=null){
            return userDetails;

        }else{
            throw new UsernameNotFoundException("User not exist with name : " + userName);
        }
    }

    public User getUser(String userName) {
        return userRepo.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userName));
    }


    public User currentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        return  getUser(userName);
    }

}
