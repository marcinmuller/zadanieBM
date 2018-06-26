package pl.mmuller.car_fleet_manager1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.mmuller.car_fleet_manager1.model.*;
import pl.mmuller.car_fleet_manager1.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class Controller {
    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add-user")
    public AppUser saveUser(@RequestBody AppUserDto user) throws Exception{
        return userService.saveUser(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public AppUser softDeleteUser(@PathVariable("id") Long id) throws Exception{
        return userService.softDeleteUser(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add-car")
    public Car saveCar(@RequestBody CarDto car){
        return userService.saveCar(car);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add-ride")
    public Ride saveRide(@RequestBody RideDto ride){
        return userService.saveRide(ride);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public List<AppUser> listUser(){
        return userService.findAllUsers();
    }

    @GetMapping("/vehicles")
    public List<Car> listCar(HttpServletRequest request) throws Exception{
        if(request.isUserInRole("ADMIN")) {
            return userService.findAllCars();
        }else if(request.isUserInRole("USER")) {
            return userService.findUsersCars(request.getUserPrincipal().getName());
        }else{
            throw new Exception("bad ROLE") ;
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/bind-car-and-user")
    public void assignUserToCar(@RequestBody Dto dto) throws Exception{
        userService.assignUserToCar(dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users-of-vehicle/{id}")
    public List<AppUser> carUsersList(@PathVariable("id") Long id) throws Exception{
        return userService.carUsersList(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/rides-of-car/{id}")
    public List<Ride> carsRides(@PathVariable("id") Long id)throws Exception{
        return userService.carsRides(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/rides-of-user/{id}")
    public List<Ride> usersRides(@PathVariable("id") Long id) throws Exception{
        return userService.usersRides(id);
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/rides")
    public List<Ride> lastRides(Authentication authentication){
        return userService.lastRides(authentication);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/bind-ride-and-car")
    public void assignRideToCar(@RequestBody Dto dto) throws Exception{
        userService.assignRideToCar(dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/bind-ride-and-user")
    public void assignRideToUser(@RequestBody Dto dto) throws Exception{
        userService.assignUserToRide(dto);
    }
}

