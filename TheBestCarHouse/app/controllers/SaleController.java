package controllers;

import com.cloudinary.Cloudinary;
import com.google.inject.Inject;
import helpers.Admin;
import helpers.Help;
import models.help_models.*;
import models.products.Sale;
import org.joda.time.DateTime;
import play.Logger;
import play.Play;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import resources.FieldNames;
import views.html.add.add_car_for_sale;
import views.html.add.add_car_tires_for_sale;

import java.io.File;
import java.util.List;

/**
 * Created by Enver on 2/25/2017.
 */
@Security.Authenticated(Admin.class)
public class SaleController extends Controller {

    @Inject
    FormFactory formFactory;

    public Result addCarSale(){
        return ok(add_car_for_sale.render(formFactory.form(Sale.class), Brand.getCarBrands(), Help.getLastHundredYears()));
    }

    public Result saveCarSale(){
        DynamicForm dynamicForm = formFactory.form().bindFromRequest();
        Image.cloudinary = new Cloudinary("cloudinary://" + Play.application().configuration().getString("cloudinary.string"));
        Sale sale;
        Price price;
        Car car;

        price = new Price(Float.parseFloat(dynamicForm.get(FieldNames.PRICE)),0F,0);
        price.save();

        car = new Car();
        car.setBrand(Brand.findBrandById(Long.parseLong(dynamicForm.get(FieldNames.BRAND))));
        car.setType(dynamicForm.get(FieldNames.TYPE));
        car.setYear(dynamicForm.get(FieldNames.YEAR));
        car.setBodyType(dynamicForm.get(FieldNames.BODY_TYPE));
        car.setColor(dynamicForm.get(FieldNames.COLOR));
        car.setMileage(dynamicForm.get(FieldNames.MILEAGE));
        car.setMotorVolume(dynamicForm.get(FieldNames.MOTOR_VOLUME));
        car.setTypeOfFuel(dynamicForm.get(FieldNames.TYPE_OF_FUEL));
        car.setTransmission(dynamicForm.get(FieldNames.TRANSMISSION));
        car.setMotorPower(dynamicForm.get(FieldNames.MOTOR_POWER));
        car.save();

        sale = new Sale();
        sale.setPartOfSale(Sale.CARS);
        sale.setAvailable(1);
        sale.setDescription(dynamicForm.get(FieldNames.DETAILS));
        sale.setPrice(price);
        sale.setCar(car);
        sale.save();

        Http.MultipartFormData body = request().body().asMultipartFormData();
        List<Http.MultipartFormData.FilePart> fileParts = body.getFiles();

        if (fileParts != null) {
            for (Http.MultipartFormData.FilePart filePart : fileParts) {
                try {
                    File file = (File) filePart.getFile();
                    Image image = Image.create(file, car.getId(), Sale.CARS);
                    image.save();
                } catch (RuntimeException re) {
                    Logger.info("Imamo pad " + new DateTime().toString() + " -> " + filePart.getFile().toString());
                }
            }
        }
        return redirect("/sale/add/sale");
    }

    public Result addCarTiresForSale(){
        return ok(add_car_tires_for_sale.render(formFactory.form(Sale.class), Brand.getCarTiresBrands(), Help.getLastHundredYears()));
    }

    public Result saveCarTiresForSale(){

        DynamicForm dynamicForm = formFactory.form().bindFromRequest();
        Image.cloudinary = new Cloudinary("cloudinary://" + Play.application().configuration().getString("cloudinary.string"));
        Sale sale;
        Price price;
        CarTires carTires;

        price = new Price(Float.parseFloat(dynamicForm.get(FieldNames.PRICE)),0F,0);
        price.save();

        carTires = new CarTires();
        carTires.setBrand(Brand.findBrandById(Long.parseLong(dynamicForm.get(FieldNames.BRAND))));
        carTires.setYear(dynamicForm.get(FieldNames.YEAR));
        carTires.setType(dynamicForm.get(FieldNames.TYPE));
        carTires.setDimension(dynamicForm.get("dimension"));
        carTires.save();

        sale = new Sale();
        sale.setPartOfSale(Sale.TIRES);
        sale.setAvailable(1);
        sale.setDescription(dynamicForm.get(FieldNames.DETAILS));
        sale.setPrice(price);
        sale.setTires(carTires);
        sale.save();

        Http.MultipartFormData body = request().body().asMultipartFormData();
        List<Http.MultipartFormData.FilePart> fileParts = body.getFiles();

        if (fileParts != null) {
            for (Http.MultipartFormData.FilePart filePart : fileParts) {
                try {
                    File file = (File) filePart.getFile();
                    Image image = Image.create(file, carTires.getId(), Sale.TIRES);
                    image.save();
                } catch (RuntimeException re) {
                    Logger.info("Imamo pad " + new DateTime().toString() + " -> " + filePart.getFile().toString());
                }
            }
        }

        return redirect("/sale/car/tires/add ");
    }
}
