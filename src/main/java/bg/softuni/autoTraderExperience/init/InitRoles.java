package bg.softuni.autoTraderExperience.init;

import bg.softuni.autoTraderExperience.models.entities.Role;
import bg.softuni.autoTraderExperience.models.entities.RoleEnum;
import bg.softuni.autoTraderExperience.repositoris.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InitRoles implements CommandLineRunner {

    public final RoleRepository roleRepository;

    @Autowired
    public InitRoles(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        initRoles();
    }

    private void initRoles() {

        if (roleRepository.count() == 0) {
            Role admin = new Role();
            admin.setName(RoleEnum.ADMIN);
            Role regular = new Role();
            regular.setName(RoleEnum.USER);
            roleRepository.saveAll(List.of(admin, regular));
        }

    }
}
