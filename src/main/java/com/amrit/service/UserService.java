package com.amrit.service;

import com.amrit.dto.UserDto;
import org.springframework.stereotype.Service;

public interface UserService {

    public Boolean register(UserDto userDto,String url) throws Exception;

}
