package com.gametown.exception;

public class GameTownException extends RuntimeException {

    public final String code;
    public final String message;

    public GameTownException(ErrorCode errorCode) {
        this.code = errorCode.name();
        this.message = errorCode.message;
    }

    public GameTownException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
