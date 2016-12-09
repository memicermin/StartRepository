package models.communication;

import com.avaje.ebean.Model;

import javax.persistence.*;

/**
 * Created by Enver on 12/6/2016.
 */
@Entity
@Table(name = "comment")
public class Comment extends Model{


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long id;
}
