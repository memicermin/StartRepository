package models;

import com.avaje.ebean.Model;
import com.cloudinary.*;
import controllers.AdminController;

import javax.persistence.*;
import java.io.File;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Enver on 11/8/2016.
 */

@Entity
@Table
public class Image extends Model {

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

    @Column(name = "rent_a_car")
    @JoinColumn(name = "rent_a_car_id", referencedColumnName = "id")
    @ManyToOne
    private RentACar rentACar;

    @Column(name = "id")
    @JoinColumn(name = "sale_id", referencedColumnName = "id")
    @ManyToOne
    private Sale sale;

    @Column(name = "background_active")
    private int backgroundActive;

    @Column(name = "using_type")
    private int usingType;



    public Image(RentACar rentACar, Sale sale) {
        this.rentACar = rentACar;
        this.sale = sale;
    }

    public Image() {
    }

    public Long getId() {
        return id;
    }

    public RentACar getRentACar() {
        return rentACar;
    }

    public void setRentACar(RentACar rentACar) {
        this.rentACar = rentACar;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public static Finder<String, Image> getFind() {
        return find;
    }

    public int getBackgroundActive() {
        return backgroundActive;
    }

    public void setBackgroundActive(int backgroundActive) {
        this.backgroundActive = backgroundActive;
    }

    public int getUsingType() {
        return usingType;
    }

    public void setUsingType(int usingType) {
        this.usingType = usingType;
    }

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
    public static Image create(File image, Long id, int usingType) {
        Map result;
        try {
            result = cloudinary.uploader().upload(image, null);
            return create(result, id, usingType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Uploading new image and saving its path with product
     */
    public static Image create(Map uploadResult, Long id, int usingType) {
        Image img = new Image();
        img.public_id = (String) uploadResult.get("public_id");
        img.image_url = (String) uploadResult.get("url");
        img.secret_image_url = (String) uploadResult.get("secure_url");

        if (usingType < 0) {
            if (Image.find.findRowCount() == 0) {
                img.setBackgroundActive(2);
            } else {
                img.setBackgroundActive(1);
            }
            img.usingType = -1;
        } else if (usingType > 0) {

        } else if (usingType == 0) {
            Sale sale = Sale.getSaleById(id);
            img.sale = sale;
            img.usingType = 0;
        }


        img.save();
        return img;
    }


    /**
     * This method generates a url for the wanted image
     *
     * @param width
     * @param height
     * @return the public url of the cloudinary image
     */
    public String getSize(int width, int height) {

        String url = cloudinary.url().format("jpg")
                .transformation(new Transformation().width(width).height(height).crop("fill"))
                .generate(image_url);

        return url;
    }

    public static List<Image> getImagesForBackground() {
        return find.where().eq("using_type", -1).findList();
    }

    public static String getBackground() {
        Image background = find.where().eq("background_active", 2).findUnique();
        if(background != null){
            return background.image_url;
        }else{
            return "/assets/images/passat-b6.jpg";
        }

    }

    public static String getTestBackground(){
        Image testBackground = find.where().eq("background_active", 3).findUnique();
        if(testBackground != null){
            return testBackground.image_url;
        }else{
          //  return "/assets/images/passat-b6.jpg";
            return "";
        }
    }

    public static void setTestImage(Long id){
        /*
        Image image = find.byId(id + "");

        image.setBackgroundActive(3);
        image.update();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        image.setBackgroundActive(1);
            image.update();
            */
        AdminController.redirect("/background");
    }

}
