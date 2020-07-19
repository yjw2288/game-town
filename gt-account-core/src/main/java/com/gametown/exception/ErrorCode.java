package com.gametown.exception;

public enum ErrorCode {
    ACCOUNT_ALREADY_EXISTS("존재 하는 email 입니다."),
    ACCOUNT_NOT_FOUND("로그인 정보가 잘못되었습니다"),
    LOGIN_REQUIRED("로그인 해주세요."),
    LOGIN_NOT_FOUND_ACCOUNT("로그인한 사용자가 존재하지 않습니다");

    public final String message;

    ErrorCode(String message) {
        this.message = message;
    }

}
