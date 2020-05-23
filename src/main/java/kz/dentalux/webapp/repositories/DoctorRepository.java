package kz.dentalux.webapp.repositories;

import java.util.List;
import java.util.Optional;
import kz.dentalux.webapp.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByUserId(Long userId);
}
