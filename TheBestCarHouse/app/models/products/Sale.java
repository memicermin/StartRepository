package models.products;

import com.avaje.ebean.Model;
import models.help_models.Car;
import models.help_models.CarParts;
import models.help_models.CarTires;
import models.help_models.Price;

import javax.persistence.*;

/**
 * Created by Enver on 2/22/2017.
 */
@Entity
@Table(name = "sale")
public class Sale extends Model {

    public static final Integer CARS = 1;
    public static final Integer CAR_PARTS = 2;
    public static final Integer TIRES = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "part_of_sale")
    private Integer partOfSale;

    @ManyToOne
    @JoinColumn(name = "car_id", columnDefinition = "id")
    @Column(name = "car")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "car_parts_id", columnDefinition = "id")
    @Column(name = "car_parts")
    private CarParts carParts;

    @ManyToOne
    @JoinColumn(name = "tires_id", columnDefinition = "id")
    @Column(name = "tires")
    private CarTires tires;

    @ManyToOne
    @JoinColumn(name = "price_id", columnDefinition = "id")
    @Column(name = "price")
    private Price price;

    @Column(name = "description")
    private String description;

    @Column(name = "available")
    private Integer available;

    public Sale() {
    }

    public Long getId() {
        return id;
    }

    public Integer getPartOfSale() {
        return partOfSale;
    }

    public void setPartOfSale(Integer partOfSale) {
        this.partOfSale = partOfSale;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public CarParts getCarParts() {
        return carParts;
    }

    public void setCarParts(CarParts carParts) {
        this.carParts = carParts;
    }

    public CarTires getTires() {
        return tires;
    }

    public void setTires(CarTires tires) {
        this.tires = tires;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }
}
