package kz.dentalux.webapp.controllers;

import java.util.List;
import java.util.stream.Collectors;
import kz.dentalux.webapp.dto.MedicalHistoryDto;
import kz.dentalux.webapp.models.MedicalHistory;
import kz.dentalux.webapp.services.MedicalHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/medical-history")
@Slf4j
public class MedicalHistoryController {

    private MedicalHistoryService service;
    private ModelMapper modelMapper;

    public MedicalHistoryController(MedicalHistoryService service,
        ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<MedicalHistoryDto> all(
        @RequestParam(value = "calType", defaultValue = "mine") String calType) {
        return service.findAll()
            .stream()
            .map(medicalHistory -> modelMapper.map(medicalHistory, MedicalHistoryDto.class))
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public MedicalHistoryDto findById(@PathVariable("id") Long id) {
        return service.findById(id)
            .map(medicalHistory -> modelMapper.map(medicalHistory, MedicalHistoryDto.class))
            .orElseThrow(() -> new IllegalStateException("med history not found"));
    }


    @PostMapping
    public MedicalHistoryDto save(@RequestBody MedicalHistoryDto dto) {
        MedicalHistory map = modelMapper.map(dto, MedicalHistory.class);
        return modelMapper.map(service.save(map), MedicalHistoryDto.class);
    }


    @PutMapping("/{id}")
    public MedicalHistoryDto update(@PathVariable("id") Long id, @RequestBody MedicalHistoryDto dto) {
        MedicalHistory map = modelMapper.map(dto, MedicalHistory.class);
        return modelMapper.map(service.update(id, map), MedicalHistoryDto.class);
    }

}
