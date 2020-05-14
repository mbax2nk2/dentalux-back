package kz.dentalux.webapp.dto;

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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SelectedServiceDto {

    private Long id;
    private Long serviceId;
    private int[] toothList;
    private int quantity;

}
