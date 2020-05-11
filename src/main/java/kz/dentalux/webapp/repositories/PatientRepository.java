package kz.dentalux.webapp.repositories;

import kz.dentalux.webapp.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {

}
