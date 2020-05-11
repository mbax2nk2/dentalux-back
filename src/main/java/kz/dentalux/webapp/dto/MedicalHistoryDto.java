package kz.dentalux.webapp.dto;

import java.time.OffsetDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalHistoryDto {

    private Long id;
    private Long patientId;
    private Long doctorId;
    private Long scheduleId;
    private boolean adultTooth;
    private boolean childTooth;
    private List<IllnessDescriptionDto> illnessDescriptions;
    private OffsetDateTime createdDate;

}
