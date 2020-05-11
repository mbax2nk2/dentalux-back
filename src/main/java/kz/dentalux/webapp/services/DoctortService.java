package kz.dentalux.webapp.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import kz.dentalux.webapp.dto.ResourceDto;
import kz.dentalux.webapp.models.Doctor;
import kz.dentalux.webapp.repositories.DoctorRepository;
import org.springframework.stereotype.Service;

@Service
public class DoctortService extends AbstractService {

    private final DoctorRepository repository;

    public DoctortService(DoctorRepository repository,
        UserService userService) {
        super(userService);
        this.repository = repository;
    }

    public List<ResourceDto> findAll() {
        Long loggedInUserId = super.getLoggedInUserId()
            .orElseThrow(() -> new IllegalStateException("not authenticated"));
        return repository.findAll()
            .stream()
            .map(doctor -> new ResourceDto(doctor.getId(),
                String.format("%s %s.%s.", doctor.getLastName(),
                    doctor.getFirstName().substring(0, 1),
                    doctor.getPatronymic().substring(0, 1)
                ),
                doctor.getEventColor(),
                loggedInUserId.equals(doctor.getUserId())
            )).collect(Collectors.toList());
//        if (calType.equals("all")) {
//        }
//        Long loggedInUserId = super.getLoggedInUserId()
//            .orElseThrow(() -> new IllegalStateException("not authenticated"));
//        return repository.findByUserId(loggedInUserId);
    }

    public Optional<Doctor> findById(Long id) {
        return repository.findById(id);
    }
}
