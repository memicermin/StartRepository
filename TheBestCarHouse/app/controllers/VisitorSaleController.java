package controllers;

import models.products.Sale;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.sale.car_tires.all_tires;
import views.html.sale.cars.all_cars;
import views.html.sale.cars.car_for_sale;

/**
 * Created by Enver on 3/17/2017.
 */
public class VisitorSaleController extends Controller {


    public Result getAllCarsForSale(){
        return ok(all_cars.render(Sale.getAllCarsForSale()));
    }

    public Result getAllTiresForSale(){
        return ok(all_tires.render(Sale.getAllTiresForSale()));
    }

    public Result getCarForSale(Long id){
        return ok(car_for_sale.render(Sale.find.byId(id)));
    }
}
