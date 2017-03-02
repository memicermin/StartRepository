package models.help_models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Enver on 2/24/2017.
 */

@Entity
@Table(name = "car_tires")
public class CarTires extends Model {

    public static Model.Finder<Long, CarTires> find = new Finder<>(CarTires.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    @Column(name = "brand")
    private Brand brand;

    @OneToMany
    private List<Image> images;

    @Column(name = "type", length = 3)
    private String type;

    @Column(name = "year", length = 4)
    private String year;

    @Column(name = "dimension", length = 10)
    private String dimension;

    public CarTires() {
    }

    public CarTires(Brand brand, String type, String year, String dimension) {
        this.brand = brand;
        this.type = type;
        this.year = year;
        this.dimension = dimension;
    }

    public Long getId() {
        return id;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public static CarTires getTiresById(Long id) {
        return find.byId(id);
    }

    public List<Image> getImages(Long id) {
        return Image.find.where().eq("car_tires_id", id).findList();
    }

}
