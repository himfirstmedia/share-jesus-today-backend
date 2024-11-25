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
    public PersonModel signUp(PersonModel request) {
        UUID userId = UUID.randomUUID();

        Person existing = personRepo.findByEmailIgnoreCase(request.getEmail());

        if (existing == null) {
            Person person = modelMapper.map(request, Person.class);
            person.setId(UUID.randomUUID());
            person.setId(userId);
            person.setCreatedTimestamp(LocalDateTime.now());
            person.setCreatedBy(userId);
            person.setUpdatedBy(userId);

            Person saved = super.save(person);

            User user = new User();
            user.setId(userId);
            user.setPassword(request.getPassword().trim());
            user.setCreatedTimestamp(LocalDateTime.now());
            user.setCreatedBy(userId);
            user.setUpdatedBy(userId);
            user.setUserName(request.getEmail());
            user.setPerson(saved);
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
            return modelMapper.map(saved, PersonModel.class);

        } else {
            throw new ApiResponseException("Email already Exists");
        }

    }
}
