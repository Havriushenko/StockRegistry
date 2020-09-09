package smida.test_task.havriushenko.stockRegestry.dto;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class HistoryDto {

    private long pk;
    private long USREOU;
    private String fieldName;
    private String lastChange;
    private String newChange;
    private OffsetDateTime dateOfModified;
}
