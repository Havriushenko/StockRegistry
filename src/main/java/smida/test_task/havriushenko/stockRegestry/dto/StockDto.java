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

    public StockDto(StockDto model) {
        this.pk = model.getPk();
        this.comment = model.getComment();
        this.USREOU = model.getUSREOU();
        this.quantity = model.getQuantity();
        this.totalNominalValue = model.getTotalNominalValue();
        this.nominalValue = model.getNominalValue();
        this.nominalValue = model.getNominalValue();
        this.status = model.getStatus();
    }

}
