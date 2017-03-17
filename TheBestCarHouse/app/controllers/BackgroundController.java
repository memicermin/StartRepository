package controllers;

import com.cloudinary.Cloudinary;
import com.google.inject.Inject;
import helpers.Admin;
import models.help_models.Background;
import models.help_models.Image;

import org.joda.time.DateTime;
import play.Play;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import resources.FieldNames;
import views.html.add.add_background;

import javax.management.DynamicMBean;
import java.io.File;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Enver on 3/14/2017.
 */
@Security.Authenticated(Admin.class)
public class BackgroundController extends Controller {

    @Inject
    FormFactory formFactory;

    public Result addBackground(){
        return ok(add_background.render(formFactory.form(Background.class), Background.find.all()));
    }

    public Result saveBackground(){
        Image.cloudinary = new Cloudinary("cloudinary://" + Play.application().configuration().getString("cloudinary.string"));

        Http.MultipartFormData body = request().body().asMultipartFormData();
        List<Http.MultipartFormData.FilePart> fileParts = body.getFiles();
        System.out.println();
        System.out.println(fileParts.toString());
        System.out.println();
        if (fileParts != null) {
            boolean isFirstImage = true;
            for (Http.MultipartFormData.FilePart filePart : fileParts) {
                try {
                    File file = (File) filePart.getFile();
                    Image image = Image.createBackground(file);
                    image.save();

                    Background bckg;
                    if(isFirstImage){
                        bckg = new Background(1, image);
                        isFirstImage = false;
                    }else{
                        bckg = new Background(0, image);
                    }
                    bckg.save();

                } catch (RuntimeException re) {
                    System.out.println("Imamo pad " + new DateTime().toString() + " -> " + filePart.getFile().toString());
                }
            }
        }
        return ok(add_background.render(formFactory.form(Background.class), Background.find.all()));
    }

    public Result changeBackground(Long id){
        if(Background.setBackgroundById(id)){
            return redirect("/");
        }else {
            return ok(add_background.render(formFactory.form(Background.class), Background.find.all()));
        }
    }
}
