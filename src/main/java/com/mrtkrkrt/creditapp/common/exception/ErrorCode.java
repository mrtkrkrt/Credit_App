package com.mrtkrkrt.creditapp.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_NOT_FOUND("User not found"),
    USER_ALREADY_EXISTS("User already exists"),
    USER_NOT_ACTIVE("User is not active"),
    USER_NOT_AUTHORIZED("User is not authorized"),
    USER_NOT_AUTHENTICATED("User is not authenticated"),
    USER_NOT_VERIFIED("User is not verified"),
    LOAN_HAS_NO_UNPAID_INSTALLMENT("Loan has no unpaid installment"),
    AMOUNT_IS_NOT_ENOUGH_FOR_INSTALLMENT("Amount is not enough for installment"),;

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

}
