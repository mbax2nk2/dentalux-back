package kz.dentalux.webapp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import kz.dentalux.webapp.dto.BusinessHoursDto;
import kz.dentalux.webapp.dto.ResourceDto;
import kz.dentalux.webapp.models.AppUser;
import kz.dentalux.webapp.models.BusinessHours;
import kz.dentalux.webapp.models.Doctor;
import kz.dentalux.webapp.repositories.DoctorRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.internal.asm.TypeReference;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class DoctortService extends AbstractAuthService {

    private final DoctorRepository repository;
    private final ModelMapper modelMapper;

    public DoctortService(DoctorRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
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
//        BusinessHoursDto businessHoursDto = new BusinessHoursDto(
//            LocalTime.of(9, 0),
//            LocalTime.of(11, 0),
//            new int[]{1});
//        BusinessHoursDto businessHoursDto1 = new BusinessHoursDto(
//            LocalTime.of(12, 0),
//            LocalTime.of(17, 0),
//            new int[]{1, 2, 4, 5, 6, 7});
        List<BusinessHoursDto> businessHours = modelMapper
            .map(doctor.getBusinessHours(), new TypeToken<List<BusinessHoursDto>>() {
            }.getType());
        return new ResourceDto(doctor.getId(),
            String.format("%s %s. %s", doctor.getLastName(),
                doctor.getFirstName().substring(0, 1),
                StringUtils.isEmpty(doctor.getPatronymic()) ? ""
                    : doctor.getPatronymic().substring(0, 1).concat(".")
            ),
            doctor.getEventColor(), userId.equals(doctor.getUserId()),
            businessHours
//            Arrays.asList(businessHoursDto1)
        );
    }

    private ResourceDto getResourceDto(boolean self, Doctor doctor) {
//        BusinessHoursDto businessHoursDto = new BusinessHoursDto(
//            LocalTime.of(9, 0),
//            LocalTime.of(11, 0),
//            new int[]{1});
//        BusinessHoursDto businessHoursDto1 = new BusinessHoursDto(
//            LocalTime.of(12, 0),
//            LocalTime.of(17, 0),
//            new int[]{1, 2, 4, 5, 6, 7});
        List<BusinessHoursDto> businessHours = modelMapper
            .map(doctor.getBusinessHours(), new TypeToken<List<BusinessHoursDto>>() {
            }.getType());
        return new ResourceDto(doctor.getId(),
            String.format("%s %s. %s", doctor.getLastName(),
                doctor.getFirstName().substring(0, 1),
                StringUtils.isEmpty(doctor.getPatronymic()) ? ""
                    : doctor.getPatronymic().substring(0, 1).concat(".")
            ),
            doctor.getEventColor(), self,
            businessHours
//            Arrays.asList(businessHoursDto1)
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
        doctor.setImplantation(user.getImplantation());
        doctor.setOrthodontics(user.getOrthodontics());
        doctor.setChildrenDentistry(user.getChildrenDentistry());
        doctor.setOrthopedics(user.getOrthopedics());
        doctor.setPeriodontium(user.getPeriodontium());
        doctor.setPeriodontology(user.getPeriodontology());
        doctor.setSurgery(user.getSurgery());
        doctor.setTherapy(user.getTherapy());
        List<BusinessHours> businessHours = new ArrayList<>();
//        BeanUtils.copyProperties(user.getBusinessHours(), businessHours);
        doctor.setBusinessHours(new ArrayList<>(user.getBusinessHours()));
        Doctor saved = repository.save(doctor);
        return getResourceDto(0L, saved);
    }

    public ResourceDto updateWorkingHours(Long id, ResourceDto resourceDto) {
        Doctor doctor = repository.findById(id)
            .orElseThrow(() -> new IllegalStateException("doctor not found"));

        List<BusinessHours> map = modelMapper
            .map(resourceDto.getBusinessHours(), new TypeToken<List<BusinessHours>>() {
            }.getType());
        doctor.setBusinessHours(map);
        repository.save(doctor);
        return getResourceDto(resourceDto.isSelf(), doctor);

    }
}
