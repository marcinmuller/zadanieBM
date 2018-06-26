package pl.mmuller.car_fleet_manager1.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Ride {
    @Id
    @GeneratedValue
    private long id;
    private long time;
    private long distance;

    @ManyToOne
    @JoinColumn(name="user")
    private AppUser user;

    @ManyToOne
    @JoinColumn(name="car")
    private Car car;
}
