package com.demoapp.media.component.http;

/**
 * @author Anton Pelykh
 */
public class InternalServerErrorHttpException extends HttpException {

    public InternalServerErrorHttpException() {
        super(500, "Internal server error.");
    }

    public InternalServerErrorHttpException(String message) {
        super(500, message);
    }

    public InternalServerErrorHttpException(String message, Throwable cause) {
        super(500, message, cause);
    }
}
