package pl.mmuller.car_fleet_manager1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.mmuller.car_fleet_manager1.dao.AppUserDao;
import pl.mmuller.car_fleet_manager1.dao.CarDao;
import pl.mmuller.car_fleet_manager1.dao.RideDao;
import pl.mmuller.car_fleet_manager1.model.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service(value = "userService")
public class UserService implements UserDetailsService {
    @Autowired
    private AppUserDao appUserDao;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private CarDao carDao;
    @Autowired
    private RideDao rideDao;


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user=appUserDao.findByName(username);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(AppUser user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+user.getRole()));
        return authorities;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public AppUser saveUser(AppUserDto user) throws Exception {
        if(findUserByName(user.getName())!=null) throw new Exception("choose other name");
        AppUser newUser = new AppUser();
        newUser.setName(user.getName());
        newUser.setFirstName((user.getFirstName()));
        newUser.setSurname(user.getSurname());
        newUser.setEmail(user.getEmail());
        newUser.setCreation(LocalDate.now());
        newUser.setModification(user.getModification());
        newUser.setDeletion(user.getDeletion());
        newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        newUser.setRole(user.getRole());
        return appUserDao.save(newUser);
    }

    public AppUser softDeleteUser(Long id) throws Exception{
        AppUser user = appUserDao.findById(id).orElseThrow(()->new Exception("invalid user id"));
        user.setDeletion(LocalDate.now());
        user.setRole("DEL");
        appUserDao.save(user);
        return user;
    }

    public Car saveCar(CarDto car) {
        Car newCar = new Car();
        newCar.setBrand(car.getBrand());
        newCar.setModel(car.getModel());
        newCar.setRegistrationNumber(car.getRegistrationNumber());
        newCar.setCreation(LocalDate.now());
        newCar.setModification(car.getModification());
        return carDao.save(newCar);
    }

    public Ride saveRide(RideDto ride) {
        Ride newRide = new Ride();
        newRide.setTime(ride.getTime());
        newRide.setDistance(ride.getDistance());
        return rideDao.save(newRide);
    }

    public List<AppUser> findAllUsers() {
        List<AppUser> list = new ArrayList<>();
        appUserDao.findAll().iterator().forEachRemaining(list::add);
        return list.stream().filter(f->!f.getRole().equals("DEL")).collect(Collectors.toList());
    }

    public List<Car> findAllCars() {
        List<Car> list = new ArrayList<>();
        carDao.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    public List<Car> findUsersCars(String name){
        return findUserByName(name).getCars().stream().collect(Collectors.toList());
    }

    public void assignUserToCar(Dto dto) throws Exception {
        Car car= carDao.findById(dto.getCarId()).orElseThrow(()->new Exception("invalid car id"));
        AppUser user = appUserDao.findById(dto.getUserId()).orElseThrow(()->new Exception("invalid user id"));
        car.getUsers().add(user);
        user.getCars().add(car);
        carDao.save(car);
    }

    public List<AppUser> carUsersList(Long id) throws Exception{
        return findCarByid(id).getUsers().stream().collect(Collectors.toList());
    }

    public List<Ride> carsRides(Long carId)throws Exception{
        return findCarByid(carId).getRides().stream().collect(Collectors.toList());
    }

    public List<Ride> usersRides(Long userId) throws Exception{
        return findUserById(userId).getRides().stream().collect(Collectors.toList());
    }

    public void assignRideToCar(Dto dto) throws Exception{
        Car car = carDao.findById(dto.getCarId()).orElseThrow(()->new Exception("invalid car id"));
        Ride ride = rideDao.findById(dto.getRideId()).orElseThrow(()->new Exception("invalid ride id"));
        car.getRides().add(ride);
        ride.setCar(car);
        carDao.save(car);
    }

    private AppUser findUserById(Long id) throws Exception{
        return appUserDao.findById(id).orElseThrow(()->new Exception("invalid user id"));
    }

    private AppUser findUserByName(String name) {
        return appUserDao.findByName(name);
    }


    public List<Ride> lastRides(Authentication authentication){
        return appUserDao.findByName(authentication.getName()).getRides().stream().collect(Collectors.toList());
    }

    public Car findCarByid(Long id) throws Exception{
        return carDao.findById(id).orElseThrow(()->new Exception("invalid car id"));
    }

    public void assignUserToRide(Dto dto) throws Exception{
        AppUser user = appUserDao.findById(dto.getUserId()).orElseThrow(()->new Exception("invalid user id"));
        Ride ride = rideDao.findById(dto.getRideId()).orElseThrow(()->new Exception("invalid ride id"));
        user.getRides().add(ride);
        ride.setUser(user);
        rideDao.save(ride);
    }

}
