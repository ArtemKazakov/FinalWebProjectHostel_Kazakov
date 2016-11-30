function checkPassword(error){
    var result = true;
    PWD_NOT_EQUAL = "<div class=\"alert alert-danger fade in\">"+error+"</div>",

    errPassword =  document.getElementById("err-password"),
    errPassword.innerHTML = "";

    pwd1 = document.forms[2]["registrationFormPassword"].value,
    pwd2 = document.forms[2]["registrationFormRepeatPassword"].value;

    if (pwd1 && pwd2 && pwd1 !== pwd2) {
      errPassword.innerHTML = PWD_NOT_EQUAL; 
      document.forms[2]["registrationFormPassword"].value = "";
      document.forms[2]["registrationFormRepeatPassword"].value = "";
      result = false;
    }   

    return result;
  }
