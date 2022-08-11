package bg.softuni.autoTraderExperience.models.dtos;

import bg.softuni.autoTraderExperience.models.entities.AutoTrader;
import bg.softuni.autoTraderExperience.models.entities.User;

import java.time.LocalDate;

public class CommentDto {


    private String title;

    private String description;

    private LocalDate commentCreated;

    private String pictureUrl;

    private AutoTrader autoTrader;

    private User user;

    public CommentDto() {
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
