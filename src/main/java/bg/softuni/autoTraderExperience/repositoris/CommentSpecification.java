package bg.softuni.autoTraderExperience.repositoris;

import bg.softuni.autoTraderExperience.models.entities.Comment;
import bg.softuni.autoTraderExperience.models.views.CommentSearchModel;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class CommentSpecification implements Specification<Comment> {

    private final CommentSearchModel commentSearchModel;

    public CommentSpecification(CommentSearchModel commentSearchModel) {
        this.commentSearchModel = commentSearchModel;
    }

    @Override
    public Predicate toPredicate(Root<Comment> root,
                                 CriteriaQuery<?> query,
                                 CriteriaBuilder cb) {

        Predicate p = cb.conjunction();

        if (commentSearchModel.getTraderName() != null && !commentSearchModel.getTraderName().isEmpty()) {
            p.getExpressions()
                    .add(cb.and(cb.equal(root.join("autoTrader").get("traderName")
                            , commentSearchModel.getTraderName()))
                    );
        }

        if (commentSearchModel.getTitle() != null && !commentSearchModel.getTitle().isEmpty()) {
            p.getExpressions()
                    .add(cb.and(cb.equal(root.get("title")
                            , commentSearchModel.getTitle()))
                    );
        }

        if (commentSearchModel.getCity() != null && !commentSearchModel.getCity().isEmpty()) {
            p.getExpressions()
                    .add(cb.and(cb.equal(root.join("autoTrader").join("location").get("city")
                            , commentSearchModel.getCity()))
                    );
        }

        return p;
    }
}
