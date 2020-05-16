package kz.dentalux.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CancelEventDto {
    private Integer cancelReasonId;
    private String cancelComment;
}
