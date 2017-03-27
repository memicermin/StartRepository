package controllers;

import com.google.inject.Inject;
import helpers.Admin;
import models.help_models.Price;
import models.products.Sale;
import play.data.DynamicForm;
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

    public Result editPrice(Long id) {
        Sale sale = Sale.getSaleById(id);
        return ok(add_action.render(sale, formFactory.form(Price.class)));
    }

    public Result updatePrice(Long id) {
        DynamicForm df = formFactory.form().bindFromRequest();
        Sale sale = Sale.getSaleById(id);
        Price price = Price.find.byId(sale.getPrice().getId());
        String action = df.get("action");
        try {
            float oldPrice = price.getCurrentPrice();
            float newPrice = Float.parseFloat(df.get("price"));
            if ("on".equals(action)) {
                price.setOldPrice(oldPrice);
                price.setCurrentPrice(newPrice);
                price.setAction(1);
            } else {
                price.setOldPrice(0F);
                price.setCurrentPrice(newPrice);
                price.setAction(0);
            }
            price.update();
        } catch (NumberFormatException nfe) {
            flash("warning", "Ilegal number format");
            return ok(add_action.render(Sale.getSaleById(id), formFactory.form(Price.class)));
        }
        if (sale.getPartOfSale() == Sale.TIRES) {
            return redirect(routes.VisitorSaleController.getAllTiresForSale());
        } else if (sale.getPartOfSale() == Sale.CARS) {
            return redirect(routes.VisitorSaleController.getAllCarsForSale());
        } else {
            return redirect("/");
        }

    }

}
