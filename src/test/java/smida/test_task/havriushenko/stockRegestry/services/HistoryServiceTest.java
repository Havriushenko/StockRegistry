package smida.test_task.havriushenko.stockRegestry.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import smida.test_task.havriushenko.stockRegestry.converters.HistoryConverter;
import smida.test_task.havriushenko.stockRegestry.dto.HistoryDto;
import smida.test_task.havriushenko.stockRegestry.models.HistoryModel;
import smida.test_task.havriushenko.stockRegestry.models.StockModel;
import smida.test_task.havriushenko.stockRegestry.repositories.HistoryRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static smida.test_task.havriushenko.stockRegestry.utils.Constants.FieldNames.COMMENT_FIELD_NAME;
import static smida.test_task.havriushenko.stockRegestry.utils.Constants.StatusConstants.ACTIVE_STATUS;

class HistoryServiceTest {

    private static final String HELLO_WORLD_COMMENT = "Hello World!";
    private static final String NEW_WORLD = "New World!";

    @Mock
    private HistoryConverter historyConverter;
    @Mock
    private HistoryRepository historyRepository;

    private HistoryModel historyModel;
    private HistoryDto historyDto;
    private StockModel newStock;
    private StockModel oldStock;
    private List<HistoryModel> listHistoryModels;
    private List<HistoryDto> listHistoryDtos;

    private HistoryService tested;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        initStockModels();
        initHistoryModels();
        initListHistoryModels();
        initHistoryDtos();
        initListHistorDtos();

        tested = new HistoryService(historyConverter, historyRepository);
    }

    private void initStockModels() {
        newStock = new StockModel();
        newStock.setPk(1);
        newStock.setUSREOU(204565);
        newStock.setQuantity(5);
        newStock.setNominalValue(100.55);
        newStock.setComment(NEW_WORLD);
        newStock.setTotalNominalValue(502.75);
        newStock.setStatus(ACTIVE_STATUS);

        oldStock = new StockModel(newStock);
        oldStock.setUSREOU(204565);
        oldStock.setPk(2);
        oldStock.setComment(HELLO_WORLD_COMMENT);
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

    private void initListHistoryModels() {
        listHistoryModels = new ArrayList<>();
        listHistoryModels.add(historyModel);
    }

    private void initListHistorDtos() {
        listHistoryDtos = new ArrayList<>();
        listHistoryDtos.add(historyDto);
    }

    @Test
    public void saveUpdateFiledInHistory() {
        Long result = tested.comparisonStock(newStock, oldStock);

        assertEquals(result, 1);
        verify(historyRepository).save(any());
    }

    @Test
    public void doNotSaveAnyThingInHistory() {
        Long result = tested.comparisonStock(newStock, newStock);

        assertEquals(result, 0);
        verify(historyRepository, times(0)).save(any());
    }

    @Test
    public void getAllHistory() {
        when(historyRepository.findAll()).thenReturn(listHistoryModels);
        when(historyConverter.convertModelToDto(historyModel)).thenReturn(historyDto);

        List<HistoryDto> result = tested.getHistory();

        assertEquals(result, listHistoryDtos);
        assertEquals(result.get(0).getUSREOU(), historyDto.getUSREOU());
    }
}