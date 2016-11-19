package models;

import com.avaje.ebean.Model;
import com.cloudinary.*;

import javax.persistence.*;
import java.io.File;
import java.io.*;
import java.util.Map;

/**
 * Created by Enver on 11/8/2016.
 */

@Entity
@Table
public class Image extends Model {

    private static Finder<String, Image> find = new Finder<>(Image.class);

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
    public static Image create(File image, Long id) {
        Map result;
        try {
            result = cloudinary.uploader().upload(image, null);
            return create(result, id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Uploading new image for selected product and saving its path with product
     */
    public static Image create(Map uploadResult, Long id) {
        Image img = new Image();
        img.public_id = (String) uploadResult.get("public_id");
        img.image_url = (String) uploadResult.get("url");
        img.secret_image_url = (String) uploadResult.get("secure_url");
        //  Product product = Product.getProductById(id);
        //  img.product = product;

        Sale sale = Sale.getSaleById(id);
        img.sale = sale;
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
                .generate(public_id);

        return url;
    }

}
