package bg.softuni.autoTraderExperience.services;

import bg.softuni.autoTraderExperience.exceptions.UserAlreadyExistException;
import bg.softuni.autoTraderExperience.models.binding.UserRegisterBindingModel;
import bg.softuni.autoTraderExperience.models.entities.Role;
import bg.softuni.autoTraderExperience.models.entities.RoleEnum;
import bg.softuni.autoTraderExperience.models.entities.User;
import bg.softuni.autoTraderExperience.repositoris.RoleRepository;
import bg.softuni.autoTraderExperience.repositoris.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserDetailsService userDetailsService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository,
                       UserDetailsService userDetailsService, ModelMapper modelMapper,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userDetailsService = userDetailsService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(UserRegisterBindingModel userRegisterBindingModel) {

        Optional<User> optUser = userRepository.findByEmail(userRegisterBindingModel.getEmail());
        if (optUser.isPresent()) {
            throw new UserAlreadyExistException("Submit another email!");
        }

        User user = modelMapper.map(userRegisterBindingModel, User.class);
        user.setPassword(passwordEncoder.encode(userRegisterBindingModel.getPassword()));
        user.setCreated(LocalDate.now());
        user.setActive(true);

        Role admin = roleRepository.findByName(RoleEnum.ADMIN);
        Role regular = roleRepository.findByName(RoleEnum.USER);

        if (userRepository.count() == 0) {
            user.setRoles(List.of(regular, admin));
        } else {
            user.setRoles(List.of(regular));
        }
        userRepository.save(user);
        login(user.getEmail());

    }

    public void login(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
                userDetails.getAuthorities());

        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);

    }

    public void createUserIfNotExist(String email) {
        if (userRepository.findByEmail(email).isEmpty()) {

            User fbUser = new User("unknown", "unknown", email, "");
            fbUser.setRoles(List.of());
            fbUser.setCreated(LocalDate.now());
            userRepository.save(fbUser);
        }
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
