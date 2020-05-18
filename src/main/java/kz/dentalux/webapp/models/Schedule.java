package kz.dentalux.webapp.models;

import java.time.OffsetDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private OffsetDateTime startTime;
    private OffsetDateTime endTime;
    private String note;
    private Integer cancelReasonId;
    private String cancelComment;
    private Integer changeReasonId;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    private Patient patient;

    @ManyToOne
    private Doctor doctor;

    @CreationTimestamp
    private OffsetDateTime createdDate;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;


    public enum Status {
        PENDING, ARRIVED, MISSED, FINISHED, CONFIRMED, START_APPOINTMENT, COMPLETED,
        CANCELLED
    }

    public Schedule(Long id) {
        this.id = id;
    }
}
