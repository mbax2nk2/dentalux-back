package kz.dentalux.webapp.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompleteOrderRes {
    private EventDto event;
    private PatientDto patient;
    private List<WorkOrderDto> workOrders;
}
