package pl.mmuller.car_fleet_manager1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@Setter
@Getter
public class AppUser {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String firstName;
    private String surname;
    private String email;
    private LocalDate creation;
    private LocalDate modification;
    private LocalDate deletion;
    @JsonIgnore
    private String password;
    private String role;

    @JsonIgnore
    @ManyToMany(mappedBy = "users")
    public Set<Car> cars=new HashSet<>();

    @JsonIgnore
    @OneToMany
            (
            targetEntity = Ride.class,
            mappedBy = "user",
            cascade = CascadeType.ALL//,
    )
    private Set<Ride> rides=new HashSet<>();
}
