package controllers;

import com.cloudinary.Cloudinary;
import com.google.inject.Inject;
import helpers.Admin;
import helpers.Help;
import models.help_models.Brand;
import models.help_models.Car;
import models.help_models.Image;
import models.help_models.Price;
import models.products.RentACar;
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
import views.html.add.add_rentacar;

import java.io.File;
import java.util.List;

/**
 * Created by Enver on 2/26/2017.
 */
@Security.Authenticated(Admin.class)
public class RentACarController extends Controller {

    @Inject
    FormFactory formFactory;

    public Result addRentACar() {
        return ok(add_rentacar.render(formFactory.form(RentACar.class), Brand.getCarBrands(), Help.getLastHundredYears()));
    }

    public Result saveRentACar() {
        DynamicForm dynamicForm = formFactory.form().bindFromRequest();

        Image.cloudinary = new Cloudinary("cloudinary://" + Play.application().configuration().getString("cloudinary.string"));

        RentACar rent;
        Price price;
        Car car;

        rent = new RentACar();
        rent.setAvailabilityDate("");
        rent.setActiveCar(1);
        rent.setAvailable(1);
        rent.setDescription(dynamicForm.get(FieldNames.DETAILS));
        price = new Price(Float.parseFloat(dynamicForm.get(FieldNames.PRICE)), 0F, 0);
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
        price.save();
        car.save();
        rent.setPrice(price);
        rent.setCar(car);
        rent.save();
        Http.MultipartFormData body = request().body().asMultipartFormData();
        List<Http.MultipartFormData.FilePart> fileParts = body.getFiles();
        if (fileParts != null) {
            for (Http.MultipartFormData.FilePart filePart : fileParts) {
                try {
                    File file = (File) filePart.getFile();
                    Image image = Image.create(file, car.getId());
                    image.save();
                } catch (RuntimeException re) {
                    Logger.info("Imamo pad " + new DateTime().toString() + " -> " + filePart.getFile().toString());
                }
            }
        }
        return redirect("/rent/a/car/add/rent/a/car");
    }
}
