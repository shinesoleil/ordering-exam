package com.thoughtworks.ketsu.web.exception;

import java.util.ArrayList;
import java.util.List;

public class InvalidParameterException extends RuntimeException {
    private List<InvalidExceptionMessage> invalidExceptionMessageList;

    public InvalidParameterException(String message) {
        super(message);
    }

    public InvalidParameterException() {
        super();
    }

    public InvalidParameterException(Exception e) {
        super(e);
    }

    public InvalidParameterException(List<String> invalidParamList) {
        invalidExceptionMessageList = new ArrayList<>();

        for (String invalidParam: invalidParamList) {
            invalidExceptionMessageList.add(new InvalidExceptionMessage(invalidParam));
        }
    }

    public List<InvalidExceptionMessage> getInvalidExceptionMessageList() {
        return invalidExceptionMessageList;
    }
}
