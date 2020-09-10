package smida.test_task.havriushenko.stockRegestry.converters.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smida.test_task.havriushenko.stockRegestry.converters.StockConverter;
import smida.test_task.havriushenko.stockRegestry.dto.StockDto;
import smida.test_task.havriushenko.stockRegestry.models.StockModel;

import static org.junit.Assert.assertEquals;
import static smida.test_task.havriushenko.stockRegestry.utils.Constants.StatusConstants.ACTIVE_STATUS;

class StockConverterImplTest {


    private static final String HELLO_WORLD_COMMENT = "Hello World!";

    private StockModel stockModel;
    private StockDto stockDto;

    private StockConverter tested;

    @BeforeEach
    void setUp() {
        initStockModels();
        initStockDtos();

        tested = new StockConverterImpl();
    }

    private void initStockModels() {
        stockModel = new StockModel();
        stockModel.setPk(1);
        stockModel.setUSREOU(204565);
        stockModel.setQuantity(5);
        stockModel.setNominalValue(100.55);
        stockModel.setComment(HELLO_WORLD_COMMENT);
        stockModel.setTotalNominalValue(502.75);
        stockModel.setStatus(ACTIVE_STATUS);
    }

    private void initStockDtos() {
        stockDto = new StockDto();
        stockDto.setPk(1);
        stockDto.setUSREOU(204565);
        stockDto.setQuantity(5);
        stockDto.setNominalValue(100.55);
        stockDto.setComment(HELLO_WORLD_COMMENT);
        stockDto.setTotalNominalValue(502.75);
        stockDto.setStatus(ACTIVE_STATUS);
    }

    @Test
    public void convertModelToDto() {
        StockDto result = tested.convertModelToDto(stockModel);

        assertEquals(result, stockDto);
        assertEquals(result.getUSREOU(), stockDto.getUSREOU());
    }

    @Test
    public void convertDtoToModel() {
        StockModel result = tested.convertDtoToModel(stockDto);

        assertEquals(result, stockModel);
        assertEquals(result.getUSREOU(), stockModel.getUSREOU());
    }
}