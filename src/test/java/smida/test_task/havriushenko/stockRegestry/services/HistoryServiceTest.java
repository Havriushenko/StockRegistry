package smida.test_task.havriushenko.stockRegestry.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import smida.test_task.havriushenko.stockRegestry.models.HistoryModel;
import smida.test_task.havriushenko.stockRegestry.models.StockModel;
import smida.test_task.havriushenko.stockRegestry.repositories.HistoryRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static smida.test_task.havriushenko.stockRegestry.utils.Constants.StatusConstants.ACTIVE_STATUS;
import static smida.test_task.havriushenko.stockRegestry.utils.Constants.StatusConstants.DELETED_STATUS;

class HistoryServiceTest {

    private static final String HELLO_WORLD_COMMENT = "Hello World!";
    private static final String NEW_WORLD = "New World!";

    @Mock
    private HistoryRepository historyRepository;

    private HistoryModel historyModel;
    private StockModel newStock;
    private StockModel oldStock;

    private HistoryService tested;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        initStockModel();

        tested = new HistoryService(historyRepository);
    }

    private void initStockModel() {
        newStock = new StockModel();
        newStock.setPk(1);
        newStock.setUSREOU(204565);
        newStock.setQuantity(5);
        newStock.setNominalValue(100.55);
        newStock.setComment(NEW_WORLD);
        newStock.setTotalNominalValue(502.75);
        newStock.setStatus(ACTIVE_STATUS);

        oldStock = new StockModel(newStock);
        oldStock.setUSREOU(555444336);
        oldStock.setPk(2);
        oldStock.setComment(HELLO_WORLD_COMMENT);
    }

    @Test
    public void saveUpdateFiledInHistory() {
        Long result = tested.comparisonStock(newStock, oldStock);

        assertEquals(result, 1);
        verify(historyRepository).save(any());
    }

    @Test
    public void doNotSaveAnyThingInHistory(){
        Long result = tested.comparisonStock(newStock, newStock);

        assertEquals(result, 0);
        verify(historyRepository,times(0)).save(any());

    }
}