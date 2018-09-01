package com.demoapp.media.component.http;

/**
 * Exception represents the HTTP status (4xx and 5xx statuses).
 *
 * @author Anton Pelykh
 */
public class HttpException extends RuntimeException {

    private int statusCode;

    /**
     * @param statusCode the HTTP status code
     */
    public HttpException(int statusCode) {
        super();

        this.statusCode = statusCode;
    }

    /**
     * @param statusCode the HTTP status code
     * @param message the detailed message
     */
    public HttpException(int statusCode, String message) {
        super(message);

        this.statusCode = statusCode;
    }

    /**
     * @param statusCode the HTTP status code
     * @param message the detailed message
     * @param cause the cause
     */
    public HttpException(int statusCode, String message, Throwable cause) {
        super(message, cause);

        this.statusCode = statusCode;
    }

    /** Gets the HTTP status code.
     *
     *  @return HTTP status code
     **/
    public int getStatusCode() {
        return statusCode;
    }
}
