package smida.test_task.havriushenko.stockRegestry.exceptions;

public class StockNotFoundException extends NullPointerException {

    public StockNotFoundException() {
    }

    public StockNotFoundException(String s) {
        super(s);
    }
}
