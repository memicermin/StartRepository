package models.products;

import com.avaje.ebean.Model;
import models.help_models.Car;
import models.help_models.Price;

import javax.persistence.*;

/**
 * Created by Enver on 2/24/2017.
 */
@Entity
@Table(name = "rent_a_car")
public class RentACar extends Model {

    public static Model.Finder<Long, RentACar> find = new Finder<>(RentACar.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "car_id", columnDefinition = "id")
    @Column(name = "car")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "price_id", columnDefinition = "id")
    @Column(name = "price")
    private Price price;

    @Column(name = "description")
    private String description;

    @Column(name = "available")
    private Integer available;

    @Column(name = "availability_date")
    private String availabilityDate;

    @Column(name = "active_car")
    private Integer activeCar;

    public RentACar() {
    }

    public Long getId() {
        return id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
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

    public String getAvailabilityDate() {
        return availabilityDate;
    }

    public void setAvailabilityDate(String availabilityDate) {
        this.availabilityDate = availabilityDate;
    }

    public Integer getActiveCar() {
        return activeCar;
    }

    public void setActiveCar(Integer activeCar) {
        this.activeCar = activeCar;
    }

    //FIND METHODS
    public RentACar byId(Long id){
        return find.byId(id);
    }
}
