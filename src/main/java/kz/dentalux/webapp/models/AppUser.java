package kz.dentalux.webapp.models;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import kz.dentalux.webapp.models.dictionary.Color;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.asm.Advice.Local;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String password;
    private String username;
    private LocalDate birthDate;
    private String mobilePhone;
    private String phone;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String livingAddress;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;
    private Integer professionId;
    private String email;
    private Character gender;
    private String eventColor;
    private Boolean canGiveDiscount;
    private Integer therapy;
    private Integer orthodontics;
    private Integer orthopedics;
    private Integer surgery;
    private Integer periodontium;
    private Integer periodontology;
    private Integer childrenDentistry;
    private Integer implantation;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<AppGrantedAuthority> authorities;

    @OneToMany(cascade = CascadeType.ALL)
    private List<BusinessHours> businessHours;

    @OneToOne
    private Company company;

    public AppUser(String username, List<AppGrantedAuthority> authorities,
        Company company) {
        this.username = username;
        this.authorities = authorities;
        this.company = company;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

}
