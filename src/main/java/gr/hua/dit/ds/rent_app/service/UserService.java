package gr.hua.dit.ds.rent_app.service;

import gr.hua.dit.ds.rent_app.entities.Renter;
import gr.hua.dit.ds.rent_app.entities.Role;
import gr.hua.dit.ds.rent_app.entities.User;
import gr.hua.dit.ds.rent_app.repositories.OwnerRepository;
import gr.hua.dit.ds.rent_app.repositories.RenterRepository;
import gr.hua.dit.ds.rent_app.repositories.RoleRepository;
import gr.hua.dit.ds.rent_app.repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/*Service class for managing user-related operations*/
@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private BCryptPasswordEncoder passwordEncoder;

    private RenterRepository renterRepository;

    private OwnerRepository ownerRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository,
                       BCryptPasswordEncoder passwordEncoder, RenterRepository renterRepository,
                       OwnerRepository ownerRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.renterRepository = renterRepository;
        this.ownerRepository = ownerRepository;
    }

    //Saves a new user, encodes its password and sets the default role of renter to him
    @Transactional
    public Integer saveUser(User user) {
        String passwd= user.getPassword();
        String encodedPassword = passwordEncoder.encode(passwd);
        user.setPassword(encodedPassword);

        Role role_renter = roleRepository.findByName("ROLE_RENTER")
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        Set<Role> roles = new HashSet<>();
        roles.add(role_renter);
        user.setRoles(roles);

        user = userRepository.save(user);
        Renter renter = new Renter();
        renterRepository.save(renter);
        return user.getId();
    }

    //Loads a user by his username and returns the user's details
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> opt = userRepository.findByUsername(username);
        //checks if user with this username exists; if not it throws error
        if(opt.isEmpty())
            throw new UsernameNotFoundException("User with id: " +username +" not found !");
        else {
            User user = opt.get();
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    user.getRoles()
                            .stream()
                            .map(role-> new SimpleGrantedAuthority(role.toString()))
                            .collect(Collectors.toSet())
            );
        }
    }

    //Retrieves a list of users and returns it
    @Transactional
    public Object getUsers() {
        return userRepository.findAll();
    }

    //Retrieves a specific user and returns it
    @Transactional
    public Object getUser(Integer userid) {
        return userRepository.findById(userid).get();
    }

    //Saves a new user
    @Transactional
    public void saveNewUser(User user) {
        userRepository.save(user);
    }
}
