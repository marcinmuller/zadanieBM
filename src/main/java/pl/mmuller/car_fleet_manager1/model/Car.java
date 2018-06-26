package pl.mmuller.car_fleet_manager1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Car {
    @Id
    @GeneratedValue
    private long id;
    private String brand;
    private String model;
    private String registrationNumber;
    private LocalDate creation;
    private LocalDate modification;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            joinColumns = {@JoinColumn(name = "cars_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "users_id", referencedColumnName = "id")}
    )
    public Set<AppUser> users=new HashSet<>();

    @JsonIgnore
    @OneToMany(
            targetEntity = Ride.class,
            mappedBy = "car",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    public Set<Ride> rides=new HashSet<>();
}
