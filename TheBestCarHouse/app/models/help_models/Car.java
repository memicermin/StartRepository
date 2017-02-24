package models.help_models;

import com.avaje.ebean.Model;
import models.help_models.Brand;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Enver on 2/22/2017.
 */
@Entity
@Table(name = "car")
public class Car extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    @Column(name = "brand")
    private Brand brand;

//    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
//    private List<Image> images;

    private String type;

    private Integer year;

    private Double price;

    private String bodyType;

    private String color;

    private Integer meileage;

    private String motorVolume;

    private Integer motorPower;

    private String typeOfFuel;

    private String transmission;

    public Car() {
    }

    //GETTERS AND SETTERS
    public Integer getMotorPower() {
        return motorPower;
    }

    public void setMotorPower(Integer motorPower) {
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

    public Integer getMeileage() {
        return meileage;
    }

    public void setMeileage(Integer meileage) {
        this.meileage = meileage;
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
}
