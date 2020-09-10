package smida.test_task.havriushenko.stockRegestry.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stock")
public class StockModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long pk;

    @Column(length = 2000)
    private String comment;
    @Column(name = "usreou")
    private long USREOU;            //Unified State Register of Enterprises and Organizations of Ukraine
    @Column
    private double quantity;
    @Column(name = "total_nominal_value")
    private double totalNominalValue;
    @Column(name = "nominal_value")
    private double nominalValue;
    @Column(name = "release_date")
    private OffsetDateTime releaseDate;
    @Column(length = 10)
    private String status;

    public StockModel(StockModel model) {
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
