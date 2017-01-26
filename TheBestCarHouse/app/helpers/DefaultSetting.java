package helpers;

import models.users.User;
import org.joda.time.DateTime;

import java.util.UUID;

/**
 * Created by Enver on 1/22/2017.
 */
public class DefaultSetting {

public static void setdefaultSettings(){
    User u1 = new User();

    u1.setUsername("enver");
    u1.setEmail("enver.memic80@gmail.com");
    u1.setPassword(MD5Hash.getEncriptedPasswordMD5("Enver123."));
    u1.setFirstName("Enver");
    u1.setLastName("Memic");
    u1.setBirthDate("02.10.1980");
    u1.setGender(1);
    u1.setLocation("Sarajevo");
    u1.setPhoneNumber("000-000-000");

    u1.setCreationDate(DateTimeHelper.getCurrentDateFormated(DateTimeHelper.DEFAULT_FORMAT));
    u1.setUpdateDate("0");
    u1.setVerification(1);
    u1.setUserLevel(1);
    u1.setLoginCount(0);
    u1.setPremiumUser(0);
    u1.setGuest(-1);
    u1.setToken(UUID.randomUUID().toString());
    u1.save();
    }
}
