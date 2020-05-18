package kz.dentalux.webapp.models;

import java.time.OffsetDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean upperJaw;
    private boolean lowerJaw;
    private boolean oralCavity;
    private boolean adultTooth;
    private boolean childTooth;
    private boolean therapy;
    private boolean orthopedics;
    private boolean orthodontics;
    private boolean periodontology;
    private double employeeDiscount;
    private double adminDiscount;
    private int total;
    private int subTotal;
    private Long medicalHistoryId;
    private Integer debt;
    @Transient
    private Integer saldo;
    private Integer amountPaid;

    @Enumerated(EnumType.STRING)
    private Status status = Status.CREATED;

    public enum Status {
        CREATED, COMPLETED
    }

    @ManyToOne
    private Patient patient;


    @ManyToOne
    private Doctor doctor;

    @OneToOne
    private Schedule schedule;

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<SelectedService> selectedServices;

    @CreationTimestamp
    private OffsetDateTime createdDate;
}
