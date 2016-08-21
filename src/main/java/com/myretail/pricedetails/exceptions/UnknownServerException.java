package com.myretail.pricedetails.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by keerthiprasad on 8/20/16.
 */
@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class UnknownServerException extends RuntimeException {
    public UnknownServerException(String message) {
        super(message);
    }
}
