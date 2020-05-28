package kz.dentalux.webapp.models;

import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BusinessHours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private OffsetDateTime startTimeIns;
    private OffsetDateTime endTimeIns;
    private String startTime;
    private String endTime;

    @Type(type = "int-array")
    @Column(
        columnDefinition = "integer[]"
    )
    private int[] daysOfWeek;

}
