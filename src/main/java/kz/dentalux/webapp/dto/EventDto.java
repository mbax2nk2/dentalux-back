package kz.dentalux.webapp.dto;

import java.time.OffsetDateTime;
import java.util.Map;
import kz.dentalux.webapp.models.Schedule.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {
    private Long id;
    private Long resourceId;
    private OffsetDateTime start;
    private OffsetDateTime end;
    private String title;
    private Status status;
    private String patientMobilePhone;
    private Long patientId;
    private Map<String, Object> extendedProps;
    private String note;
    private OffsetDateTime createdDate;
}
