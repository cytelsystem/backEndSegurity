package apiEvent.Model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Events")
public class Event {
    @Id
    private String id;
    private String idCompany;
    private String nameEvent;
    private String description;
    private String location;
    private String city;
    private LocalDate date;
    private String category;
    private Integer minimumAge;
    private String address;
    private String openTime;
    private List<Image> images = new ArrayList<>();
    private List<Ticket> tickets = new ArrayList<>();

    public String getDate() {
        // Convert LocalDate to String in the desired format
        return date != null ? date.toString() : null;
    }
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Image{
        private String url;
    }
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Ticket{
        private String type;
        private Double price;
        private Integer quantity;
    }

}
