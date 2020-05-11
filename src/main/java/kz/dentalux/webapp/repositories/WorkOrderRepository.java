package kz.dentalux.webapp.repositories;

import java.util.Optional;
import kz.dentalux.webapp.models.WorkOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {
    Optional<WorkOrder> findByScheduleId(Long scheduleId);
}
