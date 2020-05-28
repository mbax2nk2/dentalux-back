package kz.dentalux.webapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.asm.Advice.Local;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessHoursDto {
    private Long id;
    private String startTime;
    private String endTime;
    private OffsetDateTime startTimeIns;
    private OffsetDateTime endTimeIns;
    private int[] daysOfWeek;
}
