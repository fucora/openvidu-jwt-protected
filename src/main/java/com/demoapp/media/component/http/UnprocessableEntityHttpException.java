package com.demoapp.media.component.http;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Anton Pelykh
 */
@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Unprocessable Entity")
public class UnprocessableEntityHttpException extends HttpException {

    private BindingResult bindingResult;

    /**
     * @param bindingResult the binding result you can get the errors from
     */
    public UnprocessableEntityHttpException(BindingResult bindingResult) {
        super(422, "Unprocessable entity.");

        this.bindingResult = bindingResult;
    }

    /**
     * @param bindingResult the binding result you can get the errors from
     * @param message the detailed message
     */
    public UnprocessableEntityHttpException(BindingResult bindingResult, String message) {
        super(422, message);

        this.bindingResult = bindingResult;
    }

    /**
     * @param bindingResult the binding result you can get the errors from
     * @param message the detailed message
     * @param cause the cause
     */
    public UnprocessableEntityHttpException(BindingResult bindingResult, String message, Throwable cause) {
        super(422, message, cause);

        this.bindingResult = bindingResult;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }
}
