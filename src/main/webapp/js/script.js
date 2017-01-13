function checkPassword(error){
    var result = true;
    PWD_NOT_EQUAL = "<div class=\"alert alert-danger fade in\">"+error+"</div>",

    errPassword =  document.getElementById("err-password"),
    errPassword.innerHTML = "";

    pwd1 = document.getElementById("password").value,
    pwd2 = document.getElementById("repeatPassword").value;

    if (pwd1 && pwd2 && pwd1 !== pwd2) {
      errPassword.innerHTML = PWD_NOT_EQUAL; 
      document.getElementById("password").value = "";
      document.getElementById("repeatPassword").value = "";
      result = false;
    }   

    return result;
}


function recalculatePrice(){
    var price = 0;
    var perDayCost = 0;
    var days = 0;
    var discountValue = 0;

    var room = document.getElementById("suitableRooms").options[document.getElementById("suitableRooms").selectedIndex].text;
    if(room){
        perDayCost = room.substring(room.lastIndexOf(":")+2);
    }

    days = document.getElementById("daysStayNumber2").value;

    price = days * perDayCost ;

    if (document.getElementsByName("rentalRequestFormType")[0].checked) {
        price *= 1.1;
    }

    var discount = document.getElementById("discounts").options[document.getElementById("discounts").selectedIndex].text;
    if(discount){
        discountValue = discount.substring(discount.lastIndexOf(":")+2);
        price -= discountValue;
    }

    price = Math.round(price);

    if(price<0){
        price = 0;
    }

    document.getElementById("resultPayment").value = price;
}

recalculatePrice();
