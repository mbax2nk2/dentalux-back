package kz.dentalux.webapp.repositories;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import kz.dentalux.webapp.models.WorkOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {
    Optional<WorkOrder> findByScheduleId(Long scheduleId);
    List<WorkOrder> findByPatient_IdAndDebtGreaterThanOrderByCreatedDateDesc(long patientId, int debt);
    List<WorkOrder> findWorkOrdersByCreatedDateBetween(OffsetDateTime from, OffsetDateTime to);
}
