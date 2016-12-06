package models.products;

import com.avaje.ebean.Model;
import models.Brand;
import models.Price;

import javax.persistence.*;

/**
 * Created by Enver on 12/6/2016.
 */
@Entity
@Table(name = "car")
public class Car extends Model{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long id;

    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    @Column(name = "brand")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "price_id", referencedColumnName = "id")
    @Column(name = "price")
    private Price price;

    private String type;

    private Integer year;

    private String bodyType;

    private String color;

    private Integer meileage;

    private String motorVolume;

    private Integer motorPower;

    private String typeOfFuel;

    private String transmission;

    @Column(length = 1500)
    private String details;

}
