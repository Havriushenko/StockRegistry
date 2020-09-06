package smida.test_task.havriushenko.stockRegestry.models;

import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Data
@Table(name = "history")
public class HistoryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long pk;
    @Column(name = "usreou")
    private long USREOU;
    @Column(name = "field_name", length = 500)
    private String fieldName;
    @Column(name = "last_change", length = 2000)
    private String lastChange;
    @Column(name = "new_change", length = 2000)
    private String newChange;
    @Column(name = "date_of_modified")
    private OffsetDateTime dateOfModified;
}
