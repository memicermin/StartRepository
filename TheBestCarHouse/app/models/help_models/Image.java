package models.help_models;

import com.avaje.ebean.Model;
import com.cloudinary.Cloudinary;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Enver on 2/21/2017.
 */
public class Image extends Model{

    private static Model.Finder<String,Image> find = new Model.Finder<>(Image.class);

    public static Cloudinary cloudinary;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "public_id")
    public String public_id;

    @Column(name = "secret_image_url")
    public String secret_image_url;

    @Column(name = "image_url")
    public String image_url;


    /**
     * Constructor
     */
    public static Image create(String public_id, String image_url, String secret_image_url) {
        Image img = new Image();
        img.public_id = public_id;
        img.image_url = image_url;
        img.secret_image_url = secret_image_url;
        img.save();
        return img;
    }


}
