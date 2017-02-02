<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 10.11.2016
  Time: 0:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setLocale value="${requestScope.selectedLanguage}"/>
<f:setBundle basename="local" var="local"/>
<f:message bundle="${local}" key="local.siteName" var="siteName"/>
<f:message bundle="${local}" key="local.login" var="login"/>
<f:message bundle="${local}" key="local.password" var="password"/>
<f:message bundle="${local}" key="local.repeatPassword" var="repeatPassword"/>
<f:message bundle="${local}" key="local.findSuitableRoomsButton" var="findSuitableRoomsButton"/>
<f:message bundle="${local}" key="local.rooms" var="rooms"/>
<f:message bundle="${local}" key="local.serverError" var="serverError"/>
<f:message bundle="${local}" key="local.personalInformation" var="personalInformation"/>
<f:message bundle="${local}" key="local.emptyField" var="emptyField"/>
<f:message bundle="${local}" key="local.invalidDaysStayNumber" var="invalidDaysStayNumber"/>
<f:message bundle="${local}" key="local.invalidSeatsNumber" var="invalidSeatsNumber"/>
<f:message bundle="${local}" key="local.seatsNumber" var="seatsNumber"/>
<f:message bundle="${local}" key="local.checkInDate" var="chekInDate"/>
<f:message bundle="${local}" key="local.daysNumber" var="daysNumber"/>
<f:message bundle="${local}" key="local.orderRoom" var="orderRoom"/>
<f:message bundle="${local}" key="local.number" var="number"/>
<f:message bundle="${local}" key="local.perdayCost" var="perdayCost"/>
<f:message bundle="${local}" key="local.changeParamButton" var="changeParamButton"/>
<f:message bundle="${local}" key="local.reservation" var="reservation"/>
<f:message bundle="${local}" key="local.fullPayment" var="fullPayment"/>
<f:message bundle="${local}" key="local.value" var="value"/>
<f:message bundle="${local}" key="local.discounts" var="sales"/>
<f:message bundle="${local}" key="local.makeOrderButton" var="makeOrderButton"/>
<f:message bundle="${local}" key="local.price" var="price"/>
<f:message bundle="${local}" key="local.makeOrderSuccess" var="makeOrderSuccess"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${siteName} - ${orderRoom}</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/hostel_icon.png" type="image/png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<c:import url="template/header.jsp"/>

<div class="container">
    <div id="order-info">
        <br>
        <ul class="nav nav-tabs">
            <li><a href="${pageContext.request.contextPath}/Controller?command=userAccount">${personalInformation}</a>
            </li>
            <li class="active">
                <a href="${pageContext.request.contextPath}/Controller?command=findSuitableRooms">${orderRoom}</a>
            </li>
        </ul>
        <br>
        <div class="row">
            <div class="col-md-6">
                <c:if test="${requestScope.rooms == null}">
                    <form action="${pageContext.request.contextPath}/Controller" method="get">
                        <input type="hidden" name="command" value="findSuitableRooms"/>
                        <div class="form-group">
                            <label for="date">${chekInDate}:</label>
                            <div class="input-group">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                                <input name="searchFormCheckInDate" type="date"
                                       max="" min="" class="form-control" id="date" required
                                       oninvalid="this.setCustomValidity('${emptyField}')"
                                       onchange="try{setCustomValidity('')}catch(e){}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="daysStayNumber">${daysNumber}:</label>
                            <div class="input-group">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-pencil"></i></span>
                                <input name="searchFormDaysStayNumber" type="number" min="1" max="30"
                                       class="form-control"
                                       id="daysStayNumber" title="${invalidDaysStayNumber}" required
                                       oninvalid="this.setCustomValidity('${emptyField} ${invalidDaysStayNumber}')"
                                       onchange="try{setCustomValidity('')}catch(e){}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="seatsNumber">${seatsNumber}:</label>
                            <div class="input-group">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-cog"></i></span>
                                <input name="searchFormSeatsNumber" type="number" min="1" max="5" class="form-control"
                                       id="seatsNumber"
                                       title="${invalidSeatsNumber}" required
                                       oninvalid="this.setCustomValidity('${emptyField} ${invalidSeatsNumber}')"
                                       onchange="try{setCustomValidity('')}catch(e){}">
                            </div>
                        </div>
                        <div class="form-group">
                            <button type="submit" class="btn btn-default">${findSuitableRoomsButton}</button>
                        </div>
                    </form>
                </c:if>
                <c:if test="${requestScope.rooms != null}">
                    <form action="${pageContext.request.contextPath}/Controller" method="post">
                        <input type="hidden" name="command" value="makeRentalRequest"/>
                        <div class="form-group">
                            <label for="date2">${chekInDate}:</label>
                            <div class="input-group">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                                <input name="rentalRequestFormCheckInDate" type="date"
                                       value="${requestScope.checkInDate}"
                                       class="form-control" id="date2" readonly>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="daysStayNumber2">${daysNumber}:</label>
                            <div class="input-group">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-pencil"></i></span>
                                <input name="rentalRequestFormDaysStayNumber" type="number"
                                       class="form-control"
                                       value="${requestScope.daysStayNumber}"
                                       id="daysStayNumber2" title="${invalidDaysStayNumber}" readonly>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="seatsNumber2">${seatsNumber}:</label>
                            <div class="input-group">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-cog"></i></span>
                                <input name="rentalRequestFormSeatsNumber" type="number"
                                       class="form-control"
                                       id="seatsNumber2" value="${requestScope.seatsNumber}"
                                       title="${invalidSeatsNumber}" readonly>
                            </div>
                        </div>
                        <div class="form-group">
                            <a class="btn btn-default"
                               href="${pageContext.request.contextPath}/Controller?command=findSuitableRooms">${changeParamButton}</a>
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
                            <input type="radio" checked name="rentalRequestFormType" id="reservation" value="0"
                                   onchange="recalculatePrice()"/>
                            <label for="fullPayment">${fullPayment}</label>
                            <input type="radio" name="rentalRequestFormType" id="fullPayment" value="1"
                                   onchange="recalculatePrice()"/>
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
                            <div class="input-group">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-usd"></i></span>
                                <input name="rentalRequestFormPayment" type="number"
                                       class="form-control"
                                       id="resultPayment" readonly>
                            </div>
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

</div>

<c:import url="template/footer.jsp"/>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/datepicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>

