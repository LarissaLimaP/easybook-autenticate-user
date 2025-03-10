package com.puc.easybookautenticateuser.format;

import com.puc.easybookautenticateuser.model.Profile;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    private String username;
    private String password;
    private Profile profile;

    @Override
    public String toString() {
        return "UserDto{" +
                "user='" + username + '\'' +
                ", password='" + password + '\'' +
                ", profile=" + profile +
                '}';
    }
}
