package kz.dentalux.webapp.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import kz.dentalux.webapp.dto.CompleteOrderRes;
import kz.dentalux.webapp.dto.CreateWorkOrderResDto;
import kz.dentalux.webapp.dto.EventDto;
import kz.dentalux.webapp.dto.MedicalHistoryDto;
import kz.dentalux.webapp.dto.PatientDto;
import kz.dentalux.webapp.dto.PayDebtResDto;
import kz.dentalux.webapp.dto.WorkOrderDto;
import kz.dentalux.webapp.models.MedicalHistory;
import kz.dentalux.webapp.models.Patient;
import kz.dentalux.webapp.models.Schedule.Status;
import kz.dentalux.webapp.models.WorkOrder;
import kz.dentalux.webapp.repositories.WorkOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
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
        found.setDebt(Math.abs(getDebt(workOrder)));
        found.setAmountPaid(workOrder.getAmountPaid() > workOrder.getTotal() ? workOrder.getTotal()
            : workOrder.getAmountPaid());
        EventDto eventDto = scheduleService
            .updateStatus(found.getSchedule().getId(), Status.COMPLETED);
        Patient patient = patientService
            .updateSaldo(found.getPatient().getId(), workOrder.getSaldo());
        WorkOrder savedWorkOrder = repository.save(found);
        repository.flush();
        int leftOver = workOrder.getAmountPaid() - workOrder.getTotal();
        if (leftOver > 0) {

            List<WorkOrder> andPayDebt = findAndPayDebt(leftOver,
                savedWorkOrder.getPatient().getId());
            andPayDebt.add(savedWorkOrder);
            List<WorkOrderDto> map1 = modelMapper
                .map(andPayDebt, new TypeToken<List<WorkOrderDto>>() {
                }.getType());
            PatientDto patientDto = modelMapper.map(patient, PatientDto.class);
            return new CompleteOrderRes(eventDto, patientDto, map1);
        }

        PatientDto patientDto = modelMapper.map(patient, PatientDto.class);
        WorkOrderDto work = modelMapper.map(savedWorkOrder, WorkOrderDto.class);
        return new CompleteOrderRes(eventDto, patientDto, Collections.singletonList(work));
    }

    private List<WorkOrder> findAndPayDebt(int leftOver, long patientId) {
        List<WorkOrder> workOrders = repository
            .findByPatient_IdAndDebtGreaterThanOrderByCreatedDateDesc(patientId, 0);
        int index = 0;
        log.info("leftOver left {}", leftOver);
        while (leftOver > 0 && index < workOrders.size()) {
            WorkOrder workOrder = workOrders.get(index);
            log.info("processing workorder id {}, leftOver {}, debt {}", workOrder.getId(),
                leftOver, workOrder.getDebt());
            if (leftOver >= workOrder.getDebt()) {
                leftOver -= workOrder.getDebt();
                log.info("leftOver left after subtraction if block {}", leftOver);
                workOrder.setDebt(0);
                workOrder.setAmountPaid(workOrder.getTotal());
            } else {
                workOrder.setAmountPaid(workOrder.getAmountPaid() + leftOver);
                leftOver -= workOrder.getDebt();
                log.info("leftOver left after subtraction else block {}, amountpaid {}", leftOver,
                    workOrder.getAmountPaid());
                workOrder.setDebt(Math.abs(leftOver));
                break;
            }
            index++;
        }
        return repository.saveAll(workOrders);
    }

    private Integer getDebt(WorkOrder workOrder) {
        int res = workOrder.getAmountPaid() - workOrder.getTotal();
        return Math.min(res, 0);

    }

    public PayDebtResDto payDebt(Long id, int amount) {
        WorkOrder found = repository.findById(id)
            .orElseThrow(() -> new IllegalStateException("order not found"));
        found.setDebt(Math.abs(amount - found.getDebt()));
        found.setAmountPaid(Math.min(amount + found.getAmountPaid(), found.getTotal()));
        Patient patient = patientService.updateSaldo(found.getPatient().getId(),
            amount + found.getPatient().getSaldo());
        repository.save(found);
        PatientDto patientDto = modelMapper.map(patient, PatientDto.class);
        WorkOrderDto work = modelMapper.map(found, WorkOrderDto.class);
        return new PayDebtResDto(work, patientDto);
    }
}
