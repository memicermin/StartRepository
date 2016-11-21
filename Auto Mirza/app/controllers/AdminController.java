package controllers;

import com.google.inject.Inject;
import models.Brand;
import models.ReclaimTitle;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.admin_view.add_brand;
import views.html.admin_view.add_reclaim_titles;
import views.html.admin_view.admin;


/**
 * Created by Enver on 11/9/2016.
 */
public class AdminController extends Controller {


    @Inject
    FormFactory formFactory;


    public Result newBrand(){
        return ok(add_brand.render(Brand.find.all(), formFactory.form(Brand.class)));
    }

    public Result admin(){
        return ok(admin.render());
    }

    public Result saveBrand(){
        DynamicForm dynamicForm = formFactory.form().bindFromRequest();
        Brand brand = new Brand(dynamicForm.get("brand"));
        brand.save();
        return redirect("/admin_new_brand ");
    }


    public Result listReclaimTitles(){
        return ok(add_reclaim_titles.render(ReclaimTitle.find.all(), formFactory.form(ReclaimTitle.class)));
    }

    public Result saveReclaimTitle(){
        DynamicForm dynamicForm = formFactory.form().bindFromRequest();
        ReclaimTitle title = new ReclaimTitle(dynamicForm.get("title"));
        title.save();
        return redirect("/titles");
    }

    public Result deleteReclaimTitle(Long id){
        ReclaimTitle title = ReclaimTitle.find.byId(id);
        title.delete();
        return redirect("/titles");
    }
}
