/**
 * Created by Enver on 11/25/2016.
 */

//PATTERNS
var usernameRegex = /^[a-z]{4,20}$/;
var nameRegex = /^([a-zA-Z]+\s?){2,30}$/;
var phoneRegex = /^(([+]{1}|[0]{1})([0-9]{8,17}))$/;
var passwordRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s).{8,15}$/;
var emailRegex = /^[a-zA-Z0-9\-_]+(\.[a-zA-Z0-9\-_]+)*@[a-z0-9]+(\-[a-z0-9]+)*(\.[a-z0-9]+(\-[a-z0-9]+)*)*\.[a-z]{2,4}$/;
//IDs
var usernameId = "username";
var emailId = "email";
var passwordId = "password";
var passwordAgainId = "password_again";
var firstNameId = "first_name";
var lastNameId = "last_name";
var locatinId = "location";
var phoneNumberId = "phone_number";
var birthDateId = "birth_date";
var submitBtn = "signupSubmit";


//MAIN VALIDATE FUNCTION
function inputValidate(id) {
    var element = document.getElementById(id);
    if (id == usernameId) {
        validateUsername(element);
    }
    if (id == emailId) {
        validateEmail(element);
    }
    if (id == passwordId) {
        validatePassword(element);
    }
    if (id == passwordAgainId) {
        validatePasswordAgain(element);
    }
    if (id == firstNameId) {
        validateName(element);
    }
    checkAll();

}


function validateName(element) {
    var valid = true;
    var name = element.value;
    if (checkExp(name, nameRegex)) {
        inputSuccess(element);
    } else {
        inputError(element);
        valid = false;
    }
    return valid;
}

function validateUsername(element) {
    if (checkExp(element.value, usernameRegex)) {
        inputSuccess(element);
    } else {
        inputError(element);
    }
}

function validateEmail(element) {
    if (checkExp(element.value, emailRegex)) {
        inputSuccess(element);
    } else {
        inputError(element);
    }
}

function validatePassword(element) {
    var password = element.value;
    if (checkExp(password, passwordRegex)) {
        inputSuccess(element);
    } else {
        setPlaceholder(passwordId, "[A-Za-z0-9]+-*/_");
        inputError(element);
        alert("Password must contains Uppercase letter, Lowrecasse letter, and special character +-/_.");
    }
    blankInput(passwordAgainId);
}

function validatePasswordAgain(element) {
    var passwordAgain = element.value;
    var password = document.getElementById(passwordId).value;
    if (checkExp(password, passwordRegex)) {
        if (passwordAgain == password) {
            inputSuccess(element);
        } else {
            setPlaceholder(passwordAgainId, "Passwords are different");
            inputError(element);
            blankInput(passwordAgainId);
            document.getElementById(passwordAgainId).focus();
        }
    } else {
        blankInput(passwordAgainId);
        document.getElementById(passwordId).focus();
    }
}

function validateName(element) {
    var name = element.value;
    if (checkExp(name, nameRegex)) {
        inputSuccess(element);
    } else {
        inputError(element);
    }
}

/*
 Help functions
 */


function checkAll() {
    var inputDisabled = false;
    /*
     if (!checkExp(document.getElementById(usernameId).value, usernameRegex)) {
     inputDisabled = true;
     }
     if (!checkExp(document.getElementById(emailId).value, emailRegex)) {
     inputDisabled = true;
     }

     if (!checkPasswords()) {
     inputDisabled = true;
     }

     if (!checkExp(document.getElementById(firstNameId).value, nameRegex)) {
     inputDisabled = true;
     }

     if (!checkExp(document.getElementById(lastNameId).value, nameRegex)) {
     inputDisabled = true;
     }

     if (!checkExp(document.getElementById(locatinId).value, nameRegex)) {
     inputDisabled = true;
     }

    if (!checkExp(document.getElementById(phoneNumberId).value, phoneRegex)) {
        inputDisabled = true;
    }
*/
    /*
     */
    if(!ageControl(document.getElementById(birthDateId).value)){
        inputDisabled = true;
    }


    document.getElementById(submitBtn).disabled = inputDisabled;
    if (inputDisabled) {
        document.getElementById(submitBtn).classList.remove("btn-success");
        document.getElementById(submitBtn).classList.add("btn-danger");
    } else {
        document.getElementById(submitBtn).classList.remove("btn-danger");
        document.getElementById(submitBtn).classList.add("btn-success");
    }

}


function checkPasswords() {
    var password = document.getElementById(passwordId).value;
    var passAgain = document.getElementById(passwordAgainId).value;
    if (checkExp(password, passwordRegex)) {
        if (passAgain == password) {
            return true;
        }
    }
    return false;
}


function blankInput(id) {
    var element = document.getElementById(id);
    element.value = "";
    element.classList.remove("error-class");
    element.classList.remove("success-class");
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

function ageControl(birthDate) {
    document.getElementById("info_reg").innerHTML = " " + birthDate;
    var bd = birthDate.split("-");
    var bDay = parseInt(bd[2]);
    var bMonth = parseInt(bd[1]);
    var bYear = parseInt(bd[0]);

    var dateToday = new Date();

    var dayToday = dateToday.getDate();
    var monthToday = dateToday.getMonth() +1;
    var yearToday = dateToday.getFullYear();


    if (bYear > (yearToday - 80) && (bYear + 18) < yearToday) {
        return true;
    }

    if (bYear == (yearToday - 80)) {
        if (bMonth > monthToday) {
            return true;
        } else if (bMonth == monthToday) {
            if (bDay > dayToday) {
                return true;
            }
        }
    }

    if ((bYear + 18) == yearToday) {
        if (bMonth < monthToday) {
            return true;
        } else if (bMonth == monthToday) {
            if (bDay < dayToday) {
                return true;
            }
        }
    }


    return false;





}
