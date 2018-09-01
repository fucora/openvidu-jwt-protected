package com.demoapp.media.component.web.servlet.mvc;

import com.demoapp.media.component.http.HttpException;
import com.demoapp.media.component.http.NotFoundHttpException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.demoapp.media.component.http.UnprocessableEntityHttpException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract controller with implemented HTTP errors handling.
 *
 * @author Anton Pelykh
 */
public class AbstractController {

    @ExceptionHandler({
            NotFoundHttpException.class,
            UnprocessableEntityHttpException.class,
    })
    public ResponseEntity handleHttpException(HttpException httpException) throws Exception {
        Map<String, Object> error = new HashMap<>();
        error.put("code", httpException.getStatusCode());
        error.put("message", httpException.getMessage());

        if (httpException instanceof UnprocessableEntityHttpException) {
            Map<String, Object> validationError = new HashMap<>();

            for (FieldError fieldError : ((UnprocessableEntityHttpException) httpException).getBindingResult().getFieldErrors()) {
                validationError.put(fieldError.getField(), fieldError.getDefaultMessage());
            }

            error.put("validationErrors", validationError);
        }

        return ResponseEntity.status(httpException.getStatusCode())
                .body(new ObjectMapper().writeValueAsString(error));
    }
}
