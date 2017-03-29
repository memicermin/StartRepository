package models.help_models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.List;
import java.util.Random;

/**
 * Created by Enver on 03/06/2017.
 */
@Entity
@Table(name = "reclaim_title")
public class ReclamTitle extends Model{

    public static Finder<Long, ReclamTitle> find = new Finder<>(ReclamTitle.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    public ReclamTitle(String title) {
        this.title = title;
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
        List<ReclamTitle> titles = find.all();
        if(titles.size() > 0){
            return titles.get(0).getTitle();
        }else{
            return " ";
        }
    }

    public static List<ReclamTitle> getOtherTitles(){
        List<ReclamTitle> titles = find.all();
        if(titles.size() > 0){
            titles.remove(0);
        }
        return titles;
    }

    public static List<ReclamTitle> getAllTitles(){
        return find.all();
    }

    public static String getRandomTitle(){
        Random rnd = new Random();
        List<ReclamTitle> titles = getAllTitles();
        int size = titles.size();
        if(size > 0){
            return titles.get(rnd.nextInt(size)).title;
        }else{
            return "Welcome";
        }
    }
}
