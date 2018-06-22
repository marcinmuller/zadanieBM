package pl.mmuller.car_fleet_manager1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Setter
@Getter
public class AppUser {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String surname;
    private String email;
    private LocalDate creation;
    private LocalDate modification;
    private LocalDate deletion;
    @JsonIgnore
    private String password;

//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinTable(
//            name="USER_ROLES",
//            joinColumns ={@JoinColumn(name = "USER_ID",referencedColumnName = "ID")},
//            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")}
//    )
//    private Set<Role> roles;
    private String role;
}
