package smida.test_task.havriushenko.stockRegestry.services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smida.test_task.havriushenko.stockRegestry.models.HistoryModel;
import smida.test_task.havriushenko.stockRegestry.models.StockModel;
import smida.test_task.havriushenko.stockRegestry.repositories.HistoryRepository;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static smida.test_task.havriushenko.stockRegestry.utils.Constants.FieldNames.*;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    public Long comparisonStock(StockModel newStock, StockModel oldStock) {
        Map<String, Object> newStockMap = getStockToMap(newStock);
        Map<String, Object> oldStockMap = getStockToMap(oldStock);

        return saveUpdateFieldsInHistory(oldStock, newStockMap, oldStockMap);
    }

    private Map<String, Object> getStockToMap(StockModel stock) {
        return new HashMap<String, Object>() {{
            put(COMMENT_FIELD_NAME, stock.getComment());
            put(TOTAL_NOMINAL_VALUE_FIELD_NAME, stock.getTotalNominalValue());
            put(NOMINAL_VALUE_FIELD_NAME, stock.getNominalValue());
            put(QUANTITY_FIELD_NAME, stock.getQuantity());
        }};
    }

    private Long saveUpdateFieldsInHistory(StockModel oldStock, Map<String, Object> newStockMap, Map<String, Object> oldStockMap) {
        AtomicLong count = new AtomicLong(0);
        newStockMap.forEach((key, value) -> {
            if (!oldStockMap.get(key).equals(value)) {
                HistoryModel history = new HistoryModel();
                history.setUSREOU(oldStock.getUSREOU());
                history.setFieldName(key);
                history.setNewChange(String.valueOf(value));
                history.setLastChange(String.valueOf(oldStockMap.get(key)));
                history.setDateOfModified(OffsetDateTime.now());

                historyRepository.save(history);
                count.getAndIncrement();
            }
        });
        return count.get();
    }
}
