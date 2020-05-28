package kz.dentalux.webapp.controllers;

import java.util.List;
import java.util.stream.Collectors;
import kz.dentalux.webapp.dto.ResourceDto;
import kz.dentalux.webapp.services.DoctortService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/doctor")
@Slf4j
public class DoctorController {

    private DoctortService service;

    public DoctorController(DoctortService service) {
        this.service = service;
    }

    @GetMapping
    public List<ResourceDto> all() {
        return service.findAll();
    }

    @PutMapping("/{id}/workingHours")
    public ResourceDto updateWorkingHours(@PathVariable Long id, @RequestBody ResourceDto resourceDto) {
        return service.updateWorkingHours(id, resourceDto);
    }

//    @GetMapping("/{id}")
//    public ResourceDto findById(@PathVariable("id") Long id) {
//        return service.findById(id)
//            .map(doctor -> new ResourceDto(doctor.getId(),
//                String.format("%s %s.%s.", doctor.getLastName(),
//                    doctor.getFirstName().substring(0, 1),
//                    doctor.getPatronymic().substring(0, 1)
//                ),
//                doctor.getEventColor()
//            )).orElseThrow(() -> new IllegalStateException("doctor not found"));
//    }

}
