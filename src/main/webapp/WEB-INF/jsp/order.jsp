<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 10.11.2016
  Time: 0:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setLocale value="${requestScope.selectedLanguage}"/>
<f:setBundle basename="locale" var="locale"/>
<f:message bundle="${locale}" key="locale.siteName" var="siteName"/>
<f:message bundle="${locale}" key="locale.mainPage" var="mainPage"/>
<f:message bundle="${locale}" key="locale.login" var="login"/>
<f:message bundle="${locale}" key="locale.password" var="password"/>
<f:message bundle="${locale}" key="locale.repeatPassword" var="repeatPassword"/>
<f:message bundle="${locale}" key="locale.loginButton" var="loginButton"/>
<f:message bundle="${locale}" key="locale.logoutButtom" var="logoutButton"/>
<f:message bundle="${locale}" key="locale.signupButton" var="signupButton"/>
<f:message bundle="${locale}" key="locale.account" var="accountButton"/>
<f:message bundle="${locale}" key="locale.findSuitableRoomsButton" var="findSuitableRoomsButton"/>
<f:message bundle="${locale}" key="locale.rooms" var="rooms"/>
<f:message bundle="${locale}" key="locale.serverError" var="serverError"/>
<f:message bundle="${locale}" key="locale.personalInformation" var="personalInformation"/>
<f:message bundle="${locale}" key="locale.emptyField" var="emptyField"/>
<f:message bundle="${locale}" key="locale.invalidDaysStayNumber" var="invalidDaysStayNumber"/>
<f:message bundle="${locale}" key="locale.invalidSeatsNumber" var="invalidSeatsNumber"/>
<f:message bundle="${locale}" key="locale.seatsNumber" var="seatsNumber"/>
<f:message bundle="${locale}" key="locale.checkInDate" var="chekInDate"/>
<f:message bundle="${locale}" key="locale.daysNumber" var="daysNumber"/>
<f:message bundle="${locale}" key="locale.orderRoom" var="orderRoom"/>
<f:message bundle="${locale}" key="locale.number" var="number"/>
<f:message bundle="${locale}" key="locale.perdayCost" var="perdayCost"/>
<f:message bundle="${locale}" key="locale.changeParamButton" var="changeParamButton"/>
<f:message bundle="${locale}" key="locale.reservation" var="reservation"/>
<f:message bundle="${locale}" key="locale.fullPayment" var="fullPayment"/>
<f:message bundle="${locale}" key="locale.value" var="value"/>
<f:message bundle="${locale}" key="locale.discounts" var="sales"/>
<f:message bundle="${locale}" key="locale.makeOrderButton" var="makeOrderButton"/>
<f:message bundle="${locale}" key="locale.price" var="price"/>
<f:message bundle="${locale}" key="locale.makeOrderSuccess" var="makeOrderSuccess"/>


<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${siteName}</title>
    <link rel="shortcut icon" href="../../img/hostel_icon.png" type="image/png">
    <link rel="stylesheet" href="../../css/bootstrap.css">
    <link rel="stylesheet" href="../../css/style.css">
</head>
<body>

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="Controller?command=mainPage">${mainPage}</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li><a href="Controller?command=viewRoomsList">${rooms}</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <c:if test="${sessionScope.userId == null}">
                    <li>
                        <form class="navbar-form" action="Controller" method="post">
                            <input type="hidden" name="command" value="log_in"/>
                            <div class="form-group">
                                <input type="text" name="logInFormLogin" placeholder="${login}" class="form-control">
                            </div>
                            <div class="form-group">
                                <input type="password" name="logInFormPassword" placeholder="${password}"
                                       class="form-control">
                            </div>
                            <button type="submit" class="btn btn-success">${loginButton}</button>
                        </form>
                    </li>
                    <li><a href="Controller?command=registration"><span
                            class="glyphicon glyphicon-user"></span> ${signupButton}</a></li>
                </c:if>
                <c:if test="${sessionScope.userId != null}">
                    <li>
                        <form class="navbar-form navbar-right" action="Controller" method="get">
                            <input type="hidden" name="command" value="log_out"/>
                            <button type="submit" class="btn btn-success">${logoutButton}</button>
                        </form>
                    </li>
                    <li><a href="Controller?command=userAccount"><span
                            class="glyphicon glyphicon-user"></span> ${accountButton}</a></li>
                </c:if>
                <c:if test='${requestScope.selectedLanguage eq "EN"}'>
                    <li>
                        <form class="navbar-form" id="change-language-ru" action="Controller?command=changeLanguage"
                              method="post"><input type="hidden" name="changeLanguage" value="RU">
                            <button type="submit" class="btn btn-danger">RU</button>
                        </form>
                    </li>
                </c:if>
                <c:if test='${requestScope.selectedLanguage eq "RU"}'>
                    <li>
                        <form class="navbar-form" id="change-language-en" action="Controller?command=changeLanguage"
                              method="post"><input type="hidden" name="changeLanguage" value="EN">
                            <button type="submit" class="btn btn-danger">EN</button>
                        </form>
                    </li>
                </c:if>
            </ul>
        </div>
    </div>
</nav>


