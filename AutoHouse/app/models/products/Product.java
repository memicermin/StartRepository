package models.products;

import com.avaje.ebean.Model;
import models.Price;

import javax.persistence.*;

/**
 * Created by Enver on 12/6/2016.
 */
@Entity
@Table(name = "product")
public class Product extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long id;

    @ManyToOne
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    @Column(name = "car")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "car_parts_id", referencedColumnName = "id")
    @Column(name = "car_parts")
    private CarParts carParts;

    @ManyToOne
    @JoinColumn(name = "car_tires_id", referencedColumnName = "id")
    @Column(name = "car_tires")
    private CarTires carTires;

    @ManyToOne
    @JoinColumn(name = "price_id", referencedColumnName = "id")
    @Column(name = "price")
    private Price price;

}
