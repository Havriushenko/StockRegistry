package smida.test_task.havriushenko.stockRegestry.converters.impl;

import smida.test_task.havriushenko.stockRegestry.converters.StockConverter;
import smida.test_task.havriushenko.stockRegestry.dto.StockDto;
import smida.test_task.havriushenko.stockRegestry.models.StockModel;

public class StockConverterImpl implements StockConverter {

    @Override
    public StockDto convertModelToDto(StockModel model) {
        StockDto stock = new StockDto();
        stock.setPk(model.getPk());
        stock.setComment(model.getComment());
        stock.setUSREOU(model.getUSREOU());
        stock.setQuantity(model.getQuantity());
        stock.setTotalNominalValue(model.getTotalNominalValue());
        stock.setNominalValue(model.getNominalValue());
        stock.setReleaseDate(model.getReleaseDate());
        stock.setStatus(model.getStatus());
        return stock;
    }

    @Override
    public StockModel convertDtoToModel(StockDto stock) {
        StockModel model = new StockModel();
        model.setPk(stock.getPk());
        model.setComment(stock.getComment());
        model.setUSREOU(stock.getUSREOU());
        model.setQuantity(stock.getQuantity());
        model.setTotalNominalValue(stock.getTotalNominalValue());
        model.setNominalValue(stock.getNominalValue());
        model.setReleaseDate(stock.getReleaseDate());
        model.setStatus(stock.getStatus());
        return model;
    }
}
