package kz.dentalux.webapp.models;

import com.vladmihalcea.hibernate.type.array.IntArrayType;
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
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@TypeDefs({
    @TypeDef(
        name = "int-array",
        typeClass = IntArrayType.class
    )
})
public class SelectedService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long serviceId;

    @Type(type = "int-array")
    @Column(
        columnDefinition = "integer[]"
    )
    private int[] toothList;

    private Integer quantity;

}
