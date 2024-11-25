package com.himfirst.vidshare.person;

import com.himfirst.vidshare.base.BaseServiceImpl;
import com.himfirst.vidshare.exceptions.ApiResponseException;
import com.himfirst.vidshare.users.User;
import com.himfirst.vidshare.users.UserService;
import com.himfirst.vidshare.users.userRole.Role;
import com.himfirst.vidshare.users.userRole.UserRole;
import com.himfirst.vidshare.users.userRole.UserRoleService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Service
@Transactional
public class PersonServiceImpl extends BaseServiceImpl<Person, UUID> implements PersonService {

    private final ModelMapper modelMapper;

    private final UserService userService;

    private final UserRoleService userRoleService;
    private final PersonRepo personRepo;

    private final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);

    public PersonServiceImpl(PersonRepo personRepo, ModelMapper modelMapper, UserService userService, UserRoleService userRoleService) {
        super(personRepo, modelMapper);
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.personRepo = personRepo;
    }

    @Override
    public Class<Person> getClassName() {
        return Person.class;
    }

    @Override
    public PersonModel  signUp(PersonModel request) {
        UUID userId = UUID.randomUUID();

        Person existing = personRepo.findByEmailIgnoreCase(request.getEmail());
        if (existing == null) {
        Person person = modelMapper.map(request, Person.class);
        person.setId(UUID.randomUUID());
        person.setCreatedTimestamp(LocalDateTime.now());
        person.setCreatedBy(userId);
        person.setUpdatedBy(userId);
        person.setOtp(generateRandomOTP());
        person.setOtpVerified(false);
            Person saved = super.save(person);
            return modelMapper.map(saved, PersonModel.class);

        } else {
            //send OTP
            throw new ApiResponseException("Email already Exists OTP Sent");
        }


    }

    @Override
    public PersonModel findByEmail(String email) {
        Person found = personRepo.findByEmailIgnoreCase(email);
        if (found != null) {
            return modelMapper.map(found, PersonModel.class);
        }else {
            throw new ApiResponseException("No user found");
        }

    }

    @Override
    public PersonModel verifyOTP(String email, int OTP) {
        Person found = personRepo.findByEmailIgnoreCase(email);
        if (found != null && OTP == found.getOtp()) {
            found.setOtpVerified(true);
            Person saved = super.update(found);
            return modelMapper.map(saved, PersonModel.class);
        }else {
            throw new ApiResponseException("Invalid OTP");
        }

    }

    @Override
    public PersonModel setPassword(String email,  String password) {
        Person found = personRepo.findByEmailIgnoreCase(email);
        if (found != null && found.isOtpVerified()) {
        UUID userId = found.getCreatedBy();

        User user = new User();
        user.setId(userId);
        user.setPassword(password.trim());
        user.setCreatedTimestamp(LocalDateTime.now());
        user.setCreatedBy(userId);
        user.setUpdatedBy(userId);
        user.setUserName(email);
        user.setPerson(found);
        User savedUser = userService.save(user);

        UserRole userRole = new UserRole();
        userRole.setId(UUID.randomUUID());
        userRole.setId(userId);
        userRole.setCreatedTimestamp(LocalDateTime.now());
        userRole.setCreatedBy(userId);
        userRole.setUpdatedBy(userId);
        userRole.setRole(Role.USER);
        userRole.setUser(savedUser);
        userRoleService.save(userRole);

            return modelMapper.map(found, PersonModel.class);
        }else {
            throw new ApiResponseException("Email not verified");
        }

    }

//    @Override
//    public PersonModel createUser(PersonModel request) {
//        UUID userId = UUID.randomUUID();
//
//        Person existing = personRepo.findByEmailIgnoreCase(request.getEmail());
//
//        if (existing == null) {
//            Person person = modelMapper.map(request, Person.class);
//            person.setId(UUID.randomUUID());
//            person.setId(userId);
//            person.setCreatedTimestamp(LocalDateTime.now());
//            person.setCreatedBy(userId);
//            person.setUpdatedBy(userId);
//
//            Person saved = super.save(person);
//
//            return modelMapper.map(saved, PersonModel.class);
//
//        } else {
//            throw new ApiResponseException("Email already Exists");
//        }
//
//    }

    private int generateRandomOTP() {
        Random random = new Random();
        return 10000 + random.nextInt(90000);
    }


}
