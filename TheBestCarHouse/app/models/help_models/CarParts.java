package models.help_models;

import com.avaje.ebean.Model;

import javax.persistence.*;

/**
 * Created by Enver on 2/24/2017.
 */
@Entity
@Table(name = "car_parts")
public class CarParts extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    private Brand brand;
    private Price price;

}
