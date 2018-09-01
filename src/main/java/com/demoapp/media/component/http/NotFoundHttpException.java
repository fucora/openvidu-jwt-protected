package com.demoapp.media.component.http;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Anton Pelykh
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Not Found")
public class NotFoundHttpException extends HttpException {

    public NotFoundHttpException() {
        super(404, "Not found.");
    }

    public NotFoundHttpException(String message) {
        super(404, message);
    }

    public NotFoundHttpException(String message, Throwable cause) {
        super(404, message, cause);
    }
}
