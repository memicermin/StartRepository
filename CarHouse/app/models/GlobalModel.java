package models;

import com.avaje.ebean.Model;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * Created by Enver on 12/12/2016.
 */
@Entity
@Table
public class GlobalModel extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private String name;

    public GlobalModel() {
    }

    public GlobalModel(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void nwModel(){
        GlobalModel gm = new GlobalModel(new DateTime().toString());
        gm.save();
    }
}
