package kz.dentalux.webapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.List;
import kz.dentalux.webapp.models.AppGrantedAuthority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private Long id;
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String firstName;
    private String lastName;
    private String patronymic;
    private LocalDate birthDate;
    private Character gender;
    private String livingAddress;
    private String mobilePhone;
    private String phone;
    private String email;
    private int professionId;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;
    private List<AppGrantedAuthority> authorities;
    private String companyName;
    private String eventColor;
    private Boolean canGiveDiscount;

}