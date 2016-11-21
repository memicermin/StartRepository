package models.help_models;

import helpers.DateTimeHelper;
import play.data.DynamicForm;
import play.data.validation.Constraints;
import resources.ValidationMessages;

/**
 * Created by Enver on 11/8/2016.
 */

public class UserForRegister {



    @Constraints.MinLength(value = 3, message = ValidationMessages.NAME_MIN)
    @Constraints.MaxLength(value = 40, message = ValidationMessages.NAME_MAX)
    private String username;

    @Constraints.Required(message = ValidationMessages.PASSWORD_REQUIRED)
    @Constraints.Pattern(value = utils.Patterns.PASSWORD, message = ValidationMessages.PASSWORD_PATTERN)
    protected String password;

    private String passwordRetyped;

    @Constraints.Required(message = ValidationMessages.EMAIL_REQUIRED)
    @Constraints.Pattern(value = utils.Patterns.EMAIL, message = ValidationMessages.EMAIL_PATTERN)
    private String email;


    @Constraints.MinLength(value = 3, message = ValidationMessages.NAME_MIN)
    @Constraints.MaxLength(value = 40, message = ValidationMessages.NAME_MAX)
    private String firstName;

    @Constraints.MinLength(value = 3, message = ValidationMessages.NAME_MIN)
    @Constraints.MaxLength(value = 40, message = ValidationMessages.NAME_MAX)
    private String lastName;

    private String birthDate;
    private Integer gender;
    private String location;
    private String phoneNumber;

    public UserForRegister() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordRetyped() {
        return passwordRetyped;
    }

    public void setPasswordRetyped(String passwordRetyped) {
        this.passwordRetyped = passwordRetyped;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public static UserForRegister createUser(DynamicForm dynamicForm){

        UserForRegister userForRegister =new UserForRegister();
        userForRegister.setUsername(dynamicForm.get("username"));
        userForRegister.setEmail(dynamicForm.get("email"));
        userForRegister.setPassword(dynamicForm.get("password"));
        userForRegister.setFirstName(dynamicForm.get("first_name"));
        userForRegister.setLastName(dynamicForm.get("last_name"));

        userForRegister.setBirthDate(DateTimeHelper.formatedBirthDate(dynamicForm.get("birth_date")));

        userForRegister.setLocation(dynamicForm.get("location"));
        userForRegister.setPhoneNumber(dynamicForm.get("phone_number"));

        if(dynamicForm.get("gender").equals("male")){
            userForRegister.setGender(1);
        }else if(dynamicForm.get("gender").equals("female")){
            userForRegister.setGender(2);
        }else if(dynamicForm.get("gender").equals("other")){
            userForRegister.setGender(3);
        }else{
            userForRegister.setGender(0);
        }
        return userForRegister;

    }

}