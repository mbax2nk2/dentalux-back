package kz.dentalux.webapp.models;

import java.time.OffsetDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MedicalHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Patient patient;

    @ManyToOne
    private Doctor doctor;

    @OneToOne
    private Schedule schedule;

    private boolean adultTooth;
    private boolean childTooth;

    @OneToMany(cascade = CascadeType.ALL)
    private List<IllnessDescription> illnessDescriptions;

    @CreationTimestamp
    private OffsetDateTime createdDate;
}
