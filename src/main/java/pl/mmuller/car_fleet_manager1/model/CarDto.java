package pl.mmuller.car_fleet_manager1.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CarDto {
    private long id;
    private String brand;
    private String model;
    private String registrationNumber;
    private LocalDate creation;
    private LocalDate modification;

}
