package com.project.megacitycab.util.exception;

public class MegaCityCabException extends RuntimeException {
    private final MegaCityCabExceptionType exceptionType;

    public MegaCityCabException(MegaCityCabExceptionType exceptionType) {
        super(exceptionType.name()); // Use the enum name as the exception message
        this.exceptionType = exceptionType;
    }

    public String getExceptionType() {
        return String.valueOf(exceptionType);
    }
}
