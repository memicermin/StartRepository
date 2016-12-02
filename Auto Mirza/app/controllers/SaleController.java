package controllers;

import com.cloudinary.Cloudinary;
import com.google.inject.Inject;
import helpers.Help;
import helpers.SessionHelper;
import models.Brand;
import models.Image;
import models.Sale;
import models.User;
import org.joda.time.DateTime;
import play.Logger;
import play.Play;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import resources.FieldNames;
import views.html.admin_view.add_action;
import views.html.admin_view.add_sale;
import views.html.admin_view.add_sale_image;
import views.html.sale.all_products_for_sale;
import views.html.sale.open_product;

import java.io.File;
import java.util.List;

/**
 * Created by Enver on 11/9/2016.
 */
public class SaleController extends Controller {

    User currentUser = SessionHelper.getCurrentUser(ctx());

    @Inject
    FormFactory formFactory;

    public Result allProductsForSale() {
        List<Sale> sales = Sale.getAllProductForSale();
        return ok(all_products_for_sale.render(sales));
    }

    public Result openProduct(Long id) {
        return ok(open_product.render(Sale.getSaleById(id)));
    }

    public Result newSale() {
        return ok(add_sale.render(formFactory.form(Sale.class), Brand.getAllBrands(), Help.getLastHundredYears()));
    }

    public Result saveSale() {
        User currentUser = SessionHelper.getCurrentUser(ctx());
        if (currentUser != null) {
            if (currentUser.getUserLevel() == 1) {

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
                sale.setAction(false);
                sale.setOldPrice(0D);
                sale.save();

                Http.MultipartFormData body = request().body().asMultipartFormData();
                List<Http.MultipartFormData.FilePart> fileParts = body.getFiles();

                if (fileParts != null) {
                    for (Http.MultipartFormData.FilePart filePart : fileParts) {
                        try {
                            File file = (File) filePart.getFile();
                            Image image = Image.create(file, sale.getId(), 0);
                            image.save();
                        } catch (RuntimeException re) {
                            Logger.info("Imamo pad " + new DateTime().toString() + " -> " + filePart.getFile().toString());
                        }
                    }
                }
                return redirect("/");
            }
            currentUser.setUserLevel(-1);
            currentUser.update();
            return redirect(routes.UserController.singUp());
        }
        return redirect("/");
    }

    public Result updateSale(Long id) {
        User currentUser = SessionHelper.getCurrentUser(ctx());
        if (currentUser != null) {
            if (currentUser.getUserLevel() == 1) {
                DynamicForm dynamicForm = formFactory.form().bindFromRequest();
                Sale sale = Sale.getSaleById(id);
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
                sale.update();
                return redirect(routes.SaleController.openProduct(sale.getId()));
            }
        }
        currentUser.setUserLevel(-1);
        currentUser.update();
        return redirect(routes.UserController.singUp());

    }

    public Result addAction(Long id){
        User currentUser = SessionHelper.getCurrentUser(ctx());
        if (currentUser != null) {
            if (currentUser.getUserLevel() == 1) {
                return ok(add_action.render(formFactory.form(Sale.class), Sale.getSaleById(id)));
            }
        }
        currentUser.setUserLevel(-1);
        currentUser.update();
        return redirect(routes.UserController.singUp());
    }

    public Result saveAction(Long id){
        User currentUser = SessionHelper.getCurrentUser(ctx());
        if (currentUser != null) {
            if (currentUser.getUserLevel() == 1) {
                DynamicForm dynamicForm = formFactory.form().bindFromRequest();
                Sale sale = Sale.getSaleById(id);
                String action = dynamicForm.get("action");
                try {
                    double oldPrice = sale.getPrice();
                    double newPrice = Double.parseDouble(dynamicForm.get("price"));
                    if("on".equals(action)){
                        sale.setOldPrice(oldPrice);
                        sale.setPrice(newPrice);
                        sale.setAction(true);
                    }else{
                        sale.setOldPrice(0D);
                        sale.setPrice(newPrice);
                        sale.setAction(false);
                    }
                }catch (NumberFormatException nfe){
                    flash("warning", "Ilegal number format");
                    return ok(add_action.render(formFactory.form(Sale.class), Sale.getSaleById(id)));
                }
                sale.update();
                return redirect(routes.SaleController.openProduct(sale.getId()));
            }
        }
        currentUser.setUserLevel(-1);
        currentUser.update();
        return redirect(routes.UserController.singUp());
    }

    public Result addImages(Long id){
        if(SessionHelper.isRegularAdmin()){

            return ok(add_sale_image.render(Sale.getSaleById(id)));
        }
        currentUser.setUserLevel(-1);
        currentUser.update();
        return redirect(routes.UserController.singUp());
    }
}