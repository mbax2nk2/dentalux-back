package kz.dentalux.webapp.services;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import kz.dentalux.webapp.dto.CancelEventDto;
import kz.dentalux.webapp.dto.CreateEvent;
import kz.dentalux.webapp.dto.CreateEventRes;
import kz.dentalux.webapp.dto.EventDto;
import kz.dentalux.webapp.dto.PatientDto;
import kz.dentalux.webapp.models.Doctor;
import kz.dentalux.webapp.models.Patient;
import kz.dentalux.webapp.models.Schedule;
import kz.dentalux.webapp.models.Schedule.Status;
import kz.dentalux.webapp.repositories.ScheduleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ScheduleService {

    private ScheduleRepository repository;
    private PatientService patientService;
    private DoctortService doctortService;
    private ModelMapper modelMapper;

    public ScheduleService(ScheduleRepository repository,
        PatientService patientService, DoctortService doctortService,
        ModelMapper modelMapper) {
        this.repository = repository;
        this.patientService = patientService;
        this.doctortService = doctortService;
        this.modelMapper = modelMapper;
    }

    public CreateEventRes createEvent(CreateEvent createEvent) {
        EventDto event = createEvent.getEvent();
        PatientDto patientDto = createEvent.getPatient();
        Schedule schedule = new Schedule();
        if (patientDto.getId() != null) {
            Patient patient = patientService.findById(patientDto.getId())
                .orElseThrow(() -> new IllegalStateException("patient not found"));
            schedule.setPatient(patient);
        } else {
            Patient patient = modelMapper.map(patientDto, Patient.class);
            schedule.setPatient(patient);
        }
        Doctor doctor = doctortService.findById(event.getResourceId())
            .orElseThrow(() -> new IllegalStateException("doctor not found"));

        schedule.setDoctor(doctor);
        schedule.setStartTime(event.getStart());
        schedule.setEndTime(event.getEnd());
        schedule.setNote(event.getNote());

        Schedule saved = repository.save(schedule);

        EventDto res = getEventDto(saved);
        PatientDto resPatient = modelMapper.map(saved.getPatient(), PatientDto.class);
        return new CreateEventRes(res, resPatient);

    }

    private EventDto getEventDto(Schedule saved) {
        EventDto res = new EventDto();
        res.setEnd(saved.getEndTime());
        res.setId(saved.getId());
        res.setNote(saved.getNote());
        res.setResourceId(saved.getDoctor().getId());
        res.setStatus(saved.getStatus());
        res.setTitle(getShortName(saved));
        res.setStart(saved.getStartTime());
        res.setPatientId(saved.getPatient().getId());
        res.setCreatedDate(saved.getCreatedDate());
        res.setPatientMobilePhone(saved.getPatient().getMobilePhone());
        res.setCancelComment(saved.getCancelComment());
        res.setCancelReasonId(saved.getCancelReasonId());
        return res;
    }

    private String getShortName(Schedule schedule) {
        return String.format("%s %s", schedule.getPatient().getLastName(),
            schedule.getPatient().getFirstName());
    }

    public List<EventDto> getEvents(LocalDate selectedDate) {
        return repository.findByStartTime(selectedDate)
            .stream()
            .map(this::getEventDto)
            .collect(Collectors.toList());
    }

    public EventDto updateInfo(EventDto eventDto) {
        Schedule schedule = repository.findById(eventDto.getId())
            .orElseThrow(() -> new IllegalStateException("event not found"));

        schedule.setStartTime(eventDto.getStart());
        schedule.setEndTime(eventDto.getEnd());

        if (eventDto.getResourceId() != null) {
            Doctor doctor = doctortService.findById(eventDto.getResourceId())
                .orElseThrow(() -> new IllegalStateException("doctor not found"));
            schedule.setDoctor(doctor);
        }

        Schedule saved = repository.save(schedule);
        return getEventDto(saved);
    }

    public EventDto updateStatus(Long id, Status status) {
        Schedule schedule = repository.findById(id)
            .orElseThrow(() -> new IllegalStateException("event not found"));

        schedule.setStatus(status);

        Schedule saved = repository.save(schedule);

        return getEventDto(saved);
    }

    public Optional<Schedule> findById(long id) {
        return repository.findById(id);
    }

    public void save(Schedule schedule) {
        repository.save(schedule);
    }

    public List<EventDto> findAll() {
        return repository.findAll()
            .stream()
            .map(this::getEventDto)
            .collect(Collectors.toList());
    }

    public EventDto cancelEvent(Long id, CancelEventDto cancelEvent) {
        Schedule schedule = repository.findById(id)
            .orElseThrow(() -> new IllegalStateException("schedule not found"));
        schedule.setStatus(Status.CANCELLED);
        schedule.setCancelReasonId(cancelEvent.getCancelReasonId());
        schedule.setCancelComment(cancelEvent.getCancelComment());
        Schedule saved = repository.save(schedule);
        return getEventDto(saved);
    }
}
