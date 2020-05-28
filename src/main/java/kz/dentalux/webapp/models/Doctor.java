package kz.dentalux.webapp.models;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import kz.dentalux.webapp.dto.BusinessHoursDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String patronymic;
    private String eventColor;
    private Long userId;
    private Long companyId;

    @OneToMany(cascade = CascadeType.ALL)
    private List<BusinessHours> businessHours;

    public Doctor(Long userId, Long companyId) {
        this.userId = userId;
        this.companyId = companyId;
    }

    public Doctor(Long id) {
        this.id = id;
    }
}
