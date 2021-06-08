package smida.test_task.havriushenko.stockRegestry.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockDto {

    private long pk;
    private String comment;
    private long USREOU;            //Unified State Register of Enterprises and Organizations of Ukraine
    private double quantity;
    private double totalNominalValue;
    private double nominalValue;
    private OffsetDateTime releaseDate;
    private String status;

    public StockDto(StockDto stock) {
        this.pk = stock.getPk();
        this.comment = stock.getComment();
        this.USREOU = stock.getUSREOU();
        this.quantity = stock.getQuantity();
        this.totalNominalValue = stock.getTotalNominalValue();
        this.nominalValue = stock.getNominalValue();
        this.nominalValue = stock.getNominalValue();
        this.status = stock.getStatus();
    }
}
