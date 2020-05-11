package kz.dentalux.webapp.repositories;

import java.time.LocalDate;
import java.util.List;
import kz.dentalux.webapp.models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("select s from Schedule s  WHERE date(s.startTime) = :selectedDate")
    List<Schedule> findByStartTime(LocalDate selectedDate);
}
