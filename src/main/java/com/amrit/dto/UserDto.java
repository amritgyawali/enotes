package com.amrit.dto;

import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobno;
    private List<RoleDto> roles;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    @Setter
    public static class RoleDto {
        private Integer id;
        private String name;
    }
}
