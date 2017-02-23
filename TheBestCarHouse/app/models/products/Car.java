package models.products;

import com.avaje.ebean.Model;
import models.help_models.Brand;

import javax.persistence.*;

/**
 * Created by Enver on 2/22/2017.
 */
@Entity
@Table(name = "car")
public class Car extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private Brand brand;

    private String model;

    public Car() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
