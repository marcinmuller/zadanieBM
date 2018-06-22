package pl.mmuller.car_fleet_manager1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Setter
@Getter
public class AppUser {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String surname;
    private String email;
    private LocalDate creation;
    private LocalDate modification;
    private LocalDate deletion;
    @JsonIgnore
    private String password;
    private String role;
}
