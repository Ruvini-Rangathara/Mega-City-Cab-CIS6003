package com.project.megacitycab.util.exception;

public class MegaCityCabException extends RuntimeException {
    private final int errorCode;

    public MegaCityCabException(MegaCityCabExceptionType exceptionType) {
        super(exceptionType.getMessage());
        this.errorCode = exceptionType.getCode();
    }

    public int getErrorCode() {
        return errorCode;
    }
}
