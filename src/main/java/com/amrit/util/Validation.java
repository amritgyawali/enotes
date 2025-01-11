package com.amrit.util;

import com.amrit.dto.CategoryDto;
import com.amrit.dto.TodoDto;
import com.amrit.dto.UserDto;
import com.amrit.entity.Role;
import com.amrit.entity.User;
import com.amrit.enums.TodoStatus;
import com.amrit.exception.ExistDataException;
import com.amrit.exception.ResourceNotFoundException;
import com.amrit.exception.ValidationException;
import com.amrit.repository.RoleRepository;
import com.amrit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class Validation {

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private Constants constants;

    public void categoryValidation(CategoryDto categoryDto){

        Map<String,Object> error = new LinkedHashMap<>();
        if (ObjectUtils.isEmpty(categoryDto)){
            throw new IllegalArgumentException("category object shouldn't be null or empty");
        }else{

            //validation name field
            if(ObjectUtils.isEmpty(categoryDto.getName())){
                error.put("name","name field is empty or null");
            }else {
                if (categoryDto.getName().length()<2){
                    error.put("name","name length min 2");
                }
                if (categoryDto.getName().length()>100){
                    error.put("name","name length max 100");
                }
            }

            //validation description field
            if(ObjectUtils.isEmpty(categoryDto.getDescription())){
                error.put("description","description field is empty or null");
            }else {
                if (categoryDto.getDescription().length()<10){
                    error.put("description","description length min 10");
                }
                if (categoryDto.getDescription().length()>100){
                    error.put("description","description length max 100");
                }
            }

            //validation name field
            if(ObjectUtils.isEmpty(categoryDto.getName())){
                error.put("name","name field is empty or null");
            }else {
                if (categoryDto.getName().length()<2){
                    error.put("name","name length min 2");
                }
                if (categoryDto.getName().length()>100){
                    error.put("name","name length max 100");
                }
            }
            //validation description
            if(ObjectUtils.isEmpty(categoryDto.getDescription())){
                error.put("description","description field is empty or null");
            }
            //validation isActive
            if(ObjectUtils.isEmpty(categoryDto.getIsActive())){
                error.put("isActive","isActive field is empty or null");
            }
        }
        if (!error.isEmpty()){
            throw new ValidationException(error);
        }
    }
    public void todoValidation(TodoDto todo) throws Exception {
        TodoDto.StatusDto reqStatus = todo.getStatus();

        Boolean statusFound= false;
        for(TodoStatus st : TodoStatus.values()){
            if (st.getId().equals(reqStatus.getId())){
                statusFound= true;
            }
        }
        if (!statusFound){
            throw new ResourceNotFoundException("valid status not found");
        }
    }

    public void userValidation(UserDto userDto) {

        if (!StringUtils.hasText(userDto.getFirstName())) {
            throw new IllegalArgumentException("first name is invalid");
        }

        if (!StringUtils.hasText(userDto.getLastName())) {
            throw new IllegalArgumentException("last name is invalid");
        }

        if (!StringUtils.hasText(userDto.getEmail()) || !userDto.getEmail().matches(Constants.EMAIL_REGEX)) {
            throw new IllegalArgumentException("email is invalid");
        }else {
            Boolean existsByEmail =userRepo.existsByEmail(userDto.getEmail());
            if (existsByEmail){
                throw new ExistDataException("email already used");
            }
        }

        if (!StringUtils.hasText(userDto.getMobno()) || !userDto.getMobno().matches(Constants.MOBILE_REGEX)) {
            throw new IllegalArgumentException("mobno is invalid");
        }

        if (CollectionUtils.isEmpty(userDto.getRoles())) {
            throw new IllegalArgumentException("role is invalid");
        } else {

            List<Integer> roleIds = roleRepo.findAll().stream().map(r -> r.getId()).toList();

            List<Integer> invalidReqRoleids = userDto.getRoles().stream().map(r -> r.getId())
                    .filter(roleId -> !roleIds.contains(roleId)).toList();

            if (!CollectionUtils.isEmpty(invalidReqRoleids)) {
                throw new IllegalArgumentException("role is invalid" + invalidReqRoleids);
            }

        }

    }

}
