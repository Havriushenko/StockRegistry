package smida.test_task.havriushenko.stockRegestry.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import smida.test_task.havriushenko.stockRegestry.models.StockModel;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<StockModel, Long> {

    List<StockModel> findByStatus(String status);

    Optional<StockModel> findByStatusAndUSREOU(String status, long USREOU);

    List<StockModel> findAllByStatus(String status);

    Optional<StockModel> findByPkAndStatus(Long pk, String status);

    List<StockModel> findByReleaseDateOrderByNominalValueAsc(OffsetDateTime releaseDate);

    List<StockModel> findByNominalValueBetweenOrderByNominalValueAsc(Double lessThan, Double greaterThan);
}
