package kz.dentalux.webapp.controllers;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import kz.dentalux.webapp.dto.WageDto;
import kz.dentalux.webapp.dto.WorkOrderDto;
import kz.dentalux.webapp.services.SalaryService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/salary")
public class SalaryController {

    private SalaryService salaryService;

    public SalaryController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    @GetMapping("/wage")
    public WageDto getWage() {
        return salaryService.getWage();
    }

    @GetMapping("/search")
    public List<WorkOrderDto> getWorkorders(@RequestParam("periodType") String periodType,
        @DateTimeFormat(iso = ISO.DATE_TIME) @RequestParam(value = "from", required = false)
            OffsetDateTime from,
        @DateTimeFormat(iso = ISO.DATE_TIME) @RequestParam(value = "to", required = false) OffsetDateTime to,
        @DateTimeFormat(iso = ISO.DATE_TIME) @RequestParam(value = "value", required = false)
            OffsetDateTime value) {
        return salaryService.getWorkOrders(periodType, from, to, value);
    }
}
