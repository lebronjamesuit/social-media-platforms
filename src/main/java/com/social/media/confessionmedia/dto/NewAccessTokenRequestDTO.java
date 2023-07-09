package com.social.media.confessionmedia.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewAccessTokenRequestDTO {

    @NotBlank
    private String refreshToken;
    private String userName;
}

