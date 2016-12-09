package helpers;

import com.cloudinary.Cloudinary;
import models.Image;
import models.User;
import org.joda.time.DateTime;
import play.Play;
import utils.MD5Hash;

import java.io.File;

/**
 * Created by Enver on 11/21/2016.
 */
public class FillDatabase {
/*
    public static void setDefBacgroundImages() {
        Image.cloudinary = new Cloudinary("cloudinary://" + Play.application().configuration().getString("cloudinary.string"));

        File file4 = new File("public\\images\\vito-6.jpg");
        Image im4 = Image.create(file4, null, -1);
        im4.save();

        File file1 = new File("public\\images\\passat-b6.jpg");
        Image im1 = Image.create(file1, null, -1);
        im1.save();

        File file2 = new File("public\\images\\2011-Mercedes-Benz-Vito-model-range.jpg");
        Image im2 = Image.create(file2, null, -1);
        im2.save();

        File file3 = new File("public\\images\\mercedes-vito-pics-11789.jpg");
        Image im3 = Image.create(file3, null, -1);
        im3.save();



    }

    public static void setDefaultUsers() {
        User u1 = new User();
        u1.setEmail("enver.memic80@gmail.com");
        u1.setUserLevel(1);
        u1.setPassword(MD5Hash.getEncriptedPasswordMD5("Enver1234"));
        u1.setUsername("enver");
        u1.setCreationDate(new DateTime());
        u1.save();
    }
    */
}
