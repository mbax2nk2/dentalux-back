package kz.dentalux.webapp.dto;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TreatmentPlanDto {
    private boolean isChildTooth;
    private boolean isAdultTooth;

    private List<ToothDto> adultTooths;
    private List<ToothDto> childTooths;

    private List<PlanDto> plans;
}
