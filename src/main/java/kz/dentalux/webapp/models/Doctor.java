package kz.dentalux.webapp.models;

import java.sql.Date;
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
    private Date birthDate;
    private String livingAddress;
    private Character gender;
    private String mobilePhone;
    private String telephone;
    private String eventColor;
    private Long userId;

    public Doctor(Long id) {
        this.id = id;
    }
}
