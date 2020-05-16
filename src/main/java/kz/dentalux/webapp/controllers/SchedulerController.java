package kz.dentalux.webapp.controllers;

import java.time.LocalDate;
import java.util.List;
import kz.dentalux.webapp.dto.CancelEventDto;
import kz.dentalux.webapp.dto.CreateEvent;
import kz.dentalux.webapp.dto.CreateEventRes;
import kz.dentalux.webapp.dto.EventDto;
import kz.dentalux.webapp.models.Schedule.Status;
import kz.dentalux.webapp.services.ScheduleService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/scheduler")
public class SchedulerController {

    private ScheduleService service;

    public SchedulerController(ScheduleService service) {
        this.service = service;
    }

    @GetMapping
    public List<EventDto> all() {
        return service.findAll();
    }

    @PostMapping
    public CreateEventRes create(@RequestBody CreateEvent createEvent) {
        return service.createEvent(createEvent);
    }

    @PutMapping("/updateInfo")
    public EventDto updateInfo(@RequestBody EventDto eventDto) {
        return service.updateInfo(eventDto);
    }


    @PutMapping("/updateStatus/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EventDto updateStatus(@PathVariable("id") Long id,
        @RequestParam("status") Status status) {
        return service.updateStatus(id, status);
    }

    @PutMapping("/cancel/{id}")
    public EventDto cancel(@PathVariable("id") Long id, @RequestBody CancelEventDto cancelEvent) {
        return service.cancelEvent(id, cancelEvent);
    }

    @GetMapping("/events")
    public List<EventDto> events(
        @RequestParam("date") @DateTimeFormat(iso = ISO.DATE) LocalDate selectedDate) {
        return service.getEvents(selectedDate);
    }

}
