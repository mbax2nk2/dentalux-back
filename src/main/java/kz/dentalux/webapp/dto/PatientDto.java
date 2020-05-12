package kz.dentalux.webapp.dto;

import java.sql.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PatientDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private Date birthDate;
    private String livingAddress;
    private Character gender;
    private String mobilePhone;
    private String telephone;
    private TreatmentPlanDto treatmentPlan;
    private String comment;
    private String additionalComment;
    private String email;

}
