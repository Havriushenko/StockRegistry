package smida.test_task.havriushenko.stockRegestry.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StockDto {

    private long pk;
    private String comment;
    private long USREOU;            //Unified State Register of Enterprises and Organizations of Ukraine
    private double quantity;
    private double totalNominalValue;
    private double nominalValue;
    private LocalDateTime releaseDate;
    private String status;
}
