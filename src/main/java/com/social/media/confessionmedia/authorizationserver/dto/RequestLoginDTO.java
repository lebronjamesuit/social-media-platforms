package com.social.media.confessionmedia.authorizationserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class RequestLoginDTO {

    private String userName;
    private String passWord;

}