<div class="container">
    <div id="order-info">
        <br>
        <ul class="nav nav-tabs">
            <li><a href="Controller?command=userAccount">${personalInformation}</a></li>
            <li class="active">
                <a href="Controller?command=findSuitableRooms">${orderRoom}</a>
            </li>
        </ul>
        <br>
        <div class="row">
            <div class="col-md-6">
                <c:if test="${requestScope.rooms == null}">
                    <form action="Controller" method="get">
                        <input type="hidden" name="command" value="findSuitableRooms"/>
                        <div class="form-group">
                            <label for="date">${chekInDate}:</label>
                            <input name="searchFormCheckInDate" type="date"
                                   max="" min="" class="form-control" id="date" required
                                   oninvalid="this.setCustomValidity('${emptyField}')"
                                   onchange="try{setCustomValidity('')}catch(e){}">
                        </div>
                        <div class="form-group">
                            <label for="daysStayNumber">${daysNumber}:</label>
                            <input name="searchFormDaysStayNumber" type="number" min="1" max="30" class="form-control"
                                   id="daysStayNumber" title="${invalidDaysStayNumber}" required
                                   oninvalid="this.setCustomValidity('${emptyField} ${invalidDaysStayNumber}')"
                                   onchange="try{setCustomValidity('')}catch(e){}">
                        </div>
                        <div class="form-group">
                            <label for="seatsNumber">${seatsNumber}:</label>
                            <input name="searchFormSeatsNumber" type="number" min="1" max="5" class="form-control"
                                   id="seatsNumber"
                                   title="${invalidSeatsNumber}" required
                                   oninvalid="this.setCustomValidity('${emptyField} ${invalidSeatsNumber}')"
                                   onchange="try{setCustomValidity('')}catch(e){}">
                        </div>
                        <div class="form-group">
                            <button type="submit" class="btn btn-default">${findSuitableRoomsButton}</button>
                        </div>
                    </form>
                </c:if>
                <c:if test="${requestScope.rooms != null}">
                    <form action="Controller" method="post">
                        <input type="hidden" name="command" value="makeRentalRequest"/>
                        <div class="form-group">
                            <label for="date2">${chekInDate}:</label>
                            <input name="rentalRequestFormCheckInDate" type="date"
                                   value="${requestScope.checkInDate}"
                                   class="form-control" id="date2" readonly >
                        </div>
                        <div class="form-group">
                            <label for="daysStayNumber2">${daysNumber}:</label>
                            <input name="rentalRequestFormDaysStayNumber" type="number"
                                   class="form-control"
                                   value="${requestScope.daysStayNumber}"
                                   id="daysStayNumber2" title="${invalidDaysStayNumber}" readonly >
                        </div>
                        <div class="form-group">
                            <label for="seatsNumber2">${seatsNumber}:</label>
                            <input name="rentalRequestFormSeatsNumber" type="number"
                                   class="form-control"
                                   id="seatsNumber2" value="${requestScope.seatsNumber}"
                                   title="${invalidSeatsNumber}" readonly >
                        </div>
                        <div class="form-group">
                            <a class="btn btn-default"
                               href="Controller?command=findSuitableRooms">${changeParamButton}</a>
                        </div>
                        <div class="form-group">
                            <label for="suitableRooms">${rooms}:</label>
                            <select name="rentalRequestFormRoom" id="suitableRooms" onchange="recalculatePrice()">
                                <c:forEach var="room" items="${requestScope.rooms}">
                                    <option value="${room.number}">${number}: ${room.number}, ${perdayCost}: ${room.perdayCost}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="reservation">${reservation} (+10%)</label>
                            <input type="radio" checked name="rentalRequestFormType" id="reservation" value="0" onchange="recalculatePrice()"/>
                            <label for="fullPayment">${fullPayment}</label>
                            <input type="radio" name="rentalRequestFormType" id="fullPayment" value="1" onchange="recalculatePrice()"/>
                        </div>
                        <div class="form-group">
                            <label for="discounts">${sales}:</label>
                            <select name="rentalRequestFormDiscount" id="discounts" onchange="recalculatePrice()">
                                <option value="0"></option>
                                <c:forEach var="discount" items="${requestScope.discounts}">
                                    <option value="${discount.id}">${value}: ${discount.value}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="resultPayment">${price}:</label>
                            <input name="rentalRequestFormPayment" type="number"
                                   class="form-control"
                                   id="resultPayment" readonly >
                        </div>
                        <div class="form-group">
                            <button type="submit" class="btn btn-default">${makeOrderButton}</button>
                        </div>
                    </form>
                </c:if>
            </div>
        </div>

        <br>
        <c:if test="${param.serviceError}">
            <div class="alert alert-danger fade in">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    ${serverError}
            </div>
        </c:if>
        <c:if test="${param.makeOrderSuccess}">
            <div class="alert alert-success fade in">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    ${makeOrderSuccess}
            </div>
        </c:if>
    </div>

    <hr>

    <footer>
        <p>&copy; 2016 ${siteName}</p>
    </footer>
</div>

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/datepicker.js"></script>
<script type="text/javascript" src="js/script.js"></script>
</body>
</html>

