package com.mrtkrkrt.creditapp.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_NOT_FOUND("User not found"),
    USER_ALREADY_EXISTS("User already exists"),
    USER_NOT_ACTIVE("User is not active"),
    USER_NOT_AUTHORIZED("User is not authorized"),
    USER_NOT_AUTHENTICATED("User is not authenticated"),
    USER_NOT_VERIFIED("User is not verified");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

}
