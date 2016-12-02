package controllers;

import com.cloudinary.Cloudinary;
import com.google.inject.Inject;
import helpers.Help;
import helpers.SessionHelper;
import models.*;

import play.Play;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import views.html.admin_view.*;

import java.io.File;
import java.util.List;


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
            currentUser.setUserLevel(-1);
            currentUser.update();
        }
        return redirect("sing-up-...");
    }


    public Result newBrand(){
        if(currentUser != null){
            if(currentUser.getUserLevel() == 1){
                return ok(add_brand.render(Brand.find.all(), formFactory.form(Brand.class)));
            }
            currentUser.setUserLevel(-1);
            currentUser.update();
        }

        return redirect("sing-up-...");

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
        return redirect("sing-up-...");

    }


    public Result listReclaimTitles(){
        if(currentUser != null){
            if(currentUser.getUserLevel() == 1){
                return ok(add_reclaim_titles.render(ReclaimTitle.find.all(), formFactory.form(ReclaimTitle.class)));

            }
        }
        currentUser.setUserLevel(-1);
        currentUser.update();
        return redirect("sing-up-...");
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
        return redirect("sing-up-...");


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
        return redirect("sing-up-...");

    }

    public Result addBacground(){
        if(currentUser != null){
            if(currentUser.getUserLevel() == 1){
                return ok(add_background_image.render(Image.getImagesForBackground(), formFactory.form(Image.class)));

            }
        }
        currentUser.setUserLevel(-1);
        currentUser.update();
        return redirect("sing-up-...");

    }

    public Result testBackground(Long id){
        if(currentUser != null){
            if(currentUser.getUserLevel() == 1){
                Image.setTestImage(id);
                return ok(add_background_image.render(Image.getImagesForBackground(), formFactory.form(Image.class)));

            }
        }
        currentUser.setUserLevel(-1);
        currentUser.update();
        return redirect("sing-up-...");
    }

    public Result addBackgroundImages(){
        if(currentUser != null){
            if(currentUser.getUserLevel() == 1){
                Image.cloudinary = new Cloudinary("cloudinary://" + Play.application().configuration().getString("cloudinary.string"));

                Http.MultipartFormData body = request().body().asMultipartFormData();
                List<Http.MultipartFormData.FilePart> fileParts = body.getFiles();
                if (fileParts != null) {
                    for (Http.MultipartFormData.FilePart filePart : fileParts) {
                        try {


                            File file = (File) filePart.getFile();

                            Image image = Image.create(file, null, -1);

                            image.save();


                        } catch (RuntimeException re) {

                        }
                    }
                }
                return ok(add_background_image.render(Image.getImagesForBackground(), formFactory.form(Image.class)));

            }
        }
        currentUser.setUserLevel(-1);
        currentUser.update();
        return redirect("sing-up-...");
    }

    public Result changeBackground(Long id){
        if(currentUser != null){
            if(currentUser.getUserLevel() == 1){
                List<Image> backgroundImages = Image.getFind().where().eq("using_type", -1).findList();
                if(backgroundImages != null){
                    for(Image i : backgroundImages){
                        if(i.getBackgroundActive() == 2){
                            i.setBackgroundActive(1);
                            i.update();
                        }
                    }
                }
                Image nextBackground = Image.getFind().byId(id +"");
                if(nextBackground != null){
                    nextBackground.setBackgroundActive(2);
                    nextBackground.update();
                }
                return ok(add_background_image.render(Image.getImagesForBackground(), formFactory.form(Image.class)));

            }
        }
        currentUser.setUserLevel(-1);
        currentUser.update();
        return redirect("sing-up-...");
    }

    public Result deleteBackgroundImage(Long id){
        if(currentUser != null){
            if(currentUser.getUserLevel() == 1){
                Image.getFind().byId(id + "").delete();
                return ok(add_background_image.render(Image.getImagesForBackground(), formFactory.form(Image.class)));

            }
        }
        currentUser.setUserLevel(-1);
        currentUser.update();
        return redirect("sing-up-...");
    }

    public Result editProduct(Long id) {
        return ok(edit_sale.render(formFactory.form(Sale.class), Brand.getAllBrands(), Help.getLastHundredYears(), Sale.getSaleById(id)));
    }

/*
    public Result deleteBackgroundImage(Long id){
        if(currentUser != null){
            if(currentUser.getUserLevel() == 1){
                <!-- -Todo- -->

            }
        }
        currentUser.setUserLevel(-1);
        currentUser.update();
        return redirect("sing-up-...");
    }

 */


}
