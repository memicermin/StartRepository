/**
 * Created by Enver on 2/11/2017.
 */

var usernameRegex = /^[a-z]{4,20}$/;
var nameRegex = /^([a-zA-Z]+\s?){2,30}$/;
var phoneRegex = /^(([+]{1}|[0]{1})([0-9]{8,17}))$/;
var passwordRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s).{8,15}$/;

function usernameValidate(id) {
    var username = document.getElementById(id).value;
    if (checkExp(username, usernameRegex)) {
        document.getElementById("username-button").disabled = false;
    } else {
        document.getElementById("username-button").disabled = true;
    }
    if (username.length <= 3) {
        document.getElementById("username_info").innerHTML = "min 4 char";
    } else if (username.length > 20) {
        document.getElementById("username_info").innerHTML = "max 20 char";
    } else {
        document.getElementById("username_info").innerHTML = "Please use only lowercase letters";
    }
}

function nameValidate() {
    var firstName = document.getElementById("first_name").value;
    var lastName = document.getElementById("last_name").value;
    if (checkExp(firstName, nameRegex) && checkExp(lastName, nameRegex)) {
        document.getElementById("name-button").disabled = false;
    } else {
        document.getElementById("name-button").disabled = true;
    }
}

function locationValidate(id) {
    var element = document.getElementById(id);
    if (checkExp(element.value, nameRegex)) {
        document.getElementById("location_button").disabled = false;
    } else {
        document.getElementById("location_button").disabled = true;
    }
}

function phoneNumberValidate(id){
    var element = document.getElementById(id);
    if(checkExp(element.value, phoneRegex)){
        document.getElementById("phone_button").disabled = false;
    }else{
        document.getElementById("phone_button").disabled = true;
    }
}

function regularLengthPassword(id){
    var pass = document.getElementById(id).value;
    if(pass.length >= 6 && pass.length <= 24){
        document.getElementById("password_button").disabled = false;
        document.getElementById("password_info").innerHTML = "";
    }else{
        document.getElementById("password_info").innerHTML = "Password length must be between 6 and 24";
        document.getElementById("password_button").disabled = true;
    }
}

function passwordValidate(id) {
    var element = document.getElementById(id);
    var password = element.value;
    inputAgainBlank();
    if (checkExp(password, passwordRegex)) {
        inputSuccess(element);
        document.getElementById("new_password_info").innerHTML = "Password is valid";
    } else {
        document.getElementById("new_password_info").innerHTML = "Password is NOT valid";
        document.getElementById("password_again_info").innerHTML = "";

        inputError(element);
    }
}

function passwordAgainValidate(id) {
    var element = document.getElementById(id);
    var passwordAgain = element.value;
    var password = document.getElementById("password").value;
    if (checkExp(password, passwordRegex)) {
        if (password != passwordAgain) {
            inputError(element);
            document.getElementById("password_again_info").innerHTML = "Passwords are different";
            document.getElementById("new_pass_submit").disabled = true;
        } else {
            inputSuccess(element);
            document.getElementById("new_pass_submit").disabled = false;
            document.getElementById("password_again_info").innerHTML = "Password OK!";
            document.getElementById("new_pass_submit").focus();
        }
    } else {
        element.value = "";
        document.getElementById("password_again_info").innerHTML = "";
        document.getElementById("password").focus();
        document.getElementById("new_pass_submit").disabled = true;
        passwordValidate("password");
    }
}

function checkExp(exp, re) {
    return re.test(exp);
}

function setPlaceholder(id, text) {
    document.getElementById(id).value = "";
    document.getElementById(id).placeholder = text;
}

function inputSuccess(element) {
    element.classList.add("success-class");
    element.classList.remove("error-class");
}

function inputError(element) {
    element.classList.add("error-class");
    element.classList.remove("success-class");
}

function inputAgainBlank(){
    document.getElementById("password_again").value = "";
    document.getElementById("new_pass_submit").disabled = true;
    document.getElementById("password_again").classList.remove("error-class");
    document.getElementById("password_again").classList.remove("success-class");
}