package com.andrey.translator.net;

/**
 * Created by Andrey Antonenko on 02.12.2016.
 */

public class ResponseError {
    private ServerError error;
    private String errorMessage;

    public ResponseError(int errorCode, String errorMessage) {
        this.error = ServerError.fromCode(errorCode);
        this.errorMessage = errorMessage;
    }

    public ResponseError() {
        new ResponseError(ServerError.UNKNOWN_ERROR, "");
    }

    public ResponseError(ServerError error) {
        new ResponseError(error, "");
    }

    public ResponseError(ServerError error, String errorMessage) {
        this.error = error;
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public ServerError getErrorCode() {
        return error;
    }
}
