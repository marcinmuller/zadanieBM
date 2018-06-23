package pl.mmuller.car_fleet_manager1.service;

import pl.mmuller.car_fleet_manager1.model.AppUser;
import pl.mmuller.car_fleet_manager1.model.AppUserDto;

import java.util.List;



public interface UserService {
//    void delete(long id);
    List<AppUser> findAll();
    AppUser findById(Long id);
    AppUser findByName(String name);
    AppUser save(AppUserDto user);
    AppUser softDelete(Long id);
    AppUser update(AppUserDto user);
}
