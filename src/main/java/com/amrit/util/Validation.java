package com.amrit.util;

import com.amrit.dto.CategoryDto;
import com.amrit.exception.ValidationException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class Validation {

    public void categoryValidation(CategoryDto categoryDto){

        Map<String,Object> error = new LinkedHashMap<>();
        if (ObjectUtils.isEmpty(categoryDto)){
            throw new IllegalArgumentException("category object shouldn't be null or empty");
        }else{

            //validation name field
            if(ObjectUtils.isEmpty(categoryDto.getName())){
                error.put("name","name field is empty or null");
            }else {
                if (categoryDto.getName().length()<10){
                    error.put("name","name length min 10");
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
                if (categoryDto.getName().length()<10){
                    error.put("name","name length min 10");
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
}
