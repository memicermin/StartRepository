package models;

import com.avaje.ebean.Model;
import play.data.Form;

import javax.persistence.*;
import java.util.List;



/**
 * Created by Enver on 11/8/2016.
 */
@Entity
@Table(name = "sale")
public class Sale extends Model {

    public static Finder<Long, Sale> find = new Finder<>(Sale.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @OneToMany
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    @Column(name = "brand")
    private Brand brand;

    private String type;

    private Integer year;

    private Double price;

    private String details;

    private Boolean isAvilable;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    public List<Image> images;

    public Sale(Brand brand, String type, Integer year, Double price, String details, Boolean avilable) {
        this.brand = brand;
        this.type = type;
        this.year = year;
        this.price = price;
        this.details = details;
        this.isAvilable = avilable;
    }

    public Sale() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Boolean getAvilable() {
        return isAvilable;
    }

    public void setAvilable(Boolean avilable) {
        this.isAvilable = avilable;
    }


    //Methods


    public List<Image> getImages(Long id){
        return Image.getFind().where().eq("sale_id", id).findList();
    }

    public Image getFirstImage(Long id){
        Sale sale = getSaleById(id);
        List<Image> images = Image.getFind().where().eq("sale_id", id).findList();
        if(images != null){
            return images.get(0);
        }else{
            return  null;
        }

    }

    public static Sale getSaleById(Long id) {
        return find.byId(id);
    }

    public static List<Sale> getAllProductForSale(){
        return find.all();
    }
}
