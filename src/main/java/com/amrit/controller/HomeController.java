package com.amrit.controller;

import com.amrit.service.HomeService;
import com.amrit.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {
    @Autowired
    private HomeService homeService;

    @GetMapping("/verify")
    public ResponseEntity<?> verifyUserAccount(@RequestParam Integer uid, @RequestParam String code){
        Boolean verifyAccount = homeService.verifyAccount(uid, code);
        if (verifyAccount){
            return CommonUtil.createBuildResponseMessage("Account verified successfully", HttpStatus.OK);
        }
        return CommonUtil.createErrorResponseMessage("Verification Failed",HttpStatus.BAD_REQUEST);
    }

}
