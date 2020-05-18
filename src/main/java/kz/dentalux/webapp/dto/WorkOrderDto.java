package kz.dentalux.webapp.dto;

import java.time.OffsetDateTime;
import java.util.List;
import kz.dentalux.webapp.models.WorkOrder.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkOrderDto {

    private Long id;
    private boolean upperJaw;
    private boolean lowerJaw;
    private boolean oralCavity;
    private boolean adultTooth;
    private boolean childTooth;
    private boolean therapy;
    private boolean orthopedics;
    private boolean orthodontics;
    private boolean periodontology;
    private double employeeDiscount;
    private double adminDiscount;
    private int total;
    private int subTotal;
    private Long patientId;
    private Long scheduleId;
    private Long doctorId;
    private Status status = Status.CREATED;
    private List<SelectedServiceDto> selectedServices;
    private OffsetDateTime createdDate;
    private Long medicalHistoryId;
    private int saldo;
    private int debt;
    private int amountPaid;
}
