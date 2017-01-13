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
<f:message bundle="${locale}" key="locale.rooms" var="rooms"/>
<f:message bundle="${locale}" key="locale.wrongLogin" var="wrongLogin"/>
<f:message bundle="${locale}" key="locale.wrongPassword" var="wrongPassword"/>
<f:message bundle="${locale}" key="locale.editSuccess" var="editSuccess"/>
<f:message bundle="${locale}" key="locale.serverError" var="serverError"/>
<f:message bundle="${locale}" key="locale.passportIdNumber" var="passportIdNumber"/>
<f:message bundle="${locale}" key="locale.passportSeries" var="passportSeries"/>
<f:message bundle="${locale}" key="locale.surname" var="surname"/>
<f:message bundle="${locale}" key="locale.name" var="name"/>
<f:message bundle="${locale}" key="locale.patronymic" var="patronymic"/>
<f:message bundle="${locale}" key="locale.birthdayDate" var="birthdayDate"/>
<f:message bundle="${locale}" key="locale.banned" var="banned"/>
<f:message bundle="${locale}" key="locale.visitsNumber" var="visitsNumber"/>
<f:message bundle="${locale}" key="locale.emptyField" var="emptyField"/>
<f:message bundle="${locale}" key="locale.invalidLogin" var="invalidLogin"/>
<f:message bundle="${locale}" key="locale.invalidPassword" var="invalidPassword"/>
<f:message bundle="${locale}" key="locale.invalidPassportNumber" var="invalidPassportNumber"/>
<f:message bundle="${locale}" key="locale.invalidSeries" var="invalidSeries"/>
<f:message bundle="${locale}" key="locale.invalidName" var="invalidName"/>
<f:message bundle="${locale}" key="locale.repeatPasswordError" var="repeatPasswordError"/>
<f:message bundle="${locale}" key="locale.saveButton" var="saveButton"/>
<f:message bundle="${locale}" key="locale.closeButton" var="closeButton"/>
<f:message bundle="${locale}" key="locale.editButton" var="editButton"/>
<f:message bundle="${locale}" key="locale.existUserError" var="existUserError"/>
<f:message bundle="${locale}" key="locale.personalInformation" var="personalInformation"/>
<f:message bundle="${locale}" key="locale.editInformation" var="editInformation"/>
<f:message bundle="${locale}" key="locale.applyButton" var="applyButton"/>
<f:message bundle="${locale}" key="locale.discounts" var="sales"/>
<f:message bundle="${locale}" key="locale.value" var="value"/>
<f:message bundle="${locale}" key="locale.used" var="used"/>
<f:message bundle="${locale}" key="locale.giveDiscount" var="giveDiscount"/>
<f:message bundle="${locale}" key="locale.requests" var="rentalRequests"/>
<f:message bundle="${locale}" key="locale.seatsNumber" var="seatsNumber"/>
<f:message bundle="${locale}" key="locale.checkInDate" var="chekInDate"/>
<f:message bundle="${locale}" key="locale.daysNumber" var="daysNumber"/>
<f:message bundle="${locale}" key="locale.requestType" var="requestType"/>
<f:message bundle="${locale}" key="locale.payment" var="payment"/>
<f:message bundle="${locale}" key="locale.requestStatus" var="requestStatus"/>
<f:message bundle="${locale}" key="locale.reservation" var="reservation"/>
<f:message bundle="${locale}" key="locale.fullPayment" var="fullPayment"/>
<f:message bundle="${locale}" key="locale.orderRoom" var="orderRoom"/>
<f:message bundle="${locale}" key="locale.banButton" var="banButton"/>
<f:message bundle="${locale}" key="locale.unbanButton" var="unbanButton"/>
<f:message bundle="${locale}" key="locale.clients" var="clients"/>
<f:message bundle="${locale}" key="locale.deleteButton" var="deleteButton"/>
<f:message bundle="${locale}" key="locale.editDiscount" var="editDiscount"/>


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
    <div id="user-info">
        <br>
        <c:if test="${sessionScope.userRole == false}">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="Controller?command=userAccount">${personalInformation}</a>
                </li>
                <li><a href="Controller?command=findSuitableRooms">${orderRoom}</a></li>
            </ul>
        </c:if>
        <c:if test="${sessionScope.userRole}">
            <a href="Controller?command=viewUsersList" class="btn btn-info">${clients}</a>
        </c:if>
        <div class="row">
            <div class="col-md-6">
                <h2>${personalInformation}</h2>
                <ul class="list-group">
                    <li class="list-group-item">${login}: ${requestScope.user.login}</li>
                    <li class="list-group-item">${banned}: <span
                            class="glyphicon <c:if test="${requestScope.user.banned == true}">glyphicon-ok</c:if> <c:if test="${requestScope.user.banned == false}">glyphicon-remove</c:if>"></span>
                    </li>
                    <li class="list-group-item">${visitsNumber}: ${requestScope.user.visitsNumber}</li>
                    <li class="list-group-item">${passportIdNumber}: ${requestScope.user.passport.identificationNumber}</li>
                    <li class="list-group-item">${passportSeries}: ${requestScope.user.passport.series}</li>
                    <li class="list-group-item">${surname}: ${requestScope.user.passport.surname}</li>
                    <li class="list-group-item">${name}: ${requestScope.user.passport.name}</li>
                    <li class="list-group-item">${patronymic}: ${requestScope.user.passport.patronymic}</li>
                    <li class="list-group-item">${birthdayDate}: ${requestScope.user.passport.birthday}</li>
                </ul>
            </div>
            <div class="col-md-6">
                <h2>${sales}</h2>
                <table class="table table-striped">
                    <tr>
                        <c:if test="${sessionScope.userRole}">
                            <th></th>
                        </c:if>
                        <th>${value}</th>
                        <th>${used}</th>
                    </tr>
                    <c:forEach var="discount" items="${requestScope.discounts}">
                        <tr>
                            <c:if test="${sessionScope.userRole}">
                                <td>
                                    <a href="Controller?command=deleteDiscount&userId=${requestScope.user.id}&discountId=${discount.id}"
                                       class="btn btn-info">${deleteButton}</a>
                                    <button class="btn btn-info" type="button" data-toggle="modal" data-target="#editDiscountModal${discount.id}">${editButton}</button>
                                    <div id="editDiscountModal${discount.id}" class="modal fade">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button class="close" type="button" data-dismiss="modal">×</button>
                                                    <h4 class="modal-title">${editDiscount}</h4>
                                                </div>
                                                <div class="modal-body">
                                                    <form action="Controller" method="post">
                                                        <input type="hidden" name="command" value="editDiscount"/>
                                                        <input type="hidden" name="userId" value="${requestScope.user.id}">
                                                        <input type="hidden" name="discountId" value="${discount.id}">
                                                        <div class="form-group">
                                                            <label for="discountEditValue">${value}:</label>
                                                            <input name="value" type="number" value="${discount.value}"
                                                                   max="1000" min="1" class="form-control" id="discountEditValue">
                                                        </div>
                                                        <div class="form-group">
                                                            <button type="submit" class="btn btn-default">${saveButton}</button>
                                                        </div>
                                                    </form>
                                                </div>
                                                <div class="modal-footer">
                                                    <button class="btn btn-default" type="button" data-dismiss="modal">${closeButton}</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </c:if>
                            <td>${discount.value}</td>
                            <td><span
                                    class="glyphicon <c:if test="${discount.used == true}">glyphicon-ok</c:if> <c:if test="${discount.used == false}">glyphicon-remove</c:if>"></span>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <c:if test="${sessionScope.userRole}">
                    <h2>${giveDiscount}</h2>
                    <form action="Controller" method="post">
                        <input type="hidden" name="command" value="addDiscount"/>
                        <input type="hidden" name="discountFormClientId" value="${requestScope.user.id}"/>
                        <div class="form-group">
                            <label for="discountValue">${value}:</label>
                            <input name="discountFormValue" type="number" value="0"
                                   max="1000" min="1" class="form-control" id="discountValue">
                        </div>
                        <div class="form-group">
                            <button type="submit" class="btn btn-default">${applyButton}</button>
                        </div>
                    </form>
                </c:if>
            </div>
        </div>
        <c:if test="${sessionScope.userRole}">
            <a href="Controller?command=banUser&userId=${requestScope.user.id}&status=<c:if test="${requestScope.user.banned == false}">1</c:if><c:if test="${requestScope.user.banned == true}">0</c:if>"
               class="btn btn-info">
                <c:if test="${requestScope.user.banned ==true}">
                    ${unbanButton}
                </c:if>
                <c:if test="${requestScope.user.banned ==false}">
                    ${banButton}
                </c:if>
            </a>
            <a href="Controller?command=deleteUser&userId=${requestScope.user.id}"
               class="btn btn-info">${deleteButton}</a>
        </c:if>
        <button class="btn btn-info" type="button" data-toggle="modal" data-target="#editUserModal">${editButton}</button>
        <div id="editUserModal" class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button class="close" type="button" data-dismiss="modal">×</button>
                        <h4 class="modal-title">${editInformation}</h4>
                    </div>
                    <div class="modal-body">
                        <form onsubmit="return checkPassword('${repeatPasswordError}')" action="Controller"
                              method="post">
                            <input type="hidden" name="command" value="editUser"/>
                            <input type="hidden" name="userId" value="${requestScope.user.id}">
                            <div class="form-group">
                                <label for="login">${login}:</label>
                                <input name="editFormLogin" type="text" value="${requestScope.user.login}" minlength="5"
                                       maxlength="40" class="form-control" id="login" pattern="^[a-zA-Z][a-zA-Z0-9_]+$"
                                       title="${invalidLogin}" required
                                       oninvalid='this.setCustomValidity("${emptyField} ${invalidLogin}")'
                                       onchange="try{this.setCustomValidity('')}catch(e){}">
                            </div>
                            <div class="form-group">
                                <label for="password">${password}:</label>
                                <input name="editFormPassword" type="password" minlength="6" maxlength="45"
                                       class="form-control" id="password"
                                       pattern="(?=^.{6,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$"
                                       title="${invalidPassword}" required
                                       oninvalid="this.setCustomValidity('${emptyField} ${invalidPassword}')"
                                       onchange="try{this.setCustomValidity('')}catch(e){}">
                            </div>
                            <div class="form-group">
                                <label for="repeatPassword">${repeatPassword}:</label>
                                <input name="editFormRepeatPassword" type="password" minlength="6" maxlength="45"
                                       class="form-control" id="repeatPassword" required
                                       oninvalid="this.setCustomValidity('${emptyField}')"
                                       onchange="try{setCustomValidity('')}catch(e){}">
                            </div>
                            <div class="form-group">
                                <label for="identification-number">${passportIdNumber}:</label>
                                <input name="editFormIdentificationNumber" type="number"
                                       value="${requestScope.user.passport.identificationNumber}" min="1000000"
                                       max="9999999" class="form-control" id="identification-number"
                                       title="${invalidPassportNumber}" required
                                       oninvalid="this.setCustomValidity('${emptyField} ${invalidPassportNumber}')"
                                       onchange="try{setCustomValidity('')}catch(e){}">
                            </div>
                            <div class="form-group">
                                <label for="series">${passportSeries}:</label>
                                <input name="editFormSeries" type="text" value="${requestScope.user.passport.series}"
                                       minlength="2" maxlength="2" class="form-control" id="series" pattern="^[A-Z]+$"
                                       title="${invalidSeries}" required
                                       oninvalid="this.setCustomValidity('${emptyField} ${invalidSeries}')"
                                       onchange="try{setCustomValidity('')}catch(e){}">
                            </div>
                            <div class="form-group">
                                <label for="surname">${surname}:</label>
                                <input name="editFormSurname" type="text" value="${requestScope.user.passport.surname}"
                                       minlength="2" maxlength="40" class="form-control" id="surname"
                                       pattern="^[a-zA-Zа-яёА-ЯЁ\s\-]+$" title="${invalidName}" required
                                       oninvalid='this.setCustomValidity("${emptyField} ${invalidName}")'
                                       onchange="try{setCustomValidity('')}catch(e){}">
                            </div>
                            <div class="form-group">
                                <label for="name">${name}:</label>
                                <input name="editFormName" type="text" value="${requestScope.user.passport.name}"
                                       minlength="2" maxlength="40" class="form-control" id="name"
                                       pattern="^[a-zA-Zа-яёА-ЯЁ\s\-]+$" title="${invalidName}" required
                                       oninvalid='this.setCustomValidity("${emptyField} ${invalidName}")'
                                       onchange="try{setCustomValidity('')}catch(e){}">
                            </div>
                            <div class="form-group">
                                <label for="patronymic">${patronymic}:</label>
                                <input name="editFormPatronymic" type="text"
                                       value="${requestScope.user.passport.patronymic}" minlength="2" maxlength="40"
                                       class="form-control" id="patronymic" pattern="^[a-zA-Zа-яёА-ЯЁ\s\-]+$"
                                       title="${invalidName}" oninvalid='this.setCustomValidity("${invalidName}")'
                                       onchange="try{setCustomValidity('')}catch(e){}">
                            </div>
                            <div class="form-group">
                                <label for="date">${birthdayDate}:</label>
                                <input name="editFormBirthdayDate" type="date"
                                       value="${requestScope.user.passport.birthday}"
                                       max="1998-01-01" min="1920-01-01" class="form-control" id="date" required
                                       oninvalid="this.setCustomValidity('${emptyField}')"
                                       onchange="try{setCustomValidity('')}catch(e){}">
                            </div>
                            <div id="err-password"></div>
                            <div class="form-group">
                                <button type="submit" class="btn btn-default">${saveButton}</button>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-default" type="button" data-dismiss="modal">${closeButton}</button>
                    </div>
                </div>
            </div>
        </div>
        <br><br>
        <c:if test="${param.serviceError}">
            <div class="alert alert-danger fade in">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    ${serverError}
            </div>
        </c:if>
        <c:if test="${requestScope.user == null}">
            <div class="alert alert-danger fade in">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    ${existUserError}
            </div>
        </c:if>
        <c:if test="${param.editSuccess}">
            <br>
            <div class="alert alert-success fade in">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    ${editSuccess}
            </div>
        </c:if>
        <c:if test="${param.wrongLogin}">
            <div class="alert alert-danger fade in">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    ${wrongLogin}
            </div>
        </c:if>
        <c:if test="${param.wrongPassword}">
            <div class="alert alert-danger fade in">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    ${wrongPassword}
            </div>
        </c:if>
        <hr>
        <h2>${rentalRequests}</h2>
        <table class="table table-striped">
            <tr>
                <th>${seatsNumber}</th>
                <th>${chekInDate}</th>
                <th>${daysNumber}</th>
                <th>${requestType}</th>
                <th>${payment}</th>
                <th>${requestStatus}</th>
            </tr>
            <c:forEach var="rentalRequest" items="${requestScope.rentalRequests}">
                <tr>
                    <td>${rentalRequest.seatsNumber}</td>
                    <td>${rentalRequest.checkInDate}</td>
                    <td>${rentalRequest.daysStayNumber}</td>
                    <td><c:if test="${rentalRequest.fullPayment == true}">${fullPayment}</c:if> <c:if
                            test="${rentalRequest.fullPayment == false}">${reservation}</c:if></td>
                    <td>${rentalRequest.payment}</td>
                    <td><span
                            class="glyphicon <c:if test="${rentalRequest.accepted == null}">glyphicon-refresh</c:if> <c:if test="${rentalRequest.accepted == true}">glyphicon-ok</c:if> <c:if test="${rentalRequest.accepted == false}">glyphicon-remove</c:if> "></span>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>

    <hr>

    <footer>
        <p>&copy; 2016 ${siteName}</p>
    </footer>
</div>

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/script.js"></script>
</body>
</html>
