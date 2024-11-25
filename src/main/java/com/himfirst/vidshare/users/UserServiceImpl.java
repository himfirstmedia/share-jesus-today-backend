package com.himfirst.vidshare.users;

import com.himfirst.vidshare.exceptions.InvalidValuesException;
import com.himfirst.vidshare.base.BaseServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User, UUID> implements UserService {

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepo userRepo, ModelMapper modelMapper, BCryptPasswordEncoder passwordEncoder) {
        super(userRepo, modelMapper);
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Class<User> getClassName() {
        return User.class;
    }

    @Override
    public User save(User entity) {
        // CHECK IF entity EXISTS
        User existingEntity = userRepo.findByUserNameIgnoreCase(entity.getUserName());
        if (existingEntity != null) {
            throw new InvalidValuesException("User with Name " + entity.getUserName() + " already exists");
        }
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        return super.save(entity);
    }

    @Override
    public User updateById(User entity, UUID entityId) {
        // CHECK IF Entity name EXISTS
        User entityToUpdate = super.findById(entityId);
        User existingEntity = userRepo.findByUserNameIgnoreCase(entity.getUserName());
        if (existingEntity != null && !entityToUpdate.getUserName().equalsIgnoreCase(existingEntity.getUserName())) {
            throw new InvalidValuesException("User with Name " + entity.getUserName() + " already exists");
        }
        entity.setPassword(entityToUpdate.getPassword());
        return super.updateById(entity, entityId);
    }

    @Override
    public User findByPersonId(UUID personId) {
        return userRepo.findByPersonId(personId);
    }
}
