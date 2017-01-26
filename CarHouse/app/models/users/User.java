package models.users;

import com.avaje.ebean.Model;
import helpers.DateTimeHelper;
import helpers.MD5Hash;
import helpers.SessionHelper;
import models.users.help_models.UserForRegister;
import play.Logger;
import play.mvc.Http;
import resources.patterns_form.DateTimePatterns;
import javax.persistence.*;
import java.util.UUID;

/**
 * Created by Enver on 12/12/2016.
 */

@Entity
@Table(name = "user")
public class User extends Model {

    public static Finder<Long, User> find = new Finder<>(User.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
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

    @Column(name = "create_date", updatable = false)
    private String creationDate;

    @Column(name = "update_date")
    private String updateDate;

    @Column(name = "verification")
    private Integer verification;

    @Column(name = "user_level")
    private Integer userLevel;

    @Column(name = "login_count")
    private Integer loginCount;

    @Column(name = "premium_user")
    private int premiumUser;

    @Column(name = "token")
    private String token;

    /**
     * Constructor
     */
    public User() {
    }

    /**
     * Getters and Setters
     */
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate() {
        creationDate = DateTimeHelper.getCurrentFormattedDateTime(DateTimePatterns.DAY_DATE_TIME);
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getVerification() {
        return verification;
    }

    public void setVerification(Integer verification) {
        this.verification = verification;
    }

    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public int getPremiumUser() {
        return premiumUser;
    }

    public void setPremiumUser(int premiumUser) {
        this.premiumUser = premiumUser;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


     // METHODS


    public static User findByEmail(String email) {
        return find.where().eq("email", email).findUnique();
    }

    public static User findById(Long id) {
        return find.byId(id);
    }

    public static User currentUser() {
        User user = SessionHelper.getCurrentUser(Http.Context.current());
        return user;
    }

    public static User createNewUser(UserForRegister userForRegister) {
        if (userForRegister != null) {
            try {
                User user = new User();
                user.userLevel = -1;
                user.loginCount = -1;
                user.verification = -1;
                user.premiumUser = -1;
                user.token = UUID.randomUUID().toString();
                user.username = userForRegister.getUsername();
                user.email = userForRegister.getEmail();
                user.password = MD5Hash.getEncriptedPasswordMD5(userForRegister.getPassword());
                user.firstName = userForRegister.getFirstName();
                user.lastName = userForRegister.getLastName();
                user.birthDate = userForRegister.getBirthDate();
                user.phoneNumber = userForRegister.getPhoneNumber();
                user.gender = userForRegister.getGender();
                user.location = userForRegister.getLocation();
                user.setCreationDate();
                user.save();
                return user;
            } catch (PersistenceException e) {
                return null;
            }
        }
        return null;
    }
}
