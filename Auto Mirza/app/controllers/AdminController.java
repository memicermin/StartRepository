package controllers;

import com.cloudinary.Cloudinary;
import com.google.inject.Inject;
import helpers.SessionHelper;
import models.Brand;
import models.Image;
import models.ReclaimTitle;
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
import views.html.admin_view.add_background_image;
import views.html.admin_view.add_brand;
import views.html.admin_view.add_reclaim_titles;
import views.html.admin_view.admin;




/**
 * Created by Enver on 11/9/2016.
 */
public class AdminController extends Controller {
    User currentUser = SessionHelper.getCurrentUser(ctx());



    @Inject
    FormFactory formFactory;


    public Result admin(){
        if(currentUser != null){
            if(currentUser.getUserLevel() == 1){
                return ok(admin.render());
            }
        }
        currentUser.setUserLevel(-1);
        currentUser.update();
        return redirect(routes.UserController.singUp());

    }


    public Result newBrand(){
        if(currentUser != null){
            if(currentUser.getUserLevel() == 1){
                return ok(add_brand.render(Brand.find.all(), formFactory.form(Brand.class)));
            }
        }
        currentUser.setUserLevel(-1);
        currentUser.update();
        return redirect(routes.UserController.singUp());

    }

    public Result saveBrand(){

        if(currentUser != null){
            if(currentUser.getUserLevel() == 1){
                DynamicForm dynamicForm = formFactory.form().bindFromRequest();
                Brand brand = new Brand(dynamicForm.get("brand"));
                brand.save();
                return redirect("/admin_new_brand ");
            }
        }
        currentUser.setUserLevel(-1);
        currentUser.update();
        return redirect(routes.UserController.singUp());

    }


    public Result listReclaimTitles(){
        if(currentUser != null){
            if(currentUser.getUserLevel() == 1){
                return ok(add_reclaim_titles.render(ReclaimTitle.find.all(), formFactory.form(ReclaimTitle.class)));

            }
        }
        currentUser.setUserLevel(-1);
        currentUser.update();
        return redirect(routes.UserController.singUp());
    }

    public Result saveReclaimTitle(){
        if(currentUser != null){
            if(currentUser.getUserLevel() == 1){
                DynamicForm dynamicForm = formFactory.form().bindFromRequest();
                ReclaimTitle title = new ReclaimTitle(dynamicForm.get("title"));
                title.save();
                return redirect("/titles");
            }
        }
        currentUser.setUserLevel(-1);
        currentUser.update();
        return redirect(routes.UserController.singUp());


    }

    public Result deleteReclaimTitle(Long id){

        if(currentUser != null){
            if(currentUser.getUserLevel() == 1){
                ReclaimTitle title = ReclaimTitle.find.byId(id);
                title.delete();
                return redirect("/titles");
            }
        }
        currentUser.setUserLevel(-1);
        currentUser.update();
        return redirect(routes.UserController.singUp());

    }

    public Result addBacground(){
        if(currentUser != null){
            if(currentUser.getUserLevel() == 1){
                return ok(add_background_image.render(Image.getImagesForBackground()));
            }
        }
        currentUser.setUserLevel(-1);
        currentUser.update();
        return redirect(routes.UserController.singUp());

    }
}
