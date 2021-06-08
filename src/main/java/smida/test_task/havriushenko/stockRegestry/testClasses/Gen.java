package smida.test_task.havriushenko.stockRegestry.testClasses;

public class Gen <T> {

    T obj;

    public Gen(T obj) {
        this.obj = obj;
    }

    public T getObj() {
        return obj;
    }
}
