package models;

import com.avaje.ebean.Model;

import javax.persistence.*;

/**
 * Created by Enver on 12/6/2016.
 */
@Entity
@Table(name = "price")
public class Price extends Model{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long id;

    @Column(name = "price")
    private Double price;

    @Column(name = "old_price")
    private Double oldPrice;

    @Column(name = "action")
    private Boolean isAction;



}
