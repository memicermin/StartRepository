/**
 * Created by Enver on 11/25/2016.
 */

function inputValidate(id) {

    var element = document.getElementById(id);

    if (id == "username") {
        validateUsername(element);
    }
    if (id == "email") {
        validateEmail(element);
    }

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

    } else {
        inputSuccess(element);
    }


}

function validateEmail(element) {
    var email = element.value;
    if (validateEmailPattern(email)) {
        inputSuccess(element);
    } else {
        inputError(element);
    }
}

function validatePassword() {
    var element = document.getElementById("password");
    var password = element.value;
    if (checkPassword(password)) {
        inputSuccess(element);
    } else {
        setPlaceholder("password", "[A-Za-z0-9]+-*/_");
        inputError(element);
    }
}

function validatePasswordAgain() {
    var element = document.getElementById("password_again");
    var passwordAgain = element.value;
    var password = document.getElementById("password").value;
    if (checkPassword(password)) {
        if (password != passwordAgain) {
            inputError(element);
            setPlaceholder("password_again", "Passwords are different");
        } else {
            inputSuccess(element);
        }
    } else {
        element.value = "";
        document.getElementById("password").focus();
        validatePassword();
    }

}

/*
 Help functions
 */

function checkPassword(password) {
    var pattern = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s).{8,15}$/;
    if (pattern.test(password)) {
        return true;
    }
    return false;
}

function validateEmailPattern(email) {
    var re = /^[a-zA-Z0-9\-_]+(\.[a-zA-Z0-9\-_]+)*@[a-z0-9]+(\-[a-z0-9]+)*(\.[a-z0-9]+(\-[a-z0-9]+)*)*\.[a-z]{2,4}$/;
    return re.test(email);
}

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

function inputSuccess(element) {
    element.classList.add("success-class");
    element.classList.remove("error-class");
}
function inputError(element) {
    element.classList.add("error-class");
    element.classList.remove("success-class");
}