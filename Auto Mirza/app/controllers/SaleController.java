package controllers;


import com.cloudinary.Cloudinary;
import com.google.inject.Inject;
import helpers.Help;
import models.Brand;
import models.Image;
import models.Sale;
import org.joda.time.DateTime;
import play.Logger;
import play.Play;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import resources.FieldNames;
import views.html.admin_view.add_sale;
import views.html.sale.all_products_for_sale;
import views.html.sale.open_product;

import java.io.File;
import java.util.List;


/**
 * Created by Enver on 11/9/2016.
 */
public class SaleController extends Controller {

    @Inject
    FormFactory formFactory;

    public Result allProductsForSale(){
        return ok(all_products_for_sale.render(Sale.getAllProductForSale()));
    }

    public Result openProduct(Long id){

        return ok(open_product.render(Sale.getSaleById(id)));
    }

    public Result newSale() {
        return ok(add_sale.render(formFactory.form(Sale.class), Brand.getAllBrands(), Help.getLastHundredYears()));
    }



    public Result saveSale() {

        DynamicForm dynamicForm = formFactory.form().bindFromRequest();


        Image.cloudinary = new Cloudinary("cloudinary://" + Play.application().configuration().getString("cloudinary.string"));


        Sale sale = new Sale();
        sale.setAvilable(true);
        sale.setBrand(Brand.findBrandById(Long.parseLong(dynamicForm.get(FieldNames.BRAND))));
        sale.setDetails(dynamicForm.get(FieldNames.DETAILS));
        sale.setPrice(Double.parseDouble(dynamicForm.get(FieldNames.PRICE)));
        sale.setType(dynamicForm.get(FieldNames.TYPE));
        sale.setYear(Integer.parseInt(dynamicForm.get(FieldNames.YEAR)));
        sale.setBodyType(dynamicForm.get(FieldNames.BODY_TYPE));
        sale.setColor(dynamicForm.get(FieldNames.COLOR));
        sale.setMeileage(Integer.parseInt(dynamicForm.get(FieldNames.MILEAGE)));
        sale.setMotorVolume(dynamicForm.get(FieldNames.MOTOR_VOLUME));
        sale.setTypeOfFuel(dynamicForm.get(FieldNames.TYPE_OF_FUEL));
        sale.setTransmission(dynamicForm.get(FieldNames.TRANSMISSION));
        sale.setMotorPower(Integer.parseInt(dynamicForm.get(FieldNames.MOTOR_POWER)));
        sale.save();


        Http.MultipartFormData body = request().body().asMultipartFormData();
        List<Http.MultipartFormData.FilePart> fileParts = body.getFiles();
        Logger.info("1.---" + fileParts.toString());


        Logger.info("2.---" + dynamicForm.get(FieldNames.IMAGES));
        Logger.info("3.---" + body.toString());


        if (fileParts != null) {
            for (Http.MultipartFormData.FilePart filePart : fileParts) {
            try {


                    File file = (File) filePart.getFile();

                    Image image = Image.create(file, sale.getId());

                    image.save();


            }catch (RuntimeException re){
                Logger.info("Imamo pad " + new DateTime().toString() + " -> " + filePart.getFile().toString());
            }
            }
        }


        return redirect("/");
    }

}
