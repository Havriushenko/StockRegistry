package smida.test_task.havriushenko.stockRegestry.specification.stock;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import smida.test_task.havriushenko.stockRegestry.models.StockModel;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import static smida.test_task.havriushenko.stockRegestry.utils.Constants.FieldNames.USREOU_FIELD_NAME;

@RequiredArgsConstructor
public class StockWithUSREOU implements Specification<StockModel> {

    private final Long USREOU;

    @Override
    public Predicate toPredicate(Root<StockModel> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if (USREOU == 0) {
            return cb.isTrue(cb.literal(true));
        }
        return cb.equal(root.get(USREOU_FIELD_NAME), this.USREOU);
    }
}
