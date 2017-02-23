package models.users.help_user_models;

import play.data.DynamicForm;
import play.data.validation.Constraints;
import resources.Patterns;
import resources.ValidationMessages;

/**
 * Created by Enver on 1/22/2017.
 */
public class UserForRegister {

    @Constraints.MinLength(value = 3, message = ValidationMessages.USERNAME_MIN)
    @Constraints.MaxLength(value = 40, message = ValidationMessages.USERNAME_MAX)
    public String username;

    @Constraints.Required(message = ValidationMessages.EMAIL_REQUIRED)
    @Constraints.Pattern(value = Patterns.EMAIL, message = ValidationMessages.EMAIL_PATTERN)
    public String email;

    @Constraints.Required(message = ValidationMessages.PASSWORD_REQUIRED)
    @Constraints.Pattern(value = Patterns.PASSWORD, message = ValidationMessages.PASSWORD_PATTERN)
    public String password;
    public String passwordRetyped;

    @Constraints.MinLength(value = 3, message = ValidationMessages.NAME_MIN)
    @Constraints.MaxLength(value = 40, message = ValidationMessages.NAME_MAX)
    public String firsName;

    @Constraints.MinLength(value = 3, message = ValidationMessages.NAME_MIN)
    @Constraints.MaxLength(value = 40, message = ValidationMessages.NAME_MAX)
    public String lastName;

    public String birthDate;
    public String location;
    public String phoneNumber;
    public  int   gender;

    public static UserForRegister createUser(DynamicForm df){
        UserForRegister ufr = new UserForRegister();
        ufr.username = df.get("username");
        ufr.email = df.get("email");
        ufr.password = df.get("password");
        ufr.firsName = df.get("first_name");
        ufr.lastName = df.get("last_name");
        ufr.birthDate = df.get("birth_date");
        ufr.location = df.get("location");
        ufr.phoneNumber = df.get("phone_number");
        if(df.get("gender").equals("male")){
            ufr.gender = 1;
        }else if(df.get("gender").equals("female")){
            ufr.gender = 2;
        }else if(df.get("gender").equals("other")){
            ufr.gender = 3;
        }else{
            ufr.gender = 0;
        }
        return ufr;
    }
}
