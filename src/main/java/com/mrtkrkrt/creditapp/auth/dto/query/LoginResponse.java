package com.mrtkrkrt.creditapp.auth.dto.query;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {

    private String token;
}
