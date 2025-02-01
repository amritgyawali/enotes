package com.amrit.service.impl;

import com.amrit.entity.AccountStatus;
import com.amrit.entity.User;
import com.amrit.exception.ExistDataException;
import com.amrit.repository.UserRepository;
import com.amrit.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public Boolean verifyAccount(Integer userId, String verificationCode) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));

        if (user.getStatus().getVerificationCode()==null){
            throw new ExistDataException("account already verified");
        }

        if (user.getStatus().getVerificationCode().equals(verificationCode)){
            AccountStatus status=user.getStatus();
                    status.setIsActive(true);
                    status.setVerificationCode(null);
                    userRepo.save(user);
                    return true;
        }
        return false;
    }
}
