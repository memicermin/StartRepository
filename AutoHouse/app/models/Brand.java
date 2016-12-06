package models;

import com.avaje.ebean.Model;

import javax.persistence.*;


/**
 * Created by Enver on 12/5/2016.
 */
@Entity
@Table(name="brand")
public class Brand extends Model{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long id;


    @Column(name = "brand")
    private String brand;



}
