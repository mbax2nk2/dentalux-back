package kz.dentalux.webapp.services;

import java.util.List;
import java.util.Optional;
import kz.dentalux.webapp.dto.CompleteOrderRes;
import kz.dentalux.webapp.dto.CreateWorkOrderResDto;
import kz.dentalux.webapp.dto.EventDto;
import kz.dentalux.webapp.dto.MedicalHistoryDto;
import kz.dentalux.webapp.dto.PatientDto;
import kz.dentalux.webapp.dto.WorkOrderDto;
import kz.dentalux.webapp.models.MedicalHistory;
import kz.dentalux.webapp.models.Patient;
import kz.dentalux.webapp.models.Schedule.Status;
import kz.dentalux.webapp.models.WorkOrder;
import kz.dentalux.webapp.repositories.WorkOrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WorkOrderService extends AbstractService {

    private final WorkOrderRepository repository;
    private ScheduleService scheduleService;
    private MedicalHistoryService medicalHistoryService;
    private PatientService patientService;
    private ModelMapper modelMapper;

    public WorkOrderService(WorkOrderRepository repository,
        UserService userService, ScheduleService scheduleService,
        MedicalHistoryService medicalHistoryService,
        PatientService patientService, ModelMapper modelMapper) {
        super(userService);
        this.repository = repository;
        this.scheduleService = scheduleService;
        this.medicalHistoryService = medicalHistoryService;
        this.patientService = patientService;
        this.modelMapper = modelMapper;
    }

    public List<WorkOrder> findAll() {
        return repository.findAll();
    }

    public Optional<WorkOrder> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public CreateWorkOrderResDto createOrder(WorkOrder workOrder) {
        MedicalHistory medHist = medicalHistoryService.createMedHist(workOrder);
        workOrder.setMedicalHistoryId(medHist.getId());
        EventDto eventDto = scheduleService
            .updateStatus(workOrder.getSchedule().getId(), Status.FINISHED);
        WorkOrderDto worRes = modelMapper.map(repository.save(workOrder), WorkOrderDto.class);
        MedicalHistoryDto medHistory = modelMapper.map(medHist, MedicalHistoryDto.class);
        return new CreateWorkOrderResDto(medHistory, worRes, eventDto);
    }

    public Optional<WorkOrder> findByEventId(Long id) {
        return repository.findByScheduleId(id);
    }

    @Transactional
    public CompleteOrderRes completeOrder(Long id, WorkOrder workOrder) {
        WorkOrder found = repository.findById(id)
            .orElseThrow(() -> new IllegalStateException("work order not found"));
        found.setStatus(WorkOrder.Status.COMPLETED);
        found.setEmployeeDiscount(workOrder.getEmployeeDiscount());
        found.setAdminDiscount(workOrder.getAdminDiscount());
        found.setTotal(workOrder.getTotal());
        found.setSubTotal(workOrder.getSubTotal());
        found.setSaldo(workOrder.getSaldo());
        found.setAmountPaid(workOrder.getAmountPaid());
        EventDto eventDto = scheduleService
            .updateStatus(found.getSchedule().getId(), Status.COMPLETED);
        Patient patient = patientService
            .updateSaldo(found.getPatient().getId(), workOrder.getSaldo());
        WorkOrder savedWorkOrder = repository.save(found);

        PatientDto patientDto = modelMapper.map(patient, PatientDto.class);
        WorkOrderDto work = modelMapper.map(savedWorkOrder, WorkOrderDto.class);
        return new CompleteOrderRes(eventDto, patientDto, work);
    }
}
