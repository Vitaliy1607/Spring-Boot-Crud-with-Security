package user_base.domain.exception;

import java.time.LocalDateTime;

public class ExceptionResponse {

    private LocalDateTime time;
    private String message;
    private String details;

    public ExceptionResponse(String message, String details) {
        this.time = LocalDateTime.now();
        this.message = message;
        this.details = details;
    }
}
