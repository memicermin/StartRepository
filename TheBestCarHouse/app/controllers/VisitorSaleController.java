package controllers;

import models.help_models.Brand;
import models.help_models.Car;
import models.help_models.CarTires;
import models.products.Sale;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.sale.car_tires.all_tires;
import views.html.sale.car_tires.tires_for_sale;
import views.html.sale.cars.all_cars;
import views.html.sale.cars.car_for_sale;
import views.html.sale.sales_by_brand;

import java.util.ArrayList;
import java.util.List;

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

    public Result getTiresForSale(Long id){
        return ok(tires_for_sale.render(Sale.getSaleById(id)));
    }

    public Result getSalesByBrand(Long id){
        Brand brand = Brand.findBrandById(id);
        if(brand.getPartOfBrand() == Sale.CARS){
            List<Car> cars = Car.find.where().eq("brand_id", id).findList();
            List<Sale> sales = new ArrayList<>();
            for(Car car : cars){
                Sale sal = Sale.getSaleByCarId(car.getId());
                sales.add(sal);
            }
            if(sales.size()>0){
                return ok(all_cars.render(sales));
            }
        }else if(brand.getPartOfBrand() == Sale.TIRES){
            List<CarTires> tires = CarTires.find.where().eq("brand_id", id).findList();
            List<Sale> sales = new ArrayList<>();
            for(CarTires carTires : tires){
                Sale sal = Sale.getSaleByTiresId(carTires.getId());
                sales.add(sal);
            }
            if(sales.size()>0){
                return ok(all_tires.render(sales));
            }
        }
        return redirect("/");
    }

    public Result listBrands(){
        return ok(sales_by_brand.render(Brand.getAllBrands()));
    }

}
