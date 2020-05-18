package kz.dentalux.webapp.models;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
public class Patient {
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
    private int saldo;
    private String comment;
    private String additionalComment;
    private String email;
    @Enumerated(EnumType.STRING)
    private Status status = Status.FIRST_TIME;
    private Boolean child;
    private String parentFullName;

    public enum Status {
        FIRST_TIME, REPEATED
    }

    public Patient(Long id) {
        this.id = id;
    }
}
