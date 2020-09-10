package smida.test_task.havriushenko.stockRegestry.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import smida.test_task.havriushenko.stockRegestry.converters.StockConverter;
import smida.test_task.havriushenko.stockRegestry.dto.StockDto;
import smida.test_task.havriushenko.stockRegestry.exceptions.StockExistException;
import smida.test_task.havriushenko.stockRegestry.exceptions.StockNotFoundException;
import smida.test_task.havriushenko.stockRegestry.models.StockModel;
import smida.test_task.havriushenko.stockRegestry.repositories.StockRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static smida.test_task.havriushenko.stockRegestry.utils.Constants.ExceptionMessages.*;
import static smida.test_task.havriushenko.stockRegestry.utils.Constants.FieldNames.PK_FIELD_NAME;
import static smida.test_task.havriushenko.stockRegestry.utils.Constants.FieldNames.USREOU_FIELD_NAME;
import static smida.test_task.havriushenko.stockRegestry.utils.Constants.StatusConstants.ACTIVE_STATUS;
import static smida.test_task.havriushenko.stockRegestry.utils.Constants.StatusConstants.DELETED_STATUS;

class StockServiceTest {

    private static final String HELLO_WORLD_COMMENT = "Hello World!";
    private static final double NOMINAL_VALUE_LESS_THAN = 50d;
    private static final double NOMINAL_VALUE_GREATER_THAN = 1000d;
    private static final String WRONG_STATUS = "status";

    @Mock
    private StockConverter stockConverter;
    @Mock
    private StockRepository stockRepository;
    @Mock
    private HistoryService historyService;

    private StockModel stockModel;
    private StockModel stockModel2;
    private StockModel stockModel3;
    private StockDto stockDto;
    private StockDto stockDto2;
    private StockDto stockDto3;
    private List<StockModel> listModels;
    private List<StockModel> listModelsWithStatusDeleted;
    private List<StockDto> listDtos;

    private StockService tested;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        initStockModels();
        initStockDtos();
        initListStockModels();
        initListStockDtos();

        tested = new StockService(stockConverter, stockRepository, historyService);
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

        stockModel2 = new StockModel(stockModel);
        stockModel2.setUSREOU(555444336);
        stockModel2.setPk(2);

        stockModel3 = new StockModel(stockModel);
        stockModel3.setPk(3);
        stockModel3.setUSREOU(44333222);
        stockModel3.setStatus(DELETED_STATUS);
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

        stockDto2 = new StockDto(stockDto);
        stockDto2.setPk(2);

