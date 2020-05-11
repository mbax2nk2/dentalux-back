package kz.dentalux.webapp.controllers;

import java.util.List;
import java.util.stream.Collectors;
import kz.dentalux.webapp.dto.WorkOrderDto;
import kz.dentalux.webapp.models.WorkOrder;
import kz.dentalux.webapp.services.WorkOrderService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/work-order")
@Slf4j
public class WorkOrderController {

    private WorkOrderService service;
    private ModelMapper modelMapper;

    public WorkOrderController(WorkOrderService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<WorkOrderDto> all() {
        return service.findAll()
            .stream()
            .map(value -> modelMapper.map(value, WorkOrderDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public WorkOrderDto findById(@PathVariable("id") Long id) {
        return service.findById(id)
            .map(value -> modelMapper.map(value, WorkOrderDto.class))
            .orElseThrow(() -> new IllegalStateException("doctor not found"));
    }


    @GetMapping("/search/{scheduleId}")
    public WorkOrderDto findByEventId(@PathVariable("scheduleId") Long id) {
        return service.findByEventId(id)
            .map(value -> modelMapper.map(value, WorkOrderDto.class))
            .orElseThrow(() -> new IllegalStateException("work order not found"));
    }

    @PostMapping("create-order")
    public WorkOrderDto createOrder(@RequestBody WorkOrderDto workOrderDto) {
        return modelMapper.map(service.createOrder(modelMapper.map(workOrderDto, WorkOrder.class)), WorkOrderDto.class);
    }


    @PutMapping("/complete-order/{id}")
    public WorkOrderDto completeOrder(@PathVariable("id") Long id, @RequestBody WorkOrderDto workOrderDto) {
        return modelMapper.map(service.completeOrder(id, modelMapper.map(workOrderDto, WorkOrder.class)), WorkOrderDto.class);
    }

}
