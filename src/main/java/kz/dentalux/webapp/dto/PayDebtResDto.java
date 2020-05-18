package kz.dentalux.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayDebtResDto {
    private WorkOrderDto workOrder;
    private PatientDto patient;
}
