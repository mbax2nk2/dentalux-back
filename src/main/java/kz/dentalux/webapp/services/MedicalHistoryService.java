package kz.dentalux.webapp.services;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import kz.dentalux.webapp.models.Doctor;
import kz.dentalux.webapp.models.IllnessDescription;
import kz.dentalux.webapp.models.MedicalHistory;
import kz.dentalux.webapp.models.Patient;
import kz.dentalux.webapp.models.Schedule;
import kz.dentalux.webapp.models.SelectedService;
import kz.dentalux.webapp.models.WorkOrder;
import kz.dentalux.webapp.repositories.MedicalHistoryRepository;
import org.springframework.stereotype.Service;

@Service
public class MedicalHistoryService extends AbstractService {

    private final MedicalHistoryRepository repository;

    public MedicalHistoryService(MedicalHistoryRepository repository,
        UserService userService) {
        super(userService);
        this.repository = repository;
    }

    public List<MedicalHistory> findAll() {
        return repository.findAll();
    }

    public Optional<MedicalHistory> findById(Long id) {
        return repository.findById(id);
    }

    public MedicalHistory save(MedicalHistory medicalHistory) {
        return repository.save(medicalHistory);
    }

    public MedicalHistory update(Long id, MedicalHistory medicalHistory) {
        repository.findById(id).orElseThrow(() -> new IllegalStateException("med hist not found"));
        return repository.save(medicalHistory);
    }

    public MedicalHistory createMedHist(WorkOrder saved) {
        int[] toothList = saved.getSelectedServices().stream()
            .map(SelectedService::getToothList)
            .flatMapToInt(Arrays::stream)
            .toArray();
        IllnessDescription illnessDescription = IllnessDescription.builder()
            .lowerJaw(saved.isLowerJaw())
            .upperJaw(saved.isUpperJaw())
            .toothList(toothList)
            .oralCavity(saved.isOralCavity())
            .build();

        MedicalHistory medicalHistory =
            MedicalHistory.builder()
                .adultTooth(saved.isAdultTooth())
                .childTooth(saved.isChildTooth())
                .doctor(new Doctor(saved.getDoctor().getId()))
                .patient(new Patient(saved.getPatient().getId()))
                .schedule(new Schedule(saved.getSchedule().getId()))
                .illnessDescriptions(Collections.singletonList(illnessDescription))
                .build();
        return repository.save(medicalHistory);
    }
}
