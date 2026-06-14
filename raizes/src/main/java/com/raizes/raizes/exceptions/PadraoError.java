package com.raizes.raizes.exceptions;

import java.time.LocalDateTime;

public class PadraoError {
    
    private String error;
    private String message;
    private LocalDateTime timestamp;
    private String path;

    public PadraoError(String error, String message, String path) {
        this.error = error;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.path = path;
    }

  
    public String getError() { return error; }
    public String getMessage() { return message; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getPath() { return path; }
}