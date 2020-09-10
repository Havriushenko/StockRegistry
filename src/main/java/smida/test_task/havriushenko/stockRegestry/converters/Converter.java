package smida.test_task.havriushenko.stockRegestry.converters;

public interface Converter<D, M> {

    D convertModelToDto(M m);

    M convertDtoToModel(D d);
}
