package kz.dentalux.webapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

@NoArgsConstructor
@Getter
@Setter
public class WageDto {
    private Integer therapy;
    private Integer orthodontics;
    private Integer orthopedics;
    private Integer surgery;
    private Integer periodontium;
    private Integer periodontology;
    private Integer childrenDentistry;
    private Integer implantation;
}
