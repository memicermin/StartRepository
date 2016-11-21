package models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Enver on 11/20/2016.
 */
@Entity
@Table(name = "reclaim_title")
public class ReclaimTitle extends Model{

    public static Finder<Long, ReclaimTitle> find = new Finder<>(ReclaimTitle.class);



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    public ReclaimTitle(String title) {
        this.title = title;
    }

    public ReclaimTitle() {
    }


    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static String getFirstReclaimTitle(){
        List<ReclaimTitle> titles = find.all();
        if(titles.size() > 0){
            return titles.get(0).getTitle();
        }else{
            return " ";
        }
    }

    public static List<ReclaimTitle> getOtherTitles(){
        List<ReclaimTitle> titles = find.all();
        if(titles.size() > 0){
            titles.remove(0);
        }
        return titles;
    }
}
