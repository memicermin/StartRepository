package models;

import com.avaje.ebean.Model;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * Created by Enver on 12/6/2016.
 */
@Entity
@Table(name = "user")
public class User extends Model{



    public static Finder<Long, User> find = new Finder<>(User.class);


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", insertable = false)
    private Long id;

    @Column(name = "username", length = 50)
    private String username;

    @Column(name = "email", unique = true, updatable = false, length = 100)
    private String email;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "first_name", updatable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", updatable = false, length = 100)
    private String lastName;

    @Column(name = "birth_date")
    private String birthDate;

    @Column(name = "gender", length = 1)
    private Integer gender;

    @Column(name = "location", length = 150)
    private String location;

    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @Column(name = "create_date", updatable = false, columnDefinition = "datetime")
    private DateTime creationDate = new DateTime();

    @Column(name = "update_date")
    private DateTime updateDate = new DateTime();

    @Column(name = "verification")
    private Integer verification;

    @Column(name = "user_level")
    private Integer userLevel;

    private String token;


}
