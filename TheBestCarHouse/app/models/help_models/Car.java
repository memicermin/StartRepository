package models.help_models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Enver on 2/22/2017.
 */
@Entity
@Table(name = "car")
public class Car extends Model {

    public static Model.Finder<Long, Car> find = new Finder<>(Car.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    @Column(name = "brand")
    private Brand brand;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    public List<Image> images;

    private String type;

    private String year;

    private String bodyType;

    private String color;

    private String mileage;

    private String motorVolume;

    private String motorPower;

    private String typeOfFuel;

    private String transmission;

    public Car() {
    }

    //GETTERS AND SETTERS
    public String getMotorPower() {
        return motorPower;
    }

    public void setMotorPower(String motorPower) {
        this.motorPower = motorPower;
    }

    public Long getId() {
        return id;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
//
//    public List<Image> getImages() {
//        return images;
//    }
//
//    public void setImages(List<Image> images) {
//        this.images = images;
//    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getMotorVolume() {
        return motorVolume;
    }

    public void setMotorVolume(String motorVolume) {
        this.motorVolume = motorVolume;
    }

    public String getTypeOfFuel() {
        return typeOfFuel;
    }

    public void setTypeOfFuel(String typeOfFuel) {
        this.typeOfFuel = typeOfFuel;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    // FIND METHODS

    public static Car getCarById(Long id){
        return find.byId(id);
    }
}
