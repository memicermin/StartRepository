package controllers;

import com.cloudinary.Cloudinary;
import com.google.inject.Inject;
import helpers.Admin;
import models.help_models.Brand;
import models.help_models.Image;
import org.joda.time.DateTime;
import play.Logger;
import play.Play;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import resources.FieldNames;
import views.html.add.add_brand;

import java.io.File;
import java.util.List;

/**
 * Created by Enver on 2/23/2017.
 */
@Security.Authenticated(Admin.class)
public class BrandController extends Controller {

    @Inject
    FormFactory formFactory;

    public Result addBrand() {
        return ok(add_brand.render(Brand.getAllBrands(), formFactory.form(Brand.class)));
    }

    public Result saveBrand() {
        DynamicForm dynamicForm = formFactory.form().bindFromRequest();

        Image.cloudinary = new Cloudinary("cloudinary://" + Play.application().configuration().getString("cloudinary.string"));

        Http.MultipartFormData body = request().body().asMultipartFormData();
        List<Http.MultipartFormData.FilePart> fileParts = body.getFiles();

        if (fileParts != null) {
            Http.MultipartFormData.FilePart filePart = fileParts.get(0);
            try {
                File file = (File) filePart.getFile();
                System.out.println(file.getPath());
                Image image = Image.createBackground(file);
                image.save();

                Brand brand = new Brand(dynamicForm.get(FieldNames.BRAND), Integer.parseInt(dynamicForm.get(FieldNames.PART_OF_BRAND)), image);
                brand.save();
            } catch (RuntimeException re) {
                flash("warning", "brand did not save");
                System.out.println("brand did not save");
            }

        }
        return redirect("/add/brand");
    }

    public Result deleteBrand(Long id) {
        Brand.findBrandById(id).delete();
        return redirect("/add/brand");
    }


}
