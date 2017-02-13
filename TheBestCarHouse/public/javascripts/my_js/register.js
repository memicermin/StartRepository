/**
 * Created by Enver on 11/25/2016.
 */
var usernameRegex = /^[a-z]{4,20}$/;
var nameRegex = /^([a-zA-Z]+\s?){2,30}$/;
var phoneRegex = /^(([+]{1}|[0]{1})([0-9]{8,17}))$/;
var passwordRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s).{8,15}$/;
var emailRegex = /^[a-zA-Z0-9\-_]+(\.[a-zA-Z0-9\-_]+)*@[a-z0-9]+(\-[a-z0-9]+)*(\.[a-z0-9]+(\-[a-z0-9]+)*)*\.[a-z]{2,4}$/;


function inputValidate(id) {
    checkAll();
    var element = document.getElementById(id);
    if (id == "username") {
        validateUsername(element);
    }
    if (id == "email") {
        validateEmail(element);
    }
    if(id == "password"){
        validatePassword(element);
    }if(id == "password_again"){
        validatePasswordAgain(element);
    }
}

function checkAll(){
    var valid = false;
    if(!validateUsername(document.getElementById("username"))){
        valid = true;
    }
    if(!validateEmail(document.getElementById("email"))){
        valid = true;
    }
    if(!validatePasswordAgain(document.getElementById("password_again"))){
        valid = true;
    }

    document.getElementById("signupSubmit").disabled = valid;
}

function validateUsername(element) {
    var valid = true;
    var val = element.value;

    if (val != "") {
        if (val.length < 3) {
            setPlaceholder("username", "min 3 char");
            valid = false;
        }
        if (val.length > 18) {
            setPlaceholder("username", "max 18 char");
            valid = false;
        }
        if (containSpace(val)) {
            setPlaceholder("username", "You can not use \"space\"");
            valid = false;
        }
    } else {
        setPlaceholder("username", "This field is required");
        valid = false;
    }

    if (valid != true) {
        inputError(element);
        return false;
    } else {
        inputSuccess(element);
        return true;
    }


}



function validateEmail(element) {
    var email = element.value;
    if (checkExp(email, emailRegex)) {
        inputSuccess(element);
        return true;
    } else {
        inputError(element);
        return false;
    }
}

function validatePassword(element) {
    var password = element.value;
    if (checkExp(password, passwordRegex)) {
        inputSuccess(element);
    } else {
        setPlaceholder("password", "[A-Za-z0-9]+-*/_");
        inputError(element);
    }
}

function validatePasswordAgain(element) {
    var passwordAgain = element.value;
    var password = document.getElementById("password").value;
    if (checkExp(password, passwordRegex)) {
        if (password != passwordAgain) {
            inputError(element);
            setPlaceholder("password_again", "Passwords are different");
            return false;
        } else {
            inputSuccess(element);
            return true;
        }
    } else {
        element.value = "";
      //  document.getElementById("password").focus();
        validatePassword();
        return false;
    }
}

function blankInput(id){
    var element = document.getElementById(id);
    element.value = "";
    element.classList.remove.remove("error-class");
    element.classList.remove.remove("success-class");
}

/*
 Help functions
 */

function setPlaceholder(id, text) {
    document.getElementById(id).value = "";
    document.getElementById(id).placeholder = text;
}

function containSpace(val) {
    var isSpace = false;
    for (var i = 0; i < val.length; i++) {
        if (val[i] == ' ') {
            isSpace = true;
        }
    }
    return isSpace;
}

function checkExp(exp, re) {
    return re.test(exp);
}

function inputSuccess(element) {
    element.classList.add("success-class");
    element.classList.remove("error-class");
}
function inputError(element) {
    element.classList.add("error-class");
    element.classList.remove("success-class");
}