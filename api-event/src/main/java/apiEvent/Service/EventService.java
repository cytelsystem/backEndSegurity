package apiEvent.Service;

import apiEvent.Exception.BadRequestException;
import apiEvent.Exception.ResourceNotFoundException;
import apiEvent.Model.Event;
import apiEvent.Repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;


@Service
public class EventService {


    private EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository){
        this.eventRepository = eventRepository;
    }

    public Event SaveEvent(Event e){
       return eventRepository.save(e);
    }

    public List<Event> ListAllEvents(){
        return eventRepository.findAll();
    }

    public Event FindEventByID(String id) throws ResourceNotFoundException {
        Optional<Event> eventSearched = eventRepository.findById(id);

        if (eventSearched.isPresent()) {
            return eventRepository.findById(id).get();
        } else {
            throw new ResourceNotFoundException("Event with ID " + id + " not found");
        }
    }

    public List<Event> FindEventByIdCompany(String id) throws BadRequestException {
        Optional<List<Event>> evetsCompanySearched = Optional.ofNullable(eventRepository.findByIdCompany(id));
        if (evetsCompanySearched.isPresent()){
            return eventRepository.findByIdCompany(id);
        }else{
            throw new BadRequestException("Event doesn't exist");
        }
    }

    public List<Event> FindEventByCategory(String category) throws BadRequestException{
        Optional<List<Event>> categorySearched = Optional.ofNullable(eventRepository.findByCategory(category));
        if (categorySearched.isPresent()){
            return eventRepository.findByCategory(category);
        }else{
            throw new BadRequestException("Category doesn't exist");
        }
    }

    public List<Event> FindEventByLocation(String location) throws BadRequestException{
        Optional<List<Event>> locationSearched = Optional.ofNullable(eventRepository.findByLocation(location));
        if (locationSearched.isPresent()){
            return eventRepository.findByLocation(location);
        }else{
            throw new BadRequestException("Location doesn't exist");
        }
    }

    public List<Event> FindEventByDate(LocalDate date) throws BadRequestException{
        Optional<List<Event>> dateSearched = Optional.ofNullable(eventRepository.findByDate(date));
        if (!dateSearched.isEmpty()) {
                return eventRepository.findByDate(date);
            } else {
                throw new BadRequestException("There are no events with that date");
            }
        }


    public void DeleteByID(String id) throws BadRequestException{
        Optional<Event> eventSearched = eventRepository.findById(id);
        if (eventSearched.isPresent()){
            eventRepository.deleteById(id);
        } else {
            throw new BadRequestException("This event can't be deleted");
        }
    }

    public Event EditEvent(Event event) throws BadRequestException, ResourceNotFoundException {
        Optional<Event> eventSearched = Optional.ofNullable(FindEventByID(event.getId()));
        if (eventSearched.isPresent()){
            return eventRepository.save(event);
        } else {
            throw new ResourceNotFoundException("The event doesn't exist");
        }
    }
}
