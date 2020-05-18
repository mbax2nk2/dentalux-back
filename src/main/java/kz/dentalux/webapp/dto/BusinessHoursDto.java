package kz.dentalux.webapp.dto;

import java.time.OffsetTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessHoursDto {

    private OffsetTime startTime;
    private OffsetTime endTime;
    private int[] daysOfWeek;
}
