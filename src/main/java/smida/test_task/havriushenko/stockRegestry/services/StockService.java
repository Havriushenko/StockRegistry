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
        isStockExist(stock);
        StockModel model = stockConverter.convertDtoToModel(stock);
        model.setStatus(ACTIVE_STATUS);
        model.setReleaseDate(OffsetDateTime.now());
        model.setTotalNominalValue(stock.getNominalValue() * stock.getQuantity());
        stockRepository.save(model);
    }

    private void isStockExist(StockDto stock) {
        if (stockRepository.findByStatusAndUSREOU(ACTIVE_STATUS, stock.getUSREOU()).isPresent()) {
            throw new StockExistException(STOCK_WITH + stock.getUSREOU() + USREOU_WAS_EXIST);
        }
    }

    public void editStock(StockDto stock) {
        Optional<StockModel> optionalOldModel = stockRepository.findByStatusAndUSREOU(ACTIVE_STATUS, stock.getUSREOU());
        stockIsNotNull(stock.getPk(), PK_FIELD_NAME, optionalOldModel);
        StockModel updateStock = stockConverter.convertDtoToModel(stock);
        long count = historyService.comparisonStock(updateStock, optionalOldModel.get());
        if (count != 0) {
            stockRepository.save(updateStock);
        }
    }

    private void stockIsNotNull(Object value, String fieldName, Optional<StockModel> optionalModel) {
        if (!optionalModel.isPresent()) {
            throw new StockNotFoundException(STOCK_WAS_NOT_FOUND_WITH + value + SPACE + fieldName + "!");
        }
    }

    public List<StockDto> getAllStocks() {
        List<StockModel> models = stockRepository.findAllByStatus(ACTIVE_STATUS);

        return models.stream().map(model -> stockConverter.convertModelToDto(model)).collect(Collectors.toList());
    }

    public StockDto getStockByPk(Long pk) {
        Optional<StockModel> optionalModel = stockRepository.findByPkAndStatus(pk, ACTIVE_STATUS);
        stockIsNotNull(pk, PK_FIELD_NAME, optionalModel);

        return stockConverter.convertModelToDto(optionalModel.get());
    }

    public StockDto getStockByUSREOU(Long USREOU) {
        Optional<StockModel> optionalModel = stockRepository.findByStatusAndUSREOU(ACTIVE_STATUS, USREOU);
        stockIsNotNull(USREOU, USREOU_FIELD_NAME, optionalModel);

        return stockConverter.convertModelToDto(optionalModel.get());
    }

    public void deleteByPk(Long pk) {
        Optional<StockModel> optionalModel = stockRepository.findByPkAndStatus(pk, ACTIVE_STATUS);
        stockIsNotNull(pk, PK_FIELD_NAME, optionalModel);
        StockModel model = optionalModel.get();
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
        throw new IllegalArgumentException(WRITE_WRONG_STATUS + status + STATUS_COULD_BE_ACTIVE_OR_DELETED);
    }

    private boolean isStatus(String status) {
        return StringUtils.isNotEmpty(status) && (ACTIVE_STATUS.equals(status) || DELETED_STATUS.equals(status));
    }
}
