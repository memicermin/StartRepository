package models.users;

import com.avaje.ebean.Model;
import helpers.DateTimeHelper;
import helpers.HAT36N579;
import helpers.MD5Hash;
import models.users.help_user_models.UserForLogin;
import models.users.help_user_models.UserForRegister;
import notifiers.Emails;
import javax.persistence.*;
import java.util.UUID;

/**
 * Created by Enver on 1/22/2017.
 */
@Entity
@Table(name = "user")
public class User extends Model {

    public static final int BLOCKED = 0;
    public static final int INACTIVE = 1;
    public static final int ACTIVE = 2;
    public static final int ADMIN = 3;
    public static final int MADMIN = 4;

    public static final int MAX_NOTES_LENGTH = 25000;
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

    @Column(name = "last_login")
    private String lastLogin;

    @Column(name = "token")
    private String token;

    @Column(name = "notes", length = MAX_NOTES_LENGTH)
    private String notes;
//
//    @Column(name = "active")
//    private Integer active;
//
//    @Column(name = "verification")
//    private Integer verification;

    @Column(name = "user_type")
    private Integer userType;

//    @Column(name = "guest")
//    private int guest;

    @Column(name = "premium_user")
    private Integer premiumUser;

    @Column(name = "login_count")
    private Integer loginCount;

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

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }
//
//    public Integer getVerification() {
//        return verification;
//    }
//
//    public void setVerification(Integer verification) {
//        this.verification = verification;
//    }


    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }
//
//    public int getGuest() {
//        return guest;
//    }
//
//    public void setGuest(int guest) {
//        this.guest = guest;
//    }
//
//    public Integer getActive() {
//        return active;
//    }
//
//    public void setActive(Integer active) {
//        this.active = active;
//    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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
//            user.verification = 0;
//            user.userLevel = 0;
            user.loginCount = 0;
            user.premiumUser = 0;
//            user.guest = 0;
//            user.active = 0;
            user.token = HAT36N579.getHat36(UUID.randomUUID().toString());
            user.notes = "Registered: " + DateTimeHelper.getCurrentDateFormated(DateTimeHelper.DEFAULT_FORMAT);
            user.lastLogin = "0";
            user.save();
            Emails.confirmToken(user.getEmail(), user.getToken());
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

    public static String userUpdateToString(Long id) {
        User user = findById(id);
        return "User{" +
                "id=" + id +
                ", username='" + user.username + '\'' +
                ", email='" + user.email + '\'' +
                ", firstName='" + user.firstName + '\'' +
                ", lastName='" + user.lastName + '\'' +
                ", birthDate='" + user.birthDate + '\'' +
                ", gender=" + user.gender +
                ", location='" + user.location + '\'' +
                ", phoneNumber='" + user.phoneNumber + '\'' +
                ", creationDate='" + user.creationDate + '\'' +
                ", updateDate='" + user.updateDate + '\'' +
                '}';
    }

    public static boolean adminUpdateNotes(Long id, String message) {
        User user = findById(id);
        try {
            String exData = "__________" + message + "User{" +
                    "id=" + id +
//                    ", verification=" + user.verification +
                    ", userType=" + user.userType +
                    ", loginCount=" + user.loginCount +
                    ", premiumUser=" + user.premiumUser +
//                    ", guest=" + user.guest +
//                    ", active=" + user.active +
                    ", token='" + user.token + '\'' +
                    '}' + " " + DateTimeHelper.getCurrentDateFormated(DateTimeHelper.DEFAULT_FORMAT);
            String data = user.notes + exData;
            if (data.length() < MAX_NOTES_LENGTH) {
                user.setNotes(data);
            } else {
                user.setNotes(data.substring(data.length() - MAX_NOTES_LENGTH, data.length()));
            }
            user.update();
            return true;
        } catch (Exception e) {
            System.err.print(e.toString());
            return false;
        }
    }
}

/**
 * eq(...) = equals
 * ne(...) = not equals
 * ieq(...) = case insensitve equals
 * between(...) = between
 * gt(...) = greater than
 * ge(...) = greater than or equals
 * lt(...) = less than or equals
 * le(...) = less than or equals
 * isNull(...) = is null
 * isNotNull(...) = is not null
 * like(...) = like
 * startsWith(...) = string starts with
 * endswith(...) = string ends with
 * contains(...) = string conains
 * in(...) = in a subquery, collection or array
 * exists(...) = at least one row exists in a subquery
 * notExists(...) = no row exists in a subquery
 */