package com.mrtkrkrt.creditapp.user.dto.query;

import com.mrtkrkrt.creditapp.user.model.UserElastic;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
public class RetrieveUserResponse {
    private UserElastic user;
}
