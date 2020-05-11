package kz.dentalux.webapp.services;

import java.util.List;
import java.util.Optional;
import kz.dentalux.webapp.models.Patient;
import kz.dentalux.webapp.repositories.PatientRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    private final PatientRepository repository;

    public PatientService(PatientRepository repository) {
        this.repository = repository;
    }

    public List<Patient> findAll() {
        return repository.findAll();
    }

    public List<Patient> findByCriteria(String firstName, String lastName, String patronymic,
        String mobilePhone) {
        Patient patient = new Patient();
        if (!patronymic.isEmpty()) {
            patient.setPatronymic(patronymic);
        }
        if (!mobilePhone.isEmpty()) {
            patient.setMobilePhone(mobilePhone);
        }
        if (!firstName.isEmpty()) {
            patient.setFirstName(firstName);
        }
        if (!lastName.isEmpty()) {
            patient.setLastName(lastName);
        }
        return repository.findAll(Example.of(patient));
    }

    public Optional<Patient> findById(Long id) {
        return repository.findById(id);
    }

    public Patient update(Long id, Patient patient) {
        repository.findById(id).orElseThrow(() -> new IllegalStateException("not found"));
        return repository.save(patient);
    }

    public Patient save(Patient patient) {
        return repository.save(patient);
    }

    public void updateSaldo(Long patientId, Integer saldo) {
        Patient patient = repository.findById(patientId)
            .orElseThrow(() -> new IllegalStateException("patient not found"));
        patient.setSaldo(saldo);
        repository.save(patient);
    }
}
