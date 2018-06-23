package pl.mmuller.car_fleet_manager1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Entity
@Setter
@Getter
public class AppUser {
    @Id
    @GeneratedValue
    private long id;
    @NotNull
    @UniqueElements
    private String name;
    private String firstName;
    private String surname;
    private String email;
    private LocalDate creation;
    private LocalDate modification;
    private LocalDate deletion;
    @JsonIgnore
    @NotNull
    private String password;
    @NotNull
    private String role;
}
