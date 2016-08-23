package com.myretail.pricedetails.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by keerthiprasad on 8/18/16.
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ResourceAlreadyExists extends RuntimeException {
    /**
     * Instantiates a new Resource already exists.
     *
     * @param message the message
     */
    public ResourceAlreadyExists(String message) {

        super(message);
    }
}
