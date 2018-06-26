package pl.mmuller.car_fleet_manager1.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RideDto {
    private long id;
    private long time;
    private long distance;
}
