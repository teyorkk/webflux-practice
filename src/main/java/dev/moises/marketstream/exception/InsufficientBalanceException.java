package dev.moises.marketstream.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsufficientBalanceException extends RuntimeException {
    private int errorId;
    public InsufficientBalanceException(int errorId,String message) {

        super(message);
        this.errorId = errorId;
    }
}
