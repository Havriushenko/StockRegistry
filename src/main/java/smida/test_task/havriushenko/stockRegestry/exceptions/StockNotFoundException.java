package smida.test_task.havriushenko.stockRegestry.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class StockNotFoundException extends NullPointerException {

    public StockNotFoundException() {
    }

    public StockNotFoundException(String s) {
        super(s);
    }

}
