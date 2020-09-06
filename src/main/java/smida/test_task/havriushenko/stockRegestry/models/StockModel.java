package smida.test_task.havriushenko.stockRegestry.models;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Stock")
public class StockModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long pk;
    @Column
    private String comment;
    @Column
    private long USREOU;            //Unified State Register of Enterprises and Organizations of Ukraine
    @Column
    private double quantity;
    @Column
    private double totalNominalValue;
    @Column
    private double nominalValue;
    @Column
    private LocalDateTime releaseDate;
    @Column
    private String status;
}
