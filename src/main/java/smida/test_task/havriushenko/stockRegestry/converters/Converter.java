package smida.test_task.havriushenko.stockRegestry.converters;

import smida.test_task.havriushenko.stockRegestry.dto.StockDto;
import smida.test_task.havriushenko.stockRegestry.models.StockModel;

public interface Converter<D, M> {

    StockDto convertModelToDto(M m);

    StockModel convertDtoToModel(D d);
}
