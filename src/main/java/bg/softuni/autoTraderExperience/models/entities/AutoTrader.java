package bg.softuni.autoTraderExperience.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "auto_traders")
public class AutoTrader extends BaseEntity {

    @Column(name = "trader_name", nullable = false)
    private String traderName;

    @Column()
    private int rating;

    @OneToOne(cascade = CascadeType.ALL)
    private Location location;

    public AutoTrader() {
    }

    public String getTraderName() {
        return traderName;
    }

    public void setTraderName(String traderName) {
        this.traderName = traderName;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
