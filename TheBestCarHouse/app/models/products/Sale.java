package models.products;

import com.avaje.ebean.Model;
import models.help_models.*;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Enver on 2/22/2017.
 */
@Entity
@Table(name = "sale")
public class Sale extends Model {

    public static final Integer CARS = 1;
    public static final Integer CAR_PARTS = 2;
    public static final Integer TIRES = 3;

    public static Model.Finder<Long, Sale> find = new Finder<>(Sale.class);

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

    @Column(name = "description", length = 1500)
    private String description;

    @Column(name = "available", length = 1)
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

    //GET METHODS
    public static Sale getSaleById(Long id){
        return find.byId(id);
    }

    public static boolean hasImage(Long id){
        if(Image.find.where().eq("car_id", id).findList().size() > 0){
            return true;
        }
        return false;
    }

    public Image getFirstImage(Long id){
        List<Image> images = Image.find.where().eq("car_id", id).findList();
        if(images != null){
            return images.get(0);
        }else{
            return  null;
        }
    }

    public List<Image> getOtherImagesForCar(Long id){
        List<Image> images = Image.find.where().eq("car_id", id).findList();
        if(images.size() == 1){
            return images;
        }else{
            images.remove(0);
            return images;
        }
    }

    public static List<Sale> getAllCarsForSale() {
        return find.where().eq("part_of_sale", CARS).findList();
    }

    public static boolean tiresHasImage(Long id){
        if(Image.find.where().eq("car_tires", id).findList().size() > 0){
            return true;
        }
        return false;
    }

    public Image getFirstTiresImage(Long id){
        List<Image> images = Image.find.where().eq("car_tires", id).findList();
        if(images != null){
            return images.get(0);
        }else{
            return  null;
        }
    }

    public List<Image> getOtherImagesforTires(Long id){
        List<Image> images = Image.find.where().eq("car_tires", id).findList();
        if(images.size() == 1){
            return images;
        }else{
            images.remove(0);
            return images;
        }
    }

    public static List<Sale> getAllTiresForSale() {
        return find.where().eq("part_of_sale", TIRES).findList();
    }

}
