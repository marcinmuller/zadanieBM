package pl.mmuller.car_fleet_manager1.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.mmuller.car_fleet_manager1.dao.AppUserDao;
import pl.mmuller.car_fleet_manager1.model.AppUser;
import pl.mmuller.car_fleet_manager1.model.AppUserDto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
//        user.getRoles().forEach(role -> {
//            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
//        });
        authorities.add(new SimpleGrantedAuthority("ROLE_"+user.getRole()));
        return authorities;
    }

    public List<AppUser> findAll() {
        List<AppUser> list = new ArrayList<>();
        appUserDao.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public void delete(long id) {
        appUserDao.deleteById(id);
    }
    @Override
    public AppUser findOne(String username) {
        return appUserDao.findByName(username);
    }
    @Override
    public AppUser findById(Long id) {
        return appUserDao.findById(id).get();
    }
    @Override
    public AppUser save(AppUserDto user) {
        AppUser newUser = new AppUser();
        newUser.setName(user.getName());
        newUser.setSurname(user.getSurname());
        newUser.setEmail(user.getEmail());
        newUser.setCreation(user.getCreation());
        newUser.setModification(user.getModification());
        newUser.setDeletion(user.getDeletion());
        newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return appUserDao.save(newUser);
    }
}
