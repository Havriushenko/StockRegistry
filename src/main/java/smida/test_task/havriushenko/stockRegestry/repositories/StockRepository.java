package smida.test_task.havriushenko.stockRegestry.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import smida.test_task.havriushenko.stockRegestry.models.StockModel;

public interface StockRepository extends JpaRepository<StockModel, Long> {
}
