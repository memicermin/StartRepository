package models;

import com.avaje.ebean.Model;
import com.cloudinary.Cloudinary;
import models.products.Product;
import javax.persistence.*;


/**
 * Created by Enver on 11/8/2016.
 */

@Entity
@Table
public class Image extends Model {

    private static Finder<Long, Image> find = new Finder<>(Image.class);

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

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @Column(name = "product")
    private Product product;

    @Column(name = "background_active")
    private int backgroundActive;

    @Column(name = "using_type")
    private int usingType;




}
