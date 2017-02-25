package controllers;

import com.cloudinary.Cloudinary;
import com.google.inject.Inject;
import helpers.Admin;
import helpers.Help;
import models.help_models.Brand;
import models.help_models.Car;
import models.help_models.Image;
import models.help_models.Price;
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
import views.html.add.add_sale;

import java.io.File;
import java.util.List;

/**
 * Created by Enver on 2/25/2017.
 */
@Security.Authenticated(Admin.class)
public class SaleController extends Controller {

    @Inject
    FormFactory formFactory;

    public Result addSale(){
        return ok(add_sale.render(formFactory.form(Sale.class), Brand.getAllBrands(), Help.getLastHundredYears()));
    }

    public Result saveSale(){
        DynamicForm dynamicForm = formFactory.form().bindFromRequest();

        Image.cloudinary = new Cloudinary("cloudinary://" + Play.application().configuration().getString("cloudinary.string"));

        Sale sale;
        Price price;
        Car car;

        sale = new Sale();
        sale.setPartOfSale(Sale.CARS);
        sale.setAvailable(1);
        sale.setDescription(dynamicForm.get(FieldNames.DETAILS));

        price = new Price(Float.parseFloat(dynamicForm.get(FieldNames.PRICE)),0F,0);

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

        sale.setPrice(price);
        sale.setCar(car);

        sale.save();


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
        return redirect("/sale/add/sale");
    }
}
