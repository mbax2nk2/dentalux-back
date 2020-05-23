package kz.dentalux.webapp.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import kz.dentalux.webapp.dto.ResourceDto;
import kz.dentalux.webapp.models.AppUser;
import kz.dentalux.webapp.models.Doctor;
import kz.dentalux.webapp.repositories.DoctorRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class DoctortService extends AbstractAuthService {

    private final DoctorRepository repository;

    public DoctortService(DoctorRepository repository) {
        this.repository = repository;
    }

    public List<ResourceDto> findAll() {
        AppUser user = super.loggedInUser()
            .orElseThrow(() -> new IllegalStateException("not authenticated"));
        return repository.findAll()
            .stream()
            .map(doctor -> getResourceDto(user.getId(), doctor)).collect(Collectors.toList());
//        if (calType.equals("all")) {
//        }
//        Long loggedInUserId = super.getLoggedInUserId()
//            .orElseThrow(() -> new IllegalStateException("not authenticated"));
//        return repository.findByUserId(loggedInUserId);
    }

    private ResourceDto getResourceDto(Long userId, Doctor doctor) {
        return new ResourceDto(doctor.getId(),
            String.format("%s %s. %s", doctor.getLastName(),
                doctor.getFirstName().substring(0, 1),
                StringUtils.isEmpty(doctor.getPatronymic()) ? ""
                    : doctor.getPatronymic().substring(0, 1).concat(".")
            ),
            doctor.getEventColor(),userId.equals(doctor.getUserId())
//            new BusinessHoursDto(OffsetTime.of(LocalTime.of(12, 0), ZoneOffset.UTC),
//                OffsetTime.of(LocalTime.of(17, 0), ZoneOffset.UTC),
//                new int[]{1, 2, 3, 4, 5, 6, 7})
        );
    }

    public Optional<Doctor> findById(Long id) {
        return repository.findById(id);
    }

    public ResourceDto saveDoctor(AppUser user) {
        Doctor doctor = repository.findByUserId(user.getId())
            .orElseGet(() -> new Doctor(user.getId(), user.getCompany().getId()));
        doctor.setEventColor(user.getEventColor());
        doctor.setFirstName(user.getFirstName());
        doctor.setLastName(user.getLastName());
        doctor.setPatronymic(user.getPatronymic());
        Doctor saved = repository.save(doctor);
        return getResourceDto(0L, saved);
    }
}
