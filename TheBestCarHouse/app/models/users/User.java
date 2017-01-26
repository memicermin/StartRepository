package models.users;

import com.avaje.ebean.Model;
import helpers.DateTimeHelper;
import helpers.HAT36N579;
import helpers.MD5Hash;
import models.users.help_user_models.UserForLogin;
import models.users.help_user_models.UserForRegister;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

/**
 * Created by Enver on 1/22/2017.
 */
@Entity
@Table(name = "user")
public class User extends Model {

    public static Model.Finder<Long, User> find = new Finder<>(User.class);

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
    private Integer premiumUser;

    @Column(name = "guest")
    private int guest;

    @Column(name = "token")
    private String token;

    /**
     * Constructor
     */
    public User() {
    }

    public Integer getPremiumUser() {
        return premiumUser;
    }

    public void setPremiumUser(Integer premiumUser) {
        this.premiumUser = premiumUser;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
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

    public int getGuest() {
        return guest;
    }

    public void setGuest(int guest) {
        this.guest = guest;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public static User findByEmail(String email) {
        return find.where().eq("email", email).findUnique();
    }

    public static User findById(Long id) {
        return find.byId(id);
    }

    public static User createNewUser(UserForRegister userForRegister) {
        try {
            User user = new User();

            user.username = userForRegister.username;
            user.email = userForRegister.email;
            user.password = MD5Hash.getEncriptedPasswordMD5(userForRegister.password);
            user.firstName = userForRegister.firsName;
            user.lastName = userForRegister.lastName;
            user.birthDate = userForRegister.birthDate;
            user.phoneNumber = userForRegister.phoneNumber;
            user.gender = userForRegister.gender;
            user.location = userForRegister.location;

            user.creationDate = DateTimeHelper.getCurrentDateFormated(DateTimeHelper.DEFAULT_FORMAT);
            user.updateDate = "0";
            user.verification = 0;
            user.userLevel = 0;
            user.loginCount = 0;
            user.premiumUser = 0;
            user.guest = 0;
            user.token = HAT36N579.getHat36(UUID.randomUUID().toString());

            user.save();

            return user;
        } catch (PersistenceException e) {
            return null;
        }

    }

    public static User getUserForLogin(UserForLogin userForLogin) {
        User user = find.where().eq("email", userForLogin.getEmail()).findUnique();
        if (user == null) {
            return null;
        }
        if (user.password.equals(MD5Hash.getEncriptedPasswordMD5(userForLogin.getPassword()))) {
            return user;
        }
        return null;
    }

    public static List<User> getInterlopers(){
        return find.where().lt("user_level", -5).findList();
    }
}