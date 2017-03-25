package controllers;

import com.google.inject.Inject;
import helpers.Admin;
import models.help_models.Price;
import models.products.Sale;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.add.add_action;

/**
 * Created by Enver on 3/25/2017.
 */
@Security.Authenticated(Admin.class)
public class PriceController extends Controller {

    @Inject
    FormFactory formFactory;

    public Result editPrice(Long id){
        Sale sale = Sale.getSaleById(id);

        return ok(add_action.render(sale, formFactory.form(Price.class)));
    }

    public Result savePrice(Long id){
        
        return ok();
    }

}
