package controllers;

import com.google.inject.Inject;
import models.help_models.Brand;
import models.help_models.Car;
import models.help_models.CarTires;
import models.help_models.Price;
import models.products.RentACar;
import models.products.Sale;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.rent.all_cars_for_rent;
import views.html.sale.car_tires.all_tires;
import views.html.sale.car_tires.tires_for_sale;
import views.html.sale.cars.all_cars;
import views.html.sale.cars.car_for_sale;
import views.html.sale.sales_by_brand;
import views.html.sale.search_page;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Enver on 3/17/2017.
 */
public class VisitorSaleController extends Controller {

    @Inject
    FormFactory formFactory;

    public Result search() {
        return ok(search_page.render(Brand.getAllBrands()));
    }

    /**
     * This method serves for search Cars which are on sale.
     * There is a possibility search by Brand, or all brands,
     * and by action price, and min and max price.
     *
     * If the user does not enter any data, the method returns for
     * render all cars from sale list;
     *
     * If the user enters some data, the method returns the filtred data
     *
     * @return ArrayList[Sale]
     */
    public Result carsSearch() {
        DynamicForm dynamicForm = formFactory.form().bindFromRequest();
        List<Sale> saleList = new ArrayList<>();
        List<Sale> newSaleList = new ArrayList<>();
        Brand brand;
        if (dynamicForm.get("brand").equals("all")) {
            saleList = Sale.getAllCarsForSale();
        } else {
            brand = Brand.findBrandById(Long.parseLong(dynamicForm.get("brand")));
            List<Car> cars = Car.find.where().eq("brand_id", brand.getId()).findList();
            for (Car car : cars) {
                saleList.add(Sale.getSaleByCarId(car.getId()));
            }
        }
        String minPriceS = dynamicForm.get("price_min");
        String maxPriceS = dynamicForm.get("price_max");
        float minPrice;
        float maxPrice;
        if(minPriceS.equals("")){
            minPrice = 0f;
        }else{
            minPrice = Float.parseFloat(dynamicForm.get("price_min"));
        }
        if(maxPriceS.equals("")){
            maxPrice = Price.getMaxPrice();
        }else {
            maxPrice = Float.parseFloat(dynamicForm.get("price_max"));
        }
        if(maxPrice == minPrice){
            maxPrice++;
        }
        if (minPrice < 0 || maxPrice <= 0 || maxPrice <= minPrice) {
            return ok(search_page.render(Brand.getAllBrands()));
        }
        if (saleList != null && saleList.size() > 0) {
            for (Sale sale : saleList) {
                boolean isForRender = true;
                if (dynamicForm.get("action")!= null) {
                    if (sale.getPrice().getAction() != 1) {
                        isForRender = false;
                    }
                }
                if (sale.getPrice().getCurrentPrice() < minPrice) {
                    isForRender = false;
                }
                if (sale.getPrice().getCurrentPrice() > maxPrice) {
                    isForRender = false;
                }
                if(isForRender == true){
                    newSaleList.add(sale);
                }
            }
        }
        if(newSaleList != null && newSaleList.size() > 0){
            return ok(all_cars.render(newSaleList));
        }
        return ok(search_page.render(Brand.getAllBrands()));
    }

    /**
     *
     * @return
     */
    public Result getAllCarsForSale() {
        return ok(all_cars.render(Sale.getAllCarsForSale()));
    }

    /**
     *
     * @param id
     * @return
     */
    public Result getCarForSale(Long id) {
        return ok(car_for_sale.render(Sale.find.byId(id)));
    }

    public Result carTiresSearch(){
        DynamicForm dynamicForm = formFactory.form().bindFromRequest();
        List<Sale> saleList = new ArrayList<>();
        List<Sale> newSaleList = new ArrayList<>();
        Brand brand;
        if (dynamicForm.get("brand").equals("all")) {
            saleList = Sale.getAllTiresForSale();
        } else {
            brand = Brand.findBrandById(Long.parseLong(dynamicForm.get("brand")));
            List<CarTires> tires = CarTires.find.where().eq("brand_id", brand.getId()).findList();
            for (CarTires ct : tires) {
                saleList.add(Sale.getSaleByTiresId(ct.getId()));
            }
        }
        String minPriceS = dynamicForm.get("price_min");
        String maxPriceS = dynamicForm.get("price_max");
        float minPrice;
        float maxPrice;
        if(minPriceS.equals("")){
            minPrice = 0f;
        }else{
            minPrice = Float.parseFloat(dynamicForm.get("price_min"));
        }
        if(maxPriceS.equals("")){
            maxPrice = Price.getMaxPrice();
        }else {
            maxPrice = Float.parseFloat(dynamicForm.get("price_max"));
        }
        if(maxPrice == minPrice){
            maxPrice++;
        }
        if (minPrice < 0 || maxPrice <= 0 || maxPrice <= minPrice) {
            return ok(search_page.render(Brand.getAllBrands()));
        }
        if (saleList != null && saleList.size() > 0) {
            for (Sale sale : saleList) {
                boolean isForRender = true;
                if (dynamicForm.get("action")!= null) {
                    if (sale.getPrice().getAction() != 1) {
                        isForRender = false;
                    }
                }
                if (sale.getPrice().getCurrentPrice() < minPrice) {
                    isForRender = false;
                }
                if (sale.getPrice().getCurrentPrice() > maxPrice) {
                    isForRender = false;
                }
                if(isForRender == true){
                    newSaleList.add(sale);
                }
            }
        }
        if(newSaleList != null && newSaleList.size() > 0){
            return ok(all_tires.render(newSaleList));
        }
        return ok(search_page.render(Brand.getAllBrands()));
    }

    /**
     *
     * @return
     */
    public Result getAllTiresForSale() {
        return ok(all_tires.render(Sale.getAllTiresForSale()));
    }

    /**
     *
     * @param id
     * @return
     */
    public Result getTiresForSale(Long id) {
        return ok(tires_for_sale.render(Sale.getSaleById(id)));
    }

    /**
     *
     * @param id
     * @return
     */
    public Result getSalesByBrand(Long id) {
        Brand brand = Brand.findBrandById(id);
        if (brand.getPartOfBrand() == Sale.CARS) {
            List<Car> cars = Car.find.where().eq("brand_id", id).findList();
            List<Sale> sales = new ArrayList<>();
            for (Car car : cars) {
                Sale sal = Sale.getSaleByCarId(car.getId());
                sales.add(sal);
            }
            if (sales.size() > 0) {
                return ok(all_cars.render(sales));
            }
        } else if (brand.getPartOfBrand() == Sale.TIRES) {
            List<CarTires> tires = CarTires.find.where().eq("brand_id", id).findList();
            List<Sale> sales = new ArrayList<>();
            for (CarTires carTires : tires) {
                Sale sal = Sale.getSaleByTiresId(carTires.getId());
                sales.add(sal);
            }
            if (sales.size() > 0) {
                return ok(all_tires.render(sales));
            }
        }
        return redirect("/");
    }

    /**
     *
     * @return
     */
    public Result listBrands() {
        return ok(sales_by_brand.render(Brand.getAllBrands()));
    }


    //=============== Rent a Car =======================

    public Result getAllRentACars(){
        return ok(all_cars_for_rent.render(RentACar.find.all()));
    }
}
