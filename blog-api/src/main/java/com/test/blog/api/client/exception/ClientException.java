package com.test.blog.api.client.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClientException extends RuntimeException {

    private final int status;

    private final String clientName;

    private final String errorCode;

    private final String errorMessage;

    public ClientException(int status, String clientName, String errorCode, String errorMessage) {
        super(errorMessage);
        this.status = status;
        this.clientName = clientName;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return String.format("ClientException: %s, %s, %s, %s", status, clientName, errorCode, errorMessage);
    }

}
