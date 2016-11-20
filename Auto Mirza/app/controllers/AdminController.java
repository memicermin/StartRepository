package controllers;

import com.google.inject.Inject;
import models.Brand;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.admin_view.add_brand;


/**
 * Created by Enver on 11/9/2016.
 */
public class AdminController extends Controller {


    @Inject
    FormFactory formFactory;


    public Result newBrand(){
        return ok(add_brand.render(formFactory.form(Brand.class)));
    }

    public Result saveBrand(){
        DynamicForm dynamicForm = formFactory.form().bindFromRequest();
        Brand brand = new Brand(dynamicForm.get("brand"));
        brand.save();
        return redirect("/admin_new_brand ");
    }

}
