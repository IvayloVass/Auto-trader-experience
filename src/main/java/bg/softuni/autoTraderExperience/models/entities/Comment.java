package bg.softuni.autoTraderExperience.models.entities;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "comments")
public class Comment extends BaseEntity {

    @Column(columnDefinition = "TEXT", nullable = false)
    private String title;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String description;

    @Column(name = "commentCreated")
    private LocalDate commentCreated;

    @Column
    private String pictureUrl;

    @ManyToOne(cascade = CascadeType.ALL)
    private AutoTrader autoTrader;

    @ManyToOne
    private User user;

    public Comment() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCommentCreated() {
        return commentCreated;
    }

    public void setCommentCreated(LocalDate commentCreated) {
        this.commentCreated = commentCreated;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public AutoTrader getAutoTrader() {
        return autoTrader;
    }

    public void setAutoTrader(AutoTrader autoTrader) {
        this.autoTrader = autoTrader;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
