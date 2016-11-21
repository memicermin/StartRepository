package models;

import com.avaje.ebean.Model;

import javax.persistence.*;

/**
 * Created by Enver on 11/8/2016.
 */

@Entity
@Table(name = "service")
public class Service extends Model {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", insertable = false)
    private Long id;

}
