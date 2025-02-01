package com.amrit.service.impl;

import com.amrit.dto.EmailRequest;
import com.amrit.dto.UserDto;
import com.amrit.entity.AccountStatus;
import com.amrit.entity.Role;
import com.amrit.entity.User;
import com.amrit.repository.RoleRepository;
import com.amrit.repository.UserRepository;
import com.amrit.service.UserService;
import com.amrit.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private Validation validation;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private EmailService emailService;

    @Override
    public Boolean register(UserDto userDto,String url) throws Exception {

        validation.userValidation(userDto);
        User user = mapper.map(userDto, User.class);

        setRole(userDto, user);

        AccountStatus status=AccountStatus.builder()
                .isActive(false)
                .verificationCode(UUID.randomUUID().toString())
                .build();
        user.setStatus(status);

        User saveUser = userRepo.save(user);


        if (!ObjectUtils.isEmpty(saveUser)) {
            // send email
            emailSend(saveUser,url);
            return true;
        }
        return false;
    }

    private void emailSend(User saveUser, String url) throws Exception {

        String message="Hi,<b>[[username]]</b> "
                + "<br> Your account register sucessfully.<br>"
                +"<br> Click the below link verify & Active your account <br>"
                +"<a href='[[url]]'>Click Here</a> <br><br>"
                +"Thanks,<br>Amrit.com"
                ;

        message = message.replace("[[username]]",saveUser.getFirstName());
        message = message.replace("[[url]]",url+"/api/v1/home/verify?uid="+saveUser.getId()
                +"&&code="+saveUser.getStatus().getVerificationCode());

        EmailRequest emailRequest = EmailRequest.builder()
                .to(saveUser.getEmail())
                .title("Account Creating Confirmation")
                .subject("Account Created Success")
                .message(message)
                .build();
        emailService.sendEmail(emailRequest);
    }

    private void setRole(UserDto userDto, User user) {
        List<Integer> reqRoleId = userDto.getRoles().stream().map(r -> r.getId()).toList();
        List<Role> roles = roleRepo.findAllById(reqRoleId);
        user.setRoles(roles);
    }

}