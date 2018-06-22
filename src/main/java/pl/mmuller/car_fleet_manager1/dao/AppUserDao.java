package pl.mmuller.car_fleet_manager1.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.mmuller.car_fleet_manager1.model.AppUser;

@Repository
public interface AppUserDao extends CrudRepository<AppUser, Long> {
    AppUser findByName(String username);
}
