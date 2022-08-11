package bg.softuni.autoTraderExperience.repositoris;

import bg.softuni.autoTraderExperience.models.entities.AutoTrader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TraderRepository extends JpaRepository<AutoTrader, Long> {
}
