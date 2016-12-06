package models;

import com.avaje.ebean.Model;
import models.products.Car;
import models.products.CarParts;
import models.products.CarTires;
import models.products.Product;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * Created by Enver on 12/6/2016.
 */
@Entity
@Table(name = "sale")
public class Sale extends Model{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @Column(name = "product")
    private Product product;

    @Column(name = "avilable")
    private Boolean isAvilable;

    @ManyToOne
    @JoinColumn(name = "admin_id", referencedColumnName = "id")
    @Column(name = "admin")
    private User admin;

    @Column(name = "date")
    private DateTime dateTime;





}
