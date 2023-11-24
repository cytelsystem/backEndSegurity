package apiEvent.Repository;

import apiEvent.Model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {
    List<Event> findByCategory(String category);
    List<Event> findByLocation(String location);
    List<Event> findByDate(LocalDate date);
    List<Event> findByIdCompany(Long id);
}
