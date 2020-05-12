package kz.dentalux.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompleteOrderRes {
    private EventDto event;
    private PatientDto patient;
    private WorkOrderDto workOrder;
}
