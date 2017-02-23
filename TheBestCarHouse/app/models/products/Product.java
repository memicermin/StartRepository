package models.products;

import com.avaje.ebean.Model;

import javax.persistence.*;

/**
 * Created by Enver on 2/22/2017.
 */

@Entity
@Table(name = "product")
public class Product extends Model {

    public static final Integer SALE = 1;
    public static final Integer RENTACAR = 2;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private Integer partOfService;

    private Price price;




}
