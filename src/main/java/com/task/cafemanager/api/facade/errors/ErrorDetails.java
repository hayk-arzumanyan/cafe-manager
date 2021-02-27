package com.task.cafemanager.api.facade.errors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorDetails {
    private HttpStatus statusCode;
    private String message;

    @JsonCreator
    public ErrorDetails(@JsonProperty("statusCode") final HttpStatus statusCode,
                        @JsonProperty("message") final String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
