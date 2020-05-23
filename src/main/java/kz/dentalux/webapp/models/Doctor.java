package kz.dentalux.webapp.models;

import java.sql.Date;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    public Doctor(Long userId, Long companyId) {
        this.userId = userId;
        this.companyId = companyId;
    }

    public Doctor(Long id) {
        this.id = id;
    }
}
