package com.sociablesphere.postsociablesphere.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorResponse {
    private int code;
    private String message;
}
