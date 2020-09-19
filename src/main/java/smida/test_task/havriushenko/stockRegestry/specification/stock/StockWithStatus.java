package smida.test_task.havriushenko.stockRegestry.specification.stock;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import smida.test_task.havriushenko.stockRegestry.models.StockModel;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import static smida.test_task.havriushenko.stockRegestry.utils.Constants.FieldNames.STATUS_FIELD_NAME;
import static smida.test_task.havriushenko.stockRegestry.utils.Constants.StatusConstants.ACTIVE_STATUS;
import static smida.test_task.havriushenko.stockRegestry.utils.Constants.StatusConstants.DELETED_STATUS;

@RequiredArgsConstructor
public class StockWithStatus implements Specification<StockModel> {

    private final String status;

    @Override
    public Predicate toPredicate(Root<StockModel> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if(isStatus()){
            return cb.equal(root.get(STATUS_FIELD_NAME),this.status);
        }
        return cb.isTrue(cb.literal(true));
    }

    private boolean isStatus() {
        return StringUtils.isNotEmpty(this.status) && (ACTIVE_STATUS.equals(this.status) || DELETED_STATUS.equals(this.status));
    }
}
