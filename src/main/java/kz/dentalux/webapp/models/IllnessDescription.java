package kz.dentalux.webapp.models;


import com.vladmihalcea.hibernate.type.array.IntArrayType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@TypeDefs({
    @TypeDef(
        name = "int-array",
        typeClass = IntArrayType.class
    )
})
@Builder
public class IllnessDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Type(type = "int-array")
    @Column(
        columnDefinition = "integer[]"
    )
    private int[] toothList;
    private String diagnose;
    private String treatment;
    private String complaints;
    private String objectively;
    private String recommendation;
    private String anamsesis;
    private String additionalInfo;
    private boolean upperJaw;
    private boolean lowerJaw;
    private boolean oralCavity;
}
