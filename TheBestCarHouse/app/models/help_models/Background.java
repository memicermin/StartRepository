package models.help_models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Enver on 3/6/2017.
 */
@Entity
@Table(name = "background")
public class Background extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private Integer active;

    @OneToMany
    public List<Image> backgrounds;

    public Background() {
    }

    public Long getId() {
        return id;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

}
