package kz.dentalux.webapp.dto;

import java.sql.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResourceDto {
    private Long id;
    private String title;
    private String eventColor;
    private boolean self;
    private List<BusinessHoursDto> businessHours;
}
