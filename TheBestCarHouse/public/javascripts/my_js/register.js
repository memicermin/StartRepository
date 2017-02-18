/**
 * Created by Enver on 11/25/2016.
 */

//PATTERNS
var usernameRegex = /^[a-z]{4,20}$/;
var nameRegex = /^([a-zA-Z]+\s?){2,30}$/;
var phoneRegex = /^(([+]{1}|[0]{1})([0-9]{8,17}))$/;
var passwordRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s).{8,15}$/;
var emailRegex = /^[a-zA-Z0-9\-_]+(\.[a-zA-Z0-9\-_]+)*@[a-z0-9]+(\-[a-z0-9]+)*(\.[a-z0-9]+(\-[a-z0-9]+)*)*\.[a-z]{2,4}$/;
var ddMMyyyyRegex = /^([0-9]{2})-([0-9]{2})-([0-9]{4})$/;
//IDs
var usernameId = "username";
var emailId = "email";
var passwordId = "password";
var passwordAgainId = "password_again";
var firstNameId = "first_name";
var lastNameId = "last_name";
var locationId = "location";
var phoneNumberId = "phone_number";
var birthDateId = "birth_date";
var submitBtn = "signupSubmit";
//
var MAX_AGE = 80;
var MIN_AGE = 18;

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
    var name = element.value;
    if (checkExp(name, nameRegex)) {
        inputSuccess(element);
    } else {
        inputError(element);
    }
}

/*
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
 */
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

     if (!checkExp(document.getElementById(locationId).value, nameRegex)) {
     inputDisabled = true;
     }

     if (!checkExp(document.getElementById(phoneNumberId).value, phoneRegex)) {
     inputDisabled = true;
     }
     */
    var birthDate = document.getElementById(birthDateId).value;
    if (!isValidDate(birthDate)) {
        document.getElementById("info_reg").innerHTML = "isValidate";
        inputDisabled = true;
    } else {
        if (!ageControl(birthDate)) {
            // document.getElementById("info_reg").innerHTML = "age Control";
            inputDisabled = true;
        }
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
    var bd = birthDate.split("-");
    var birthDay = parseInt(bd[0]);
    var birthMonth = parseInt(bd[1]);
    var birthYear = parseInt(bd[2]);
    var dayToday = new Date().getDate();
    var monthToday = new Date().getMonth() + 1;
    var yearToday = new Date().getFullYear();
    var diffDay;
    var diffMonth;
    var diffYear;

    diffYear = yearToday - birthYear;

    if (birthMonth <= monthToday) {
        diffMonth = monthToday - birthMonth;
    } else {
        diffMonth = (12 - birthMonth) + monthToday;
        diffYear--;
    }
    if (birthDay <= dayToday) {
        diffDay = dayToday - birthDay;
    } else {
        if (birthMonth == 1 || birthMonth == 3 || birthMonth == 5 || birthMonth == 7 || birthMonth == 8 || birthMonth == 10 || birthMonth == 12) {
            diffDay = (31 - birthDay) + dayToday;
        }
        if (birthMonth == 4 || birthMonth == 6 || birthMonth == 9 || birthMonth == 11) {
            diffDay = (30 - birthDay) + dayToday;
        }
        if (birthMonth == 2) {
            diffDay = (29 - birthDay) + dayToday;
        }
        if (diffMonth == 0) {
            diffYear--;
        } else {
            diffMonth--;
        }
    }

    //document.getElementById("info_reg").innerHTML = document.getElementById("info_reg").value + "years = " + diffYear + ": months = " +diffMonth + ": days = " + diffDay;
    // return diffDay + "-" + diffMonth + "-" + diffYear;

    // document.getElementById("info_reg").innerHTML = document.getElementById("info_reg").value + "years = " + years + ": months = " + months + ": days = " + days;
    if (diffYear > MIN_AGE && diffYear < MAX_AGE) {
       // document.getElementById("info_reg").innerHTML = "odma vracam true";
        return true;
    } else if (diffYear == MIN_AGE) {
        if ((diffYear - diffMonth - diffDay) == MIN_AGE) {
            return true;
        } else {
            return false;
        }
    } else if (diffYear == MAX_AGE) {
        if ((diffYear + diffMonth + diffDay) == MAX_AGE) {
            return true;
        } else {
            return false;
        }
    } else {
        return false;
    }
}

function calculateAge(birthDate) {
    var bd = birthDate.split("-");
    var birthDay = parseInt(bd[0]);
    var birthMonth = parseInt(bd[1]);
    var birthYear = parseInt(bd[2]);
    var dayToday = new Date().getDate();
    var monthToday = new Date().getMonth() + 1;
    var yearToday = new Date().getFullYear();
    var diffDay;
    var diffMonth;
    var diffYear;

    diffYear = yearToday - birthYear;

    if (birthMonth <= monthToday) {
        diffMonth = monthToday - birthMonth;
    } else {
        diffMonth = (12 - birthMonth) + monthToday;
        diffYear--;
    }
    if (birthDay <= dayToday) {
        diffDay = dayToday - birthDay;
    } else {
        if (birthMonth == 1 || birthMonth == 3 || birthMonth == 5 || birthMonth == 7 || birthMonth == 8 || birthMonth == 10 || birthMonth == 12) {
            diffDay = (31 - birthDay) + dayToday;
        }
        if (birthMonth == 4 || birthMonth == 6 || birthMonth == 9 || birthMonth == 11) {
            diffDay = (30 - birthDay) + dayToday;
        }
        if (birthMonth == 2) {
            diffDay = (29 - birthDay) + dayToday;
        }
        if (diffMonth == 0) {
            diffYear--;
        } else {
            diffMonth--;
        }
    }

    if (diffDay.length == 1) {
        diffDay = "0" + diffDay;
    }
    if (diffMonth.length == 1) {
        diffMonth = "0" + diffMonth;
    }

    // document.getElementById("info_reg").innerHTML = document.getElementById("info_reg").value + "years = " + diffYear + ": months = " +diffMonth + ": days = " + diffDay;
    return diffDay + "-" + diffMonth + "-" + diffYear;
}

function isValidDate(birthDate) {
    var bd = birthDate.split("-");
    var day = parseInt(bd[0]);
    var month = parseInt(bd[1]);
    var year = parseInt(bd[2]);
    var valid = true;

    if (day < 1 || month < 1 || year < 1) {
        valid = false;
    }
    if (month > 12) {
        valid = false;
    }
    if (year > new Date().getFullYear()) {
        valid = false;
    }
    if (year == new Date().getFullYear()) {
        if (month > new Date().getMonth() + 1) {
            valid = false;
        }
        if (month == new Date().getMonth() + 1) {
            if (day > new Date().getDate()) {
                valid = false;
            }
        }
    }
    if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
        if (day > 31) {
            valid = false;
        }
    }
    if (month == 4 || month == 6 || month == 9 || month == 11) {
        if (day > 30) {
            valid = false;
        }
    }
    if (month == 2) {
        if (day > 29) {
            valid = false;
        }
    }
    return valid;
}