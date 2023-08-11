package com.social.media.confessionmedia.authorizationserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterFormDTO {

    private String email;
    private String username;
    private String password;

}
