package kz.dentalux.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateWorkOrderResDto {
    private MedicalHistoryDto medicalHistory;
    private WorkOrderDto workOrder;
    private EventDto event;
}
