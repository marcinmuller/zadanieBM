package pl.mmuller.car_fleet_manager1.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class AppUserDto {
    private String name;
    private String surname;
    private String email;
    private LocalDate creation;
    private LocalDate modification;
    private LocalDate deletion;
    private String password;
    private String role;
}
