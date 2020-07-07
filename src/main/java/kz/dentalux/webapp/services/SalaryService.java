package kz.dentalux.webapp.services;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;
import kz.dentalux.webapp.dto.WageDto;
import kz.dentalux.webapp.dto.WorkOrderDto;
import kz.dentalux.webapp.models.AppUser;
import kz.dentalux.webapp.models.Doctor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class SalaryService extends AbstractAuthService {

    private DoctorService doctorService;
    private ModelMapper modelMapper;
    private WorkOrderService workOrderService;

    public SalaryService(DoctorService doctorService, ModelMapper modelMapper,
        WorkOrderService workOrderService) {
        this.doctorService = doctorService;
        this.modelMapper = modelMapper;
        this.workOrderService = workOrderService;
    }

    public WageDto getWage() {
        AppUser user = super.loggedInUser()
            .orElseThrow(() -> new IllegalStateException("unauthorised"));
        Doctor doctor = doctorService.findByUserId(user.getId())
            .orElseThrow(() -> new IllegalStateException("doctor not found"));
        return modelMapper.map(doctor, WageDto.class);
    }

    public List<WorkOrderDto> getWorkOrders(String periodType, OffsetDateTime from,
        OffsetDateTime to,
        OffsetDateTime value) {
        if (periodType.equals("period")) {
            return workOrderService.findWorkOrdersBetween(from, to).stream()
                .map(workOrder -> modelMapper.map(workOrder, WorkOrderDto.class)).collect(
                    Collectors.toList());
        } else if (periodType.equals("day")) {
            OffsetDateTime frm = OffsetDateTime.of(value.toLocalDateTime(), ZoneOffset.UTC).withHour(0).withMinute(0);
            OffsetDateTime to_ = OffsetDateTime.of(value.toLocalDateTime(), ZoneOffset.UTC).withHour(23)
                .withMinute(59);
            return workOrderService.findWorkOrdersBetween(frm, to_).stream()
                .map(workOrder -> modelMapper.map(workOrder, WorkOrderDto.class)).collect(
                    Collectors.toList());
        } else if (periodType.equals("month")) {
            OffsetDateTime frm = OffsetDateTime.of(value.toLocalDateTime(), ZoneOffset.UTC).withHour(0).withMinute(0);
            OffsetDateTime to_ = OffsetDateTime.of(value.toLocalDateTime(), ZoneOffset.UTC)
                .withDayOfMonth(value.toLocalDate().lengthOfMonth()).withHour(23).withMinute(59);
            return workOrderService.findWorkOrdersBetween(frm, to_).stream()
                .map(workOrder -> modelMapper.map(workOrder, WorkOrderDto.class)).collect(
                    Collectors.toList());
        } else if (periodType.equals("year")) {
            OffsetDateTime frm = OffsetDateTime.of(value.toLocalDateTime(), ZoneOffset.UTC).withMonth(1)
                .withDayOfMonth(1).withHour(0).withMinute(0);
            OffsetDateTime to_ = OffsetDateTime.of(value.toLocalDateTime(), ZoneOffset.UTC).withMonth(12)
                .withDayOfMonth(31).withHour(23)
                .withMinute(59);
            return workOrderService.findWorkOrdersBetween(frm, to_).stream()
                .map(workOrder -> modelMapper.map(workOrder, WorkOrderDto.class)).collect(
                    Collectors.toList());
        } else {
            throw new IllegalStateException("wrong periodType");
        }
    }
}
