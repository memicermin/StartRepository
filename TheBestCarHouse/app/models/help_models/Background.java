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

    public static Model.Finder<Long, Background> find = new Finder<>(Background.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private Integer active;

    @Column(name = "image")
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    @OneToOne
    public Image img;

    public Background() {
    }

    public Background(Integer active, Image img) {
        if (active != 0) {
            if (Background.find.findRowCount() > 0) {
                Background oldBckg = Background.find.where().eq("active", 1).findUnique();
                oldBckg.active = 0;
                oldBckg.update();
            }
        }
        this.active = active;
        this.img = img;
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

    public static Background getBackgroundById(Long id) {
        return find.byId(id);
    }

    public static String getBackground() {
        return Background.find.where().eq("active", 1).findUnique().img.image_url;
    }

    public static boolean setBackgroundById(Long id) {
        try {
            Background oldBackground = find.where().eq("active", 1).findUnique();
            Background newBackground = find.byId(id);
            oldBackground.setActive(0);
            newBackground.setActive(1);
            oldBackground.update();
            newBackground.update();
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
