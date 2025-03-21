package com.puc.easybookautenticateuser.format;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    private String username;
    private String password;

    @Override
    public String toString() {
        return "UserDto{" +
                "user='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
