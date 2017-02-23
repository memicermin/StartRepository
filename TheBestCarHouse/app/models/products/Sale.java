package models.products;

import com.avaje.ebean.Model;

import javax.persistence.*;

/**
 * Created by Enver on 2/22/2017.
 */
@Entity
@Table(name = "sale")
public class Sale extends Model {

    public static final Integer CARS = 1;
    public static final Integer CAR_PARTS = 2;
    public static final Integer WHELLS = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private Integer partOfSale;



}
