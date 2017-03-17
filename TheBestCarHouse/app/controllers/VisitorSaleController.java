package controllers;

import models.products.Sale;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.sale.cars.all_cars;

/**
 * Created by Enver on 3/17/2017.
 */
public class VisitorSaleController extends Controller {


    public Result getAllCarsForSale(){
        return ok(all_cars.render(Sale.getAllCarsForSale()));
    }
}
