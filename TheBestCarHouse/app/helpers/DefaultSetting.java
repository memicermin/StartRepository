package helpers;

import models.users.User;
import org.joda.time.DateTime;

import java.util.UUID;

/**
 * Created by Enver on 1/22/2017.
 */
public class DefaultSetting {

public static void setdefaultSettings(){
        setDefaultUsers();
    }
    
    private static void setDefaultUsers(){
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
        u1.setUserLevel(10000);
        u1.setLoginCount(0);
        u1.setPremiumUser(1000);
        u1.setGuest(-2);
        u1.setActive(1);
        u1.setToken(UUID.randomUUID().toString());
        u1.setNotes("Reg by app: " + DateTimeHelper.getCurrentDateFormated(DateTimeHelper.DEFAULT_FORMAT));
        u1.save();
        User u2 = new User();
        u2.setUsername("ermin");
        u2.setEmail("erminmemic@hotmail.com");
        u2.setPassword(MD5Hash.getEncriptedPasswordMD5("Ermin123."));
        u2.setFirstName("Ermin");
        u2.setLastName("Memic");
        u2.setBirthDate("30.01.1998");
        u2.setGender(1);
        u2.setLocation("Sarajevo");
        u2.setPhoneNumber("000-000-001");
        u2.setCreationDate(DateTimeHelper.getCurrentDateFormated(DateTimeHelper.DEFAULT_FORMAT));
        u2.setUpdateDate("0");
        u2.setVerification(0);
        u2.setUserLevel(0);
        u2.setLoginCount(0);
        u2.setPremiumUser(0);
        u2.setGuest(0);
        u2.setActive(1);
        u2.setToken(UUID.randomUUID().toString());
        u2.setNotes("Reg by app: " + DateTimeHelper.getCurrentDateFormated(DateTimeHelper.DEFAULT_FORMAT));
        u2.save();
        User u3 = new User();
        u3.setUsername("tidza");
        u3.setEmail("hatidza.memic87@gmail.com");
        u3.setPassword(MD5Hash.getEncriptedPasswordMD5("Tidza123."));
        u3.setFirstName("Tidza");
        u3.setLastName("Memic");
        u3.setBirthDate("23.03.1987");
        u3.setGender(2);
        u3.setLocation("Sarajevo");
        u3.setPhoneNumber("000-000-002");
        u3.setCreationDate(DateTimeHelper.getCurrentDateFormated(DateTimeHelper.DEFAULT_FORMAT));
        u3.setUpdateDate("0");
        u3.setVerification(1);
        u3.setUserLevel(1000);
        u3.setLoginCount(0);
        u3.setPremiumUser(100);
        u3.setGuest(-1);
        u3.setActive(1);
        u3.setToken(UUID.randomUUID().toString());
        u3.setNotes("Reg by app: " + DateTimeHelper.getCurrentDateFormated(DateTimeHelper.DEFAULT_FORMAT));
        u3.save();
//        User u4 = new User();
//        u4.setUsername("erver");
//        u4.setEmail("enver.memic08@gmail.com");
//        u4.setPassword(MD5Hash.getEncriptedPasswordMD5("Erver123."));
//        u4.setFirstName("Erver");
//        u4.setLastName("Memic");
//        u4.setBirthDate("20.10.1980");
//        u4.setGender(1);
//        u4.setLocation("Sarajevo");
//        u4.setPhoneNumber("000-000-004");
//        u4.setCreationDate(DateTimeHelper.getCurrentDateFormated(DateTimeHelper.DEFAULT_FORMAT));
//        u4.setUpdateDate("0");
//        u4.setVerification(0);
//        u4.setUserLevel(0);
//        u4.setLoginCount(0);
//        u4.setPremiumUser(0);
//        u4.setGuest(0);
//        u4.setActive(0);
//        u4.setToken(UUID.randomUUID().toString());
//        u4.setNotes("Reg by app: " + DateTimeHelper.getCurrentDateFormated(DateTimeHelper.DEFAULT_FORMAT));
//        u4.save();
        User u5 = new User();
        u5.setUsername("mitko");
        u5.setEmail("mitko.mrdja@gmail.com");
        u5.setPassword(MD5Hash.getEncriptedPasswordMD5("Mitko123."));
        u5.setFirstName("Mitko");
        u5.setLastName("Mrdja");
        u5.setBirthDate("20.01.1977");
        u5.setGender(3);
        u5.setLocation("Sarajevo");
        u5.setPhoneNumber("000-000-005");
        u5.setCreationDate(DateTimeHelper.getCurrentDateFormated(DateTimeHelper.DEFAULT_FORMAT));
        u5.setUpdateDate("0");
        u5.setVerification(0);
        u5.setUserLevel(0);
        u5.setLoginCount(0);
        u5.setPremiumUser(0);
        u5.setGuest(0);
        u5.setActive(1);
        u5.setToken(UUID.randomUUID().toString());
        u5.setNotes("Reg by app: " + DateTimeHelper.getCurrentDateFormated(DateTimeHelper.DEFAULT_FORMAT));
        u5.save();
    }

}
