package kz.dentalux.webapp.repositories;

import java.util.List;
import java.util.Optional;
import kz.dentalux.webapp.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findAppUserByUsername(String username);
    List<AppUser> findAllByCompany_id(Long companyId);
}
