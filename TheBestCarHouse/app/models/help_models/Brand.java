package models.help_models;

import com.avaje.ebean.Model;

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


    @Column(name = "brand")
    private String brand;

    @Column(name = "part_of_brand")
    private Integer partOfBrand;


    public Brand(String brand, Integer partOfBrand) {
        this.brand = brand;
        this.partOfBrand = partOfBrand;

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

}
