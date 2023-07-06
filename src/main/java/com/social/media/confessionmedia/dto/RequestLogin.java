package com.social.media.confessionmedia.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class RequestLogin {

    private String userName;
    private String password;

}
