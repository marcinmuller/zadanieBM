package pl.mmuller.car_fleet_manager1.service;

import pl.mmuller.car_fleet_manager1.model.AppUser;
import pl.mmuller.car_fleet_manager1.model.AppUserDto;

import java.util.List;



public interface UserService {
    AppUser save(AppUserDto user);
    List<AppUser> findAll();
    void delete(long id);
    AppUser findOne(String username);
    AppUser findById(Long id);
}
