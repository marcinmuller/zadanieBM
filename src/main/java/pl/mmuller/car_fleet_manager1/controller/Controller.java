package pl.mmuller.car_fleet_manager1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.mmuller.car_fleet_manager1.model.AppUser;
import pl.mmuller.car_fleet_manager1.model.AppUserDto;
import pl.mmuller.car_fleet_manager1.service.UserService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class Controller {
    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add-user")
    public AppUser saveUser(@RequestBody AppUserDto user){
        return userService.save(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public List<AppUser> listUser(){
        return userService.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/id/{id}")
    public AppUser findUserById(@PathVariable("id") Long id){
        return userService.findById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/name/{name}")
    public AppUser findUserByName(@PathVariable("name") String name){
        return userService.findByName(name);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public AppUser softDeleteUser(@PathVariable("id") Long id){
        return userService.softDelete(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update")
    public AppUser updateUser(@RequestBody AppUserDto user){
        return userService.update(user);
    }





//    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
//    @GetMapping("/user/{id}")
//    public AppUser getOne(@PathVariable(value = "id") Long id){
//        return userService.findById(id);
//    }
}

