package bg.softuni.autoTraderExperience.web.utils;

import bg.softuni.autoTraderExperience.models.entities.*;
import bg.softuni.autoTraderExperience.repositoris.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class TestUtil {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CommentRepository commentRepository;
    private final TraderRepository traderRepository;
    private final LocationRepository locationRepository;
    private final PasswordEncoder passwordEncoder;

    public TestUtil(UserRepository userRepository, RoleRepository roleRepository,
                    CommentRepository commentRepository, TraderRepository traderRepository,
                    LocationRepository locationRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.commentRepository = commentRepository;
        this.traderRepository = traderRepository;
        this.locationRepository = locationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void initRoles() {
        if (roleRepository.count() == 0) {
            Role admin = new Role();
            admin.setName(RoleEnum.ADMIN);
            Role regular = new Role();
            regular.setName(RoleEnum.USER);
            roleRepository.saveAll(List.of(admin, regular));
        }
    }

    public Location createLocation() {
        Location location = new Location();
        location.setCity("Pernik");
        location.setAddress("kv.Moshino");
        return locationRepository.save(location);
    }

    public AutoTrader createAutoTrader(Location location) {
        AutoTrader trader = new AutoTrader();
        trader.setTraderName("Nikifor");
        trader.setLocation(location);
        return traderRepository.save(trader);
    }

    public User initAdminUser(String email) {
        initRoles();
        User adminUser = new User();
        adminUser.setRoles(roleRepository.findAll());
        adminUser.setCreated(LocalDate.now());
        adminUser.setFirstName("Mitko");
        adminUser.setLastName("Mitkov");
        adminUser.setEmail(email);
        adminUser.setPassword(passwordEncoder.encode("mitko"));
        adminUser.setActive(true);
        return userRepository.save(adminUser);
    }

    public Comment createComment(User user, AutoTrader trader) {
        Comment comment = new Comment();
        comment.setCommentCreated(LocalDate.now());
        comment.setTitle("Test Tile");
        comment.setDescription("Some test description");
        comment.setUser(user);
        comment.setAutoTrader(trader);
        return commentRepository.save(comment);
    }

}
