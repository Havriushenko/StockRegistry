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


    @GetMapping("/filter")
    public List<StockDto> getFilteringStocks(@RequestParam Long pk,
                                             @RequestParam Long USREOU,
                                             @RequestParam String status) {
        return stockService.getFilteringStocks(pk, USREOU, status);
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

}
