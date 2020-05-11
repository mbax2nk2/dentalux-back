package kz.dentalux.webapp.services;

import java.util.List;
import java.util.Optional;
import kz.dentalux.webapp.models.MedicalHistory;
import kz.dentalux.webapp.models.Schedule.Status;
import kz.dentalux.webapp.models.WorkOrder;
import kz.dentalux.webapp.repositories.WorkOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WorkOrderService extends AbstractService {

    private final WorkOrderRepository repository;
    private ScheduleService scheduleService;
    private MedicalHistoryService medicalHistoryService;
    private PatientService patientService;

    public WorkOrderService(WorkOrderRepository repository,
        UserService userService, ScheduleService scheduleService,
        MedicalHistoryService medicalHistoryService,
        PatientService patientService) {
        super(userService);
        this.repository = repository;
        this.scheduleService = scheduleService;
        this.medicalHistoryService = medicalHistoryService;
        this.patientService = patientService;
    }

    public List<WorkOrder> findAll() {
        return repository.findAll();
    }

    public Optional<WorkOrder> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public WorkOrder createOrder(WorkOrder workOrder) {
        MedicalHistory medHist = medicalHistoryService.createMedHist(workOrder);
        workOrder.setMedicalHistoryId(medHist.getId());
        scheduleService.updateStatus(workOrder.getSchedule().getId(), Status.FINISHED);

        return repository.save(workOrder);
    }

    public Optional<WorkOrder> findByEventId(Long id) {
        return repository.findByScheduleId(id);
    }

    @Transactional
    public WorkOrder completeOrder(Long id, WorkOrder workOrder) {
        WorkOrder found = repository.findById(id)
            .orElseThrow(() -> new IllegalStateException("work order not found"));
        found.setStatus(WorkOrder.Status.COMPLETED);
        found.setEmployeeDiscount(workOrder.getEmployeeDiscount());
        found.setAdminDiscount(workOrder.getAdminDiscount());
        found.setTotal(workOrder.getTotal());
        found.setSubTotal(workOrder.getSubTotal());
        found.setSaldo(workOrder.getSaldo());
        found.setAmountPaid(workOrder.getAmountPaid());
        scheduleService.updateStatus(found.getSchedule().getId(), Status.COMPLETED);
        patientService.updateSaldo(found.getPatient().getId(), workOrder.getSaldo());
        return repository.save(found);
    }
}
