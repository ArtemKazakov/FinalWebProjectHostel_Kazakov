var date = new Date();
var dd = date.getDate();
var mm = date.getMonth()+1;
var yyyy = date.getFullYear();
if(dd<10){
    dd='0'+dd
}
if(mm<10){
    mm='0'+mm
}

date = yyyy+'-'+mm+'-'+dd;
document.getElementById("date").setAttribute("min", date);

yyyy = yyyy + 1;
date = yyyy+'-'+mm+'-'+dd;
document.getElementById("date").setAttribute("max", date);