package bg.softuni.autoTraderExperience.models.views;

public class CommentSearchModel {

    private String title;
    private String description;
    private String pictureUrl;
    private String traderName;
    private String city;
    private String address;

    public CommentSearchModel() {
    }

    public CommentSearchModel(String title, String description,
                              String pictureUrl, String traderName,
                              String city, String address) {
        this.title = title;
        this.description = description;
        this.pictureUrl = pictureUrl;
        this.traderName = traderName;
        this.city = city;
        this.address = address;
    }

    public String getTraderName() {
        return traderName;
    }

    public CommentSearchModel setTraderName(String traderName) {
        this.traderName = traderName;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public CommentSearchModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getCity() {
        return city;
    }

    public CommentSearchModel setCity(String city) {
        this.city = city;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getAddress() {
        return address;
    }

    public CommentSearchModel setAddress(String address) {
        this.address = address;
        return this;
    }

    public boolean isEmpty() {
        return this.traderName == null || traderName.isBlank()
                && this.title == null || this.title.isBlank()
                && this.city == null || this.city.isBlank();
    }
}
