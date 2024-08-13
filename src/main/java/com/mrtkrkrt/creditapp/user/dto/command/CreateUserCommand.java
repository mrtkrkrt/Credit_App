package com.mrtkrkrt.creditapp.user.dto.command;

import com.mrtkrkrt.creditapp.user.model.User;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserCommand {

    @NotBlank(message = "Tckn is required")
    private String tckn;
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Surname is required")
    private String surname;
    @NotBlank(message = "Password is required")
    private String password;

    public User toItem() {
        return User.builder()
                .name(name)
                .surname(surname)
                .password(password)
                .tckn(tckn)
                .build();
    }
}
