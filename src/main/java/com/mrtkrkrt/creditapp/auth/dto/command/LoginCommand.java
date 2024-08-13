package com.mrtkrkrt.creditapp.auth.dto.command;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginCommand {
    @NotBlank
    private String tckn;
    @NotBlank
    private String password;
}
