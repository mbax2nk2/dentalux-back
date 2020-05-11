package kz.dentalux.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class IllnessDescriptionDto {
    private Long id;
    private int[] toothList;
    private String diagnose = "";
    private String treatment= "";
    private String complaints= "";
    private String objectively= "";
    private String recommendation= "";
    private String additionalInfo= "";
    private String anamsesis= "";
    private boolean upperJaw;
    private boolean lowerJaw;
    private boolean oralCavity;
}
