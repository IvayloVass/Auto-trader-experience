package bg.softuni.autoTraderExperience.repositoris;

import bg.softuni.autoTraderExperience.models.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
