package com.amrit.service;

import com.amrit.dto.UserDto;
import org.springframework.stereotype.Service;

public interface UserService {
    Boolean register(UserDto userDto);
}
