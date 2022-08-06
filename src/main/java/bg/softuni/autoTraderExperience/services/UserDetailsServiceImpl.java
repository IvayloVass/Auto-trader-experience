package bg.softuni.autoTraderExperience.services;

import bg.softuni.autoTraderExperience.models.entities.Role;
import bg.softuni.autoTraderExperience.models.entities.User;
import bg.softuni.autoTraderExperience.repositoris.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.stream.Collectors;

public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = this.userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("This user with email: " + username + " is not found!"));
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRoles()
                        .stream().map(this::map).collect(Collectors.toList())).build();

    }

    public GrantedAuthority map(Role role) {
        return new SimpleGrantedAuthority("ROLE_" + role.getName());
    }
}
