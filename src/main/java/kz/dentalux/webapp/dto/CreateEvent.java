package kz.dentalux.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEvent {
    private EventDto event;
    private PatientDto patient;
}
