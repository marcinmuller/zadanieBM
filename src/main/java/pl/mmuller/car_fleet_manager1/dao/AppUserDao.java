package pl.mmuller.car_fleet_manager1.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.mmuller.car_fleet_manager1.model.AppUser;

@Repository
public interface AppUserDao extends /*CrudRepository*/JpaRepository<AppUser, Long> {
//    AppUser findByUsername(String username);
    AppUser findByName(String username);
}
