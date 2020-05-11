package kz.dentalux.webapp.controllers;

import java.util.List;
import kz.dentalux.webapp.models.Patient;
import kz.dentalux.webapp.services.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/patient")
@Slf4j
public class PatientController {

    private PatientService service;

    public PatientController(PatientService service) {
        this.service = service;
    }

    @GetMapping
    public List<Patient> all() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Patient all(@PathVariable("id") Long id) {
        return service.findById(id)
            .orElseThrow(() -> new IllegalStateException("patient not found"));
    }

    @PutMapping("/{id}")
    public Patient update(@PathVariable("id") Long id, @RequestBody Patient patient) {
        return service.update(id, patient);
    }

    @PostMapping
    public Patient save(@RequestBody Patient patient) {
        return service.save(patient);
    }

    @GetMapping("/search")
    public List<Patient> findByCriteria(@RequestParam(value = "firstName", required = false) String firstName,
        @RequestParam(value = "lastName", required = false) String lastName,
        @RequestParam(value = "patronymic", required = false) String patronymic,
        @RequestParam(value = "mobilePhone", required = false) String mobilePhone) {
        log.info("firstName {}, lastName {}, patronymic {}, mobilePhone {}",
            firstName, lastName, patronymic, mobilePhone);
        return service.findByCriteria(firstName, lastName, patronymic, mobilePhone);
    }

}
