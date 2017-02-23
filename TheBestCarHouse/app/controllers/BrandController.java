package controllers;

import com.google.inject.Inject;
import helpers.Admin;
import models.help_models.Brand;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.add.add_brand;

/**
 * Created by Enver on 2/23/2017.
 */
@Security.Authenticated(Admin.class)
public class BrandController extends Controller {

    @Inject
    FormFactory formFactory;

    public Result addBrand(){
        return ok(add_brand.render(Brand.getAllBrands(), formFactory.form(Brand.class)));
    }

    public Result saveBrand(){
                DynamicForm dynamicForm = formFactory.form().bindFromRequest();
                Brand brand = new Brand(dynamicForm.get("brand"));
                brand.save();
                return redirect("/add/brand");
    }

    public Result deleteBrand(Long id){
        Brand.findBrandById(id).delete();
        return redirect("/add/brand");
    }


}
