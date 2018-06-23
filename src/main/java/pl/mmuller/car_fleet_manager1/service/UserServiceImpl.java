package pl.mmuller.car_fleet_manager1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.mmuller.car_fleet_manager1.dao.AppUserDao;
import pl.mmuller.car_fleet_manager1.model.AppUser;
import pl.mmuller.car_fleet_manager1.model.AppUserDto;

import java.time.LocalDate;
import java.util.*;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {
    @Autowired
    private AppUserDao appUserDao;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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

    @Override
    public List<AppUser> findAll() {
        List<AppUser> list = new ArrayList<>();
        appUserDao.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public AppUser findById(Long id){
        return appUserDao.findById(id).get();
    }

    @Override
    public AppUser findByName(String name) {
        return appUserDao.findByName(name);
    }

    @Override
    public AppUser save(AppUserDto user) {
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

    @Override
    public AppUser softDelete(Long id){
        AppUser user = appUserDao.findById(id).get();
        user.setDeletion(LocalDate.now());
        user.setRole("DEL");
        appUserDao.save(user);
        return user;
    }

/*    @Override
    public AppUser update(AppUserDto user){

        AppUser newUser = appUserDao.findById(user.getId()).get();
        if(user.getName()!=null) newUser.setName(user.getName());
        if(user.getFirstName()!=null)newUser.setFirstName(user.getFirstName());
        if(user.getSurname()!=null)newUser.setSurname(user.getSurname());
        if(user.getEmail()!=null)newUser.setEmail(user.getEmail());
//      //  if(user.)newUser.setCreation(user.getCreation());
        newUser.setModification(LocalDate.now());
        if(user.getDeletion()!=null)newUser.setDeletion(user.getDeletion());
        if(user.getPassword()!=null) {
            newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        if(user.getRole()!=null) newUser.setRole(user.getRole());
        return appUserDao.save(newUser);
    }
*/

    @Override
    public AppUser update(AppUserDto user){

        AppUser newUser = appUserDao.findById(user.getId()).get();
        Arrays.stream(user.getClass().getFields()).forEach(field -> {
            field.setAccessible(true);
            Object f= null;
            try {
                f = field.get(user);
                System.out.println("yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy"+f);;
                if(f!=null) System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"+field.get(newUser));;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return appUserDao.save(newUser);
    }
}
