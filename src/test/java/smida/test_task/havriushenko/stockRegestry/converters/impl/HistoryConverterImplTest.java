package smida.test_task.havriushenko.stockRegestry.converters.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smida.test_task.havriushenko.stockRegestry.converters.HistoryConverter;
import smida.test_task.havriushenko.stockRegestry.dto.HistoryDto;
import smida.test_task.havriushenko.stockRegestry.models.HistoryModel;

import static org.junit.Assert.assertEquals;
import static smida.test_task.havriushenko.stockRegestry.utils.Constants.FieldNames.COMMENT_FIELD_NAME;

class HistoryConverterImplTest {

    private static final String HELLO_WORLD_COMMENT = "Hello World!";
    private static final String NEW_WORLD = "New World!";

    private HistoryModel historyModel;
    private HistoryDto historyDto;

    private HistoryConverter tested;

    @BeforeEach
    void setUp() {
        initHistoryModels();
        initHistoryDtos();

        tested = new HistoryConverterImpl();
    }

    private void initHistoryModels() {
        historyModel = new HistoryModel();
        historyModel.setPk(3);
        historyModel.setUSREOU(204565);
        historyModel.setFieldName(COMMENT_FIELD_NAME);
        historyModel.setLastChange(HELLO_WORLD_COMMENT);
        historyModel.setNewChange(NEW_WORLD);
    }

    private void initHistoryDtos() {
        historyDto = new HistoryDto();
        historyDto.setPk(3);
        historyDto.setUSREOU(204565);
        historyDto.setFieldName(COMMENT_FIELD_NAME);
        historyDto.setLastChange(HELLO_WORLD_COMMENT);
        historyDto.setNewChange(NEW_WORLD);
    }

    @Test
    public void convertModelToDto() {
        HistoryDto result = tested.convertModelToDto(historyModel);

        assertEquals(result, historyDto);
        assertEquals(result.getPk(), historyDto.getPk());
    }

    @Test
    public void convertDtoToModel() {
        HistoryModel result = tested.convertDtoToModel(historyDto);

        assertEquals(result, historyModel);
        assertEquals(result.getUSREOU(), historyModel.getUSREOU());
    }
}