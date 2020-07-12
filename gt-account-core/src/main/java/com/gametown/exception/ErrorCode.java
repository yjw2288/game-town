package com.gametown.exception;

public enum ErrorCode {
    ACCOUNT_ALREADY_EXISTS("존재 하는 userId 입니다."),
    ACCOUNT_NOT_FOUND("로그인 정보가 잘못되었습니다");

    public final String message;

    ErrorCode(String message) {
        this.message = message;
    }

}
