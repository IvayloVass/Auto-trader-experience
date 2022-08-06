package bg.softuni.autoTraderExperience.repositoris;

import bg.softuni.autoTraderExperience.models.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
