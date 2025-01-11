package com.amrit.controller;

import com.amrit.dto.UserDto;
import com.amrit.service.UserService;
import com.amrit.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<?> saveUser(@RequestBody UserDto userDto){
        Boolean userInfo = userService.register(userDto);
        if (ObjectUtils.isEmpty(userInfo)){
            return CommonUtil.createErrorResponseMessage("user is not saved", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return CommonUtil.createBuildResponseMessage("user saved successfully",HttpStatus.CREATED);
    }
}
