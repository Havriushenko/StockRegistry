package smida.test_task.havriushenko.stockRegestry.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class StockExistException extends RuntimeException {

    public StockExistException() {
    }

    public StockExistException(String s) {
        super(s);
    }

    public StockExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public StockExistException(Throwable cause) {
        super(cause);
    }
}