        stockDto3 = new StockDto(stockDto);
        stockDto3.setPk(3);
        stockDto3.setStatus(DELETED_STATUS);
    }

    private void initListStockModels() {
        listModels = new ArrayList<>();
        listModels.add(stockModel);
        listModels.add(stockModel2);

        listModelsWithStatusDeleted = new ArrayList();
        listModelsWithStatusDeleted.add(stockModel3);
    }

    private void initListStockDtos() {
        listDtos = new ArrayList<>();
        listDtos.add(stockDto);
        listDtos.add(stockDto2);
    }

    @Test
    public void throwExceptionWhenStockAlreadyExist() {
        when(stockRepository.findByStatusAndUSREOU(ACTIVE_STATUS, stockDto.getUSREOU())).thenReturn(Optional.ofNullable(stockModel));

        try {
            tested.registryStock(stockDto);
        } catch (StockExistException ex) {
            String result = ex.getMessage();

            assertEquals(result, STOCK_WITH + stockDto.getUSREOU() + USREOU_WAS_EXIST);
        }
    }

    @Test
    public void registryStock() {
        when(stockRepository.findByStatusAndUSREOU(ACTIVE_STATUS, stockDto.getUSREOU())).thenReturn(Optional.empty());
        when(stockConverter.convertDtoToModel(stockDto)).thenReturn(stockModel);

        tested.registryStock(stockDto);

        verify(stockRepository).save(any());
    }

    @Test
    public void throwExceptionWhenEditStock() {
        when(stockRepository.findByStatusAndUSREOU(ACTIVE_STATUS, stockDto.getUSREOU())).thenReturn(Optional.empty());

        try {
            tested.editStock(stockDto);
        } catch (StockNotFoundException ex) {
            String result = ex.getMessage();

            assertEquals(result, STOCK_WAS_NOT_FOUND_WITH + stockDto.getPk() + SPACE + PK_FIELD_NAME + "!");
        }
    }

    @Test
    public void editStock() {
        when(stockRepository.findByStatusAndUSREOU(ACTIVE_STATUS, stockDto.getUSREOU())).thenReturn(Optional.ofNullable(stockModel));
        when(stockConverter.convertDtoToModel(stockDto)).thenReturn(stockModel);
        when(historyService.comparisonStock(stockModel, stockModel)).thenReturn(Long.valueOf(1));

        tested.editStock(stockDto);
        verify(stockRepository).save(any());
    }

    @Test
    public void notEditStockWhenHistoryDoNotSaveAnyThing() {
        when(stockRepository.findByStatusAndUSREOU(ACTIVE_STATUS, stockDto.getUSREOU())).thenReturn(Optional.ofNullable(stockModel));
        when(stockConverter.convertDtoToModel(stockDto)).thenReturn(stockModel);
        when(historyService.comparisonStock(stockModel, stockModel)).thenReturn(Long.valueOf(0));

        tested.editStock(stockDto);
        verify(stockRepository, times(0)).save(any());
    }

    @Test
    public void getAllStocks() {
        when(stockRepository.findAllByStatus(ACTIVE_STATUS)).thenReturn(listModels);
        when(stockConverter.convertModelToDto(stockModel)).thenReturn(stockDto);
        when(stockConverter.convertModelToDto(stockModel2)).thenReturn(stockDto2);

        List<StockDto> result = tested.getAllStocks();

        assertEquals(result, listDtos);
        assertEquals(result.get(0).getPk(), stockDto.getPk());
    }

    @Test
    public void getStockByPk() {
        when(stockRepository.findByPkAndStatus(stockModel2.getPk(), ACTIVE_STATUS)).thenReturn(Optional.ofNullable(stockModel2));
        when(stockConverter.convertModelToDto(stockModel2)).thenReturn(stockDto2);

        StockDto result = tested.getStockByPk(stockModel2.getPk());

        assertEquals(result, stockDto2);
        assertEquals(result.getUSREOU(), stockDto2.getUSREOU());
    }

    @Test
    public void throwExceptionWhenStockByPkNotFound() {
        when(stockRepository.findByPkAndStatus(stockModel2.getPk(), ACTIVE_STATUS)).thenReturn(Optional.empty());

        try {
            tested.getStockByPk(stockModel2.getPk());
        } catch (StockNotFoundException ex) {
            String result = ex.getMessage();

            assertEquals(result, STOCK_WAS_NOT_FOUND_WITH + stockModel2.getPk() + SPACE + PK_FIELD_NAME + "!");
        }
    }

    @Test
    public void getStockByUSREOU() {
        when(stockRepository.findByStatusAndUSREOU(ACTIVE_STATUS, stockModel2.getUSREOU())).thenReturn(Optional.ofNullable(stockModel2));
        when(stockConverter.convertModelToDto(stockModel2)).thenReturn(stockDto2);

        StockDto result = tested.getStockByUSREOU(stockModel2.getUSREOU());

        assertEquals(result, stockDto2);
        assertEquals(result.getUSREOU(), stockDto2.getUSREOU());
    }

    @Test
    public void throwExceptionWhenStockByUSREOUNotFound() {
        when(stockRepository.findByStatusAndUSREOU(ACTIVE_STATUS, stockModel2.getUSREOU())).thenReturn(Optional.empty());

        try {
            tested.getStockByUSREOU(stockModel2.getUSREOU());
        } catch (StockNotFoundException ex) {
            String result = ex.getMessage();

            assertEquals(result, STOCK_WAS_NOT_FOUND_WITH + stockModel2.getUSREOU() + SPACE + USREOU_FIELD_NAME + "!");
        }
    }

    @Test
    public void deleteStockByPk() {
        when(stockRepository.findByPkAndStatus(stockModel2.getPk(), ACTIVE_STATUS)).thenReturn(Optional.ofNullable(stockModel2));

        tested.deleteByPk(stockModel2.getPk());

        verify(stockRepository).save(any());
    }

    @Test
    public void getStocksByReleaseDate() {
        when(stockRepository.findByReleaseDateOrderByNominalValueAsc(stockModel.getReleaseDate())).thenReturn(listModels);
        when(stockConverter.convertModelToDto(stockModel)).thenReturn(stockDto);
        when(stockConverter.convertModelToDto(stockModel2)).thenReturn(stockDto2);

        List<StockDto> result = tested.getStocksByReleaseDate(stockModel.getReleaseDate());

        assertEquals(result, listDtos);
        assertEquals(result.get(0).getPk(), stockDto.getPk());
    }

    @Test
    public void getStocksByNominalValueLessAndGreaterThan() {
        when(stockRepository.findByNominalValueBetweenOrderByNominalValueAsc(NOMINAL_VALUE_LESS_THAN, NOMINAL_VALUE_GREATER_THAN)).thenReturn(listModels);
        when(stockConverter.convertModelToDto(stockModel)).thenReturn(stockDto);
        when(stockConverter.convertModelToDto(stockModel2)).thenReturn(stockDto2);

        List<StockDto> result = tested.getStocksByNominalValueBetweenValues(NOMINAL_VALUE_LESS_THAN, NOMINAL_VALUE_GREATER_THAN);

        assertEquals(result, listDtos);
        assertEquals(result.get(1).getPk(), stockDto2.getPk());
        assertTrue(result.get(1).getNominalValue() == stockDto2.getNominalValue());
    }

    @Test
    public void getStocksByStatusActive() {
        when(stockRepository.findByStatus(ACTIVE_STATUS)).thenReturn(listModels);
        when(stockConverter.convertModelToDto(stockModel)).thenReturn(stockDto);
        when(stockConverter.convertModelToDto(stockModel2)).thenReturn(stockDto2);

        List<StockDto> result = tested.getStocksByStatus(ACTIVE_STATUS);

        assertEquals(result, listDtos);
        assertEquals(result.get(1).getPk(), stockDto2.getPk());
        assertEquals(result.get(1).getStatus(), stockDto2.getStatus());
    }

    @Test
    public void getStocksByStatusDeleted() {
        when(stockRepository.findByStatus(DELETED_STATUS)).thenReturn(listModelsWithStatusDeleted);
        when(stockConverter.convertModelToDto(stockModel3)).thenReturn(stockDto3);

        List<StockDto> result = tested.getStocksByStatus(DELETED_STATUS);

        assertEquals(result.get(0).getPk(), stockDto3.getPk());
        assertEquals(result.get(0).getStatus(), stockDto3.getStatus());
    }

    @Test
    public void throwExceptionWhenWrongStatus() {
        try {
            tested.getStocksByStatus(WRONG_STATUS);
        } catch (IllegalArgumentException ex) {
            String result = ex.getMessage();

            assertEquals(result, WRITE_WRONG_STATUS + WRONG_STATUS + STATUS_COULD_BE_ACTIVE_OR_DELETED);
        }
    }
}