package kz.dentalux.webapp.repositories;

import java.util.List;
import kz.dentalux.webapp.models.Doctor;
import kz.dentalux.webapp.models.MedicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Long> {

}
