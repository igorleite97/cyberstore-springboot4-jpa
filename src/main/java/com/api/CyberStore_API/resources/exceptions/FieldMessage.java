package com.api.CyberStore_API.resources.exceptions;

import java.io.Serial;
import java.io.Serializable;

public class FieldMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String fieldName;

    private final String message;

    public FieldMessage(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getMessage() {
        return message;
    }
}
