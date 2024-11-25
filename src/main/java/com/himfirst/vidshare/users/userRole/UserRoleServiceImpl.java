package com.himfirst.vidshare.users.userRole;

import com.himfirst.vidshare.exceptions.InvalidValuesException;
import com.himfirst.vidshare.base.BaseServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole, UUID> implements UserRoleService {

    private final UserRoleRepo userRoleRepo;

    public UserRoleServiceImpl(UserRoleRepo userRoleRepo, ModelMapper modelMapper ) {
        super(userRoleRepo, modelMapper);
        this.userRoleRepo = userRoleRepo;
    }

    @Override
    public Class<UserRole> getClassName() {
        return UserRole.class;
    }

    @Override
    public UserRole save(UserRole entity) {
        // CHECK IF entity EXISTS
        UserRole existingEntity = userRoleRepo.findByRoleAndUserId(entity.getRole(), entity.getUser().getId());
        if (existingEntity != null) {
            throw new InvalidValuesException("User already has Role " + entity.getRole() );
        }
        return super.save(entity);
    }

}
