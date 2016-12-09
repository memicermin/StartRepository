package models.help_models;

import play.data.validation.Constraints;
import resources.ValidationMessages;
import utils.Patterns;

/**
 * Created by Enver on 11/8/2016.
 */
public class UserForLogin {

    @Constraints.Required(message = ValidationMessages.EMAIL_REQUIRED)
    @Constraints.Pattern(value = Patterns.EMAIL, message = ValidationMessages.EMAIL_PATTERN)
    private String email;
    @Constraints.Required(message = ValidationMessages.PASSWORD_REQUIRED)
    @Constraints.Pattern(value = Patterns.PASSWORD, message = ValidationMessages.PASSWORD_PATTERN)
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
