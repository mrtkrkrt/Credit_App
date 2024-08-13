package com.mrtkrkrt.creditapp.auth.dto.command;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenerateTokenCommand implements Serializable {

    private String tckn;
}
