package models.help_models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Enver on 2/22/2017.
 */
@Entity
@Table(name = "price")
public class Price extends Model{

    public static Model.Finder<Long, Price> find = new Model.Finder(Price.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "current_price")
    private Float currentPrice;

    @Column(name = "old_price")
    private Float oldPrice;

    @Column(name = "action")
    private int action;

    //CONSTRUCTORS
    public Price(Float currentPrice, Float oldPrice, int action) {
        this.currentPrice = currentPrice;
        this.oldPrice = oldPrice;
        this.action = action;
    }

    public Price(Float currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Price() {
    }

    //GETTERS AND SETTERS


    public Long getId() {
        return id;
    }

    public Float getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Float currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Float getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(Float oldPrice) {
        this.oldPrice = oldPrice;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    //FIND METHODS

    public Price getById(Long id){
        return find.byId(id);
    }


    public static float getMaxPrice() {
        List<Price> allPrices = find.all();
        float maxPrice = 0;
        if(allPrices != null && allPrices.size() > 0){
            for (Price price : allPrices){
                if(price.getCurrentPrice() > maxPrice){
                    maxPrice = price.getCurrentPrice();
                }
            }
        }
        return maxPrice;
    }
}
