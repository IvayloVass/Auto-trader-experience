package bg.softuni.autoTraderExperience.services;

import bg.softuni.autoTraderExperience.models.entities.Role;
import bg.softuni.autoTraderExperience.models.entities.RoleEnum;
import bg.softuni.autoTraderExperience.models.entities.User;
import bg.softuni.autoTraderExperience.repositoris.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceTest {

    private UserDetailsServiceImpl userDetailsServiceTest;

    @Mock
    UserRepository mockUserRepository;

    @BeforeEach
    public void setUp() {
        userDetailsServiceTest = new UserDetailsServiceImpl(mockUserRepository);
    }

    @Test
    void testUserNotFound() {
        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsServiceTest.loadUserByUsername("noSuchEmail@example.com");
        });
    }

    @Test
    void testUserExists() {
        User user = new User();
        user.setFirstName("Test");
        user.setLastName("Testov");
        user.setEmail("tester@example.com");
        user.setPassword("testPass");
        user.setCreated(LocalDate.now());
        Role regular = new Role();
        regular.setName(RoleEnum.USER);
        Role admin = new Role();
        admin.setName(RoleEnum.ADMIN);
        user.setRoles(List.of(regular, admin));
        user.setActive(true);

        Mockito.when(mockUserRepository.findByEmail("tester@example.com"))
                .thenReturn(Optional.of(user));

        UserDetails userDetails = userDetailsServiceTest.loadUserByUsername("tester@example.com");
        Assertions.assertEquals(user.getEmail(), userDetails.getUsername());
        Assertions.assertEquals(2, userDetails.getAuthorities().size());
        Assertions.assertEquals(user.getPassword(), userDetails.getPassword());

        List<String> userDetailsRoles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority).toList();

        Assertions.assertTrue(userDetailsRoles.contains("ROLE_ADMIN"));
        Assertions.assertTrue(userDetailsRoles.contains("ROLE_USER"));

    }

}
