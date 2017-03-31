package models.help_models;

import com.avaje.ebean.Model;
import models.products.Sale;
import resources.FieldNames;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Enver on 1/22/2017.
 */

@Entity
@Table(name = "brand")
public class Brand extends Model {

    public static final Integer CAR_BRAND = 1;
    public static final Integer TIRES = 3;


    public static Finder<Long, Brand> find = new Finder<>(Brand.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    @Column(name = "image")
    public Image image;

    @Column(name = "brand")
    private String brand;

    @Column(name = "part_of_brand")
    private Integer partOfBrand;


    public Brand(String brand, Integer partOfBrand) {
        this.brand = brand;
        this.partOfBrand = partOfBrand;

    }

    public Brand(String brand, Integer partOfBrand, Image image) {
        this.brand = brand;
        this.partOfBrand = partOfBrand;
        this.image = image;

    }

    public Brand(String brand) {
        this.brand = brand;
    }

    public Brand() {
    }

    public Long getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getPartOfBrand() {
        return partOfBrand;
    }

    public void setPartOfBrand(Integer partOfBrand) {
        this.partOfBrand = partOfBrand;
    }

    public static Brand findBrandById(Long id){
        return find.byId(id);
    }

    public static Brand findBrandByName(String name){
        return find.where().eq("name", name).findUnique();

    }

    public static List<Brand> getAllBrands() {
        return find.all();
    }

    public static List<Brand> getCarBrands(){
        return find.where().eq(FieldNames.PART_OF_BRAND, CAR_BRAND).findList();
    }

    public static List<Brand> getCarTiresBrands() {
        return find.where().eq(FieldNames.PART_OF_BRAND, TIRES).findList();
    }

    public static boolean isActiveCarBrand(Brand brand){
//       List<Car> allCars = Car.find.all();
        List<Sale> sales = Sale.find.where().isNotNull("car_id").findList();
        boolean existBrand = false;
//        for (Car car : allCars){
//            if(car.getBrand().getId() == brand.getId()){
//                existBrand = true;
//            }
//        }
        for (Sale sale : sales){
            if(sale.getCar().getBrand().getId() == brand.getId()){
                existBrand = true;
            }
        }
        return existBrand;
    }

    public static boolean isActiveTiresBrand(Brand brand){
        List<CarTires> allCarTires = CarTires.find.all();
        List<Sale> sales = Sale.find.where().isNotNull("tires_id").findList();
        boolean existBrand = false;
//        for (CarTires carTires : allCarTires){
//            if(carTires.getBrand().getId() == brand.getId()){
//                existBrand = true;
//            }
//        }
//        return existBrand;
        for (Sale sale : sales){
            if(sale.getTires().getBrand().getId() == brand.getId()){
                existBrand = true;
            }
        }
        return existBrand;
    }

}
