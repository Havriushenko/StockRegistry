package smida.test_task.havriushenko.stockRegestry.controllers;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import smida.test_task.havriushenko.stockRegestry.dto.StockDto;
import smida.test_task.havriushenko.stockRegestry.services.StockService;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/stock")
@NoArgsConstructor
@AllArgsConstructor
public class StockController {

    @Autowired
    private StockService stockService;

    @PostMapping
    public void registryStock(@RequestBody StockDto stock) {
        stockService.registryStock(stock);
    }

    @PutMapping
    public void editStock(@RequestBody StockDto stockDto) {
        stockService.editStock(stockDto);
    }

    @DeleteMapping("/{pk}")
    public void delete(@PathVariable Long pk) {
        stockService.deleteByPk(pk);
    }

    @GetMapping
    public List<StockDto> getStocks() {
        return stockService.getAllStocks();
    }

    @GetMapping("/by_pk")
    public StockDto getStockByPk(@RequestParam Long pk) {
        return stockService.getStockByPk(pk);
    }

    @GetMapping("/by_usreou")
    public StockDto getStockByUSREOU(@RequestParam Long USREOU) {
        return stockService.getStockByUSREOU(USREOU);
    }

    @GetMapping("/by_release_date")
    public List<StockDto> getStocksByReleaseDate(@RequestParam OffsetDateTime releaseDate) {
        return stockService.getStocksByReleaseDate(releaseDate);
    }

    @GetMapping("/{lessThan}/{greaterThan}")
    public List<StockDto> getStocksByNominalValueBetweenValues(@PathVariable Double lessThan,
                                                               @PathVariable Double greaterThan) {
        return stockService.getStocksByNominalValueBetweenValues(lessThan, greaterThan);
    }

    @GetMapping("by_status")
    public List<StockDto> getByStatus(@RequestParam String status) {
        return stockService.getStocksByStatus(status);
    }
}
