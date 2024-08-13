package com.mrtkrkrt.creditapp.auth.dto.command;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenerateJwtTokenCommand {

    private String tokenId;
}
