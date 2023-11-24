package apiEvent.Controller;

import apiEvent.Exception.BadRequestException;
import apiEvent.Exception.ResourceNotFoundException;
import apiEvent.Model.Event;
import apiEvent.Service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/event")
public class EventController {
    private EventService eventService;

    @Autowired
    public EventController(EventService eventService){
        this.eventService = eventService;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/crear")
    @PreAuthorize("hasAuthority('GROUP_CREAREVENTOS')")
    public ResponseEntity<Event> SaveNewEvent(@RequestBody Event event) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate parsedDate = LocalDate.parse(event.getDate(), formatter);
            event.setDate(parsedDate);
            Event savedEvent = eventService.SaveEvent(event);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEvent);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Event>> listAllEvents() {
        return ResponseEntity.ok(eventService.ListAllEvents());
    }

    @GetMapping("/random")
    public ResponseEntity<List<Event>> randomEvent() {
        List<Event> eventsList = eventService.ListAllEvents();
        Collections.shuffle(eventsList);
        return ResponseEntity.ok(eventsList);
    }
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Event>> eventByCategory(@PathVariable String category) throws BadRequestException {
        return ResponseEntity.ok(eventService.FindEventByCategory(category));
    }

    @GetMapping("/idcompany/{id}")
    public ResponseEntity<List<Event>> eventByCompany(@PathVariable Long id) throws BadRequestException {
        return ResponseEntity.ok(eventService.FindEventByIdCompany(id));
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<List<Event>> eventByLocation(@PathVariable String location) throws BadRequestException{
        return ResponseEntity.ok(eventService.FindEventByLocation(location));
    }

    @GetMapping("date/{date}")
    public ResponseEntity<List<Event>> eventByDate(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) throws BadRequestException{
        try {
            List<Event> events = eventService.FindEventByDate(date);
            if (!events.isEmpty()) {
                return ResponseEntity.ok(events);
            } else {
                return ResponseEntity.notFound().build(); // Or handle as appropriate
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Handle exceptions
        }
    }

    @GetMapping("id/{id}")
    public ResponseEntity<Event> findById(@PathVariable String id) throws BadRequestException, ResourceNotFoundException {
        return ResponseEntity.ok(eventService.FindEventByID(id));
    }

    @PutMapping
    public ResponseEntity<Event> editEvent(@RequestBody Event event) throws BadRequestException, ResourceNotFoundException {
        Event editedEvent = eventService.EditEvent(event);
        return ResponseEntity.ok(editedEvent);
    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable String id) throws ResourceNotFoundException, BadRequestException {
        eventService.DeleteByID(id);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }


}
