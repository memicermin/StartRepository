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

    public static Finder<Long, Brand> find = new Finder<>(Brand.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long id;


    @Column(name = "brand")
    private String brand;


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
