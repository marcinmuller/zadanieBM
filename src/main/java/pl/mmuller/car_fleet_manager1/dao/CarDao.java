package pl.mmuller.car_fleet_manager1.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.mmuller.car_fleet_manager1.model.Car;

@Repository
public interface CarDao extends CrudRepository<Car, Long> {
}
