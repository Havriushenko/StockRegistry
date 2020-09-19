package smida.test_task.havriushenko.stockRegestry.services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smida.test_task.havriushenko.stockRegestry.converters.StockConverter;
import smida.test_task.havriushenko.stockRegestry.dto.StockDto;
import smida.test_task.havriushenko.stockRegestry.exceptions.StockExistException;
import smida.test_task.havriushenko.stockRegestry.exceptions.StockNotFoundException;
import smida.test_task.havriushenko.stockRegestry.models.StockModel;
import smida.test_task.havriushenko.stockRegestry.repositories.StockRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static smida.test_task.havriushenko.stockRegestry.utils.Constants.ExceptionMessages.*;
import static smida.test_task.havriushenko.stockRegestry.utils.Constants.FieldNames.PK_FIELD_NAME;
import static smida.test_task.havriushenko.stockRegestry.utils.Constants.FieldNames.USREOU_FIELD_NAME;
import static smida.test_task.havriushenko.stockRegestry.utils.Constants.StatusConstants.ACTIVE_STATUS;
import static smida.test_task.havriushenko.stockRegestry.utils.Constants.StatusConstants.DELETED_STATUS;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class StockService {

    @Autowired
    private StockConverter stockConverter;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private HistoryService historyService;

    public void registryStock(StockDto stock) {
        isStockExist(stock.getUSREOU());
        StockModel model = stockConverter.convertDtoToModel(stock);
        model.setStatus(ACTIVE_STATUS);
        model.setReleaseDate(OffsetDateTime.now());
        model.setTotalNominalValue(stock.getNominalValue() * stock.getQuantity());
        stockRepository.save(model);
    }

    private void isStockExist(Long USREOU) {
        if (stockRepository.findByStatusAndUSREOU(ACTIVE_STATUS, USREOU).isPresent()) {
            throw new StockExistException(String.format(STOCK_WITH_FIELD_WAS_EXIST, USREOU, USREOU_FIELD_NAME));
        }
    }

    public void editStock(StockDto stock) {
        StockModel model = stockRepository.findByStatusAndUSREOU(ACTIVE_STATUS, stock.getUSREOU())
                .orElseThrow(() -> new StockNotFoundException(String.format(STOCK_WAS_NOT_FOUND, stock.getUSREOU(), USREOU_FIELD_NAME)));
        StockModel updateStock = stockConverter.convertDtoToModel(stock);
        long count = historyService.comparisonStock(updateStock, model);
        if (count != 0) {
            stockRepository.save(updateStock);
        }
    }

    public List<StockDto> getAllStocks() {
        List<StockModel> models = stockRepository.findAllByStatus(ACTIVE_STATUS);

        return models.stream().map(model -> stockConverter.convertModelToDto(model)).collect(Collectors.toList());
    }

    public StockDto getStockByPk(Long pk) {
        StockModel model = stockRepository.findByPkAndStatus(pk, ACTIVE_STATUS)
                .orElseThrow(() -> new StockNotFoundException(String.format(STOCK_WAS_NOT_FOUND, pk, PK_FIELD_NAME)));

        return stockConverter.convertModelToDto(model);
    }

    public StockDto getStockByUSREOU(Long USREOU) {
        StockModel model = stockRepository.findByStatusAndUSREOU(ACTIVE_STATUS, USREOU)
                .orElseThrow(() -> new StockNotFoundException(String.format(STOCK_WAS_NOT_FOUND, USREOU, USREOU_FIELD_NAME)));

        return stockConverter.convertModelToDto(model);
    }

    public void deleteByPk(Long pk) {
        StockModel model = stockRepository.findByPkAndStatus(pk, ACTIVE_STATUS)
                .orElseThrow(() -> new StockNotFoundException(String.format(STOCK_WAS_NOT_FOUND, pk, PK_FIELD_NAME)));
        model.setStatus(DELETED_STATUS);
        stockRepository.save(model);
    }

    public List<StockDto> getStocksByReleaseDate(OffsetDateTime releaseDate) {
        List<StockModel> models = stockRepository.findByReleaseDateOrderByNominalValueAsc(releaseDate);

        return models.stream().map(model -> stockConverter.convertModelToDto(model)).collect(Collectors.toList());
    }

    public List<StockDto> getStocksByNominalValueBetweenValues(Double lessThan, Double greaterThan) {
        List<StockModel> models = stockRepository.findByNominalValueBetweenOrderByNominalValueAsc(lessThan, greaterThan);

        return models.stream().map(model -> stockConverter.convertModelToDto(model)).collect(Collectors.toList());
    }

    public List<StockDto> getStocksByStatus(String status) {
        if (isStatus(status)) {
            List<StockModel> models = stockRepository.findByStatus(status);

            return models.stream().map(model -> stockConverter.convertModelToDto(model)).collect(Collectors.toList());
        }
        throw new IllegalArgumentException(String.format(WRITE_WRONG_STATUS, status));
    }

    private boolean isStatus(String status) {
        return StringUtils.isNotEmpty(status) && (ACTIVE_STATUS.equals(status) || DELETED_STATUS.equals(status));
    }
}
