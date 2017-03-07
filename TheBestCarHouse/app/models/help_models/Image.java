package models.help_models;

import com.avaje.ebean.Model;
import com.cloudinary.Cloudinary;
import models.products.Sale;

import javax.persistence.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Enver on 2/21/2017.
 */
@Entity
@Table(name = "image")
public class Image extends Model{

    public static Model.Finder<Long,Image> find = new Model.Finder<>(Image.class);

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

    @Column(name = "id")
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    @ManyToOne
    public Car car;

    @Column(name = "id")
    @JoinColumn(name = "car_tires", referencedColumnName = "id")
    @ManyToOne
    public CarTires carTires;


    @Column(name = "id")
    @JoinColumn(name = "background", referencedColumnName = "id")
    @ManyToOne
    public Background background;




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

    /**
     * Method that return image after uploading it on cloudinary
     */
    public static Image create(File image, Long id, int type) {
        Map result;
        try {
            result = cloudinary.uploader().upload(image, null);
            return create(result, id, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Image create(Map uploadResult, Long id, int type) {
        Image img = new Image();
        img.public_id = (String) uploadResult.get("public_id");
        img.image_url = (String) uploadResult.get("url");
        img.secret_image_url = (String) uploadResult.get("secure_url");

        if(type == Sale.CARS){
            Car car = Car.getCarById(id);
            img.car = car;
        }
        if(type == Sale.TIRES){
            CarTires tires = CarTires.getTiresById(id);
            img.carTires = tires;
        }


        img.save();
        return img;
    }

}
