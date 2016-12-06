package models.products;

import com.avaje.ebean.Model;
import models.Brand;

import javax.persistence.*;

/**
 * Created by Enver on 12/6/2016.
 */
@Entity
@Table(name = "car_parts")
public class CarParts extends Model{


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long id;


    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    @Column(name = "brand")
    private Brand brand;
}
