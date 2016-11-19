package models;

import com.avaje.ebean.Model;

import javax.persistence.*;

/**
 * Created by Enver on 11/7/2016.
 */
@Entity
@Table(name = "rent_a_car")
public class RentACar extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long id;



}
