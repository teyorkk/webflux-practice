package dev.moises.marketstream.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiDownException extends RuntimeException {
    private int errorId;
    public ApiDownException(int errorId,String message) {
        super(message);
        this.errorId =errorId;
    }
}
