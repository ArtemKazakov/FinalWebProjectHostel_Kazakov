<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 09.11.2016
  Time: 0:10
  To change this template use File | Settings | File Templates.
--%>
<%-- Created by IntelliJ IDEA. --%>
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
<f:message bundle="${locale}" key="locale.rooms" var="rooms"/>
<f:message bundle="${locale}" key="locale.wrongLogin" var="wrongLogin"/>
<f:message bundle="${locale}" key="locale.wrongPassword" var="wrongPassword"/>
<f:message bundle="${locale}" key="locale.serverError" var="serverError"/>
<f:message bundle="${locale}" key="locale.registrationSuccess" var="registrationSuccess"/>
<f:message bundle="${locale}" key="locale.passportIdNumber" var="passportIdNumber"/>
<f:message bundle="${locale}" key="locale.passportSeries" var="passportSeries"/>
<f:message bundle="${locale}" key="locale.surname" var="surname"/>
<f:message bundle="${locale}" key="locale.name" var="name"/>
<f:message bundle="${locale}" key="locale.patronymic" var="patronymic"/>
<f:message bundle="${locale}" key="locale.birthdayDate" var="birthdayDate"/>
<f:message bundle="${locale}" key="locale.emptyField" var="emptyField"/>
<f:message bundle="${locale}" key="locale.invalidLogin" var="invalidLogin"/>
<f:message bundle="${locale}" key="locale.invalidPassword" var="invalidPassword"/>
<f:message bundle="${locale}" key="locale.invalidPassportNumber" var="invalidPassportNumber"/>
<f:message bundle="${locale}" key="locale.invalidSeries" var="invalidSeries"/>
<f:message bundle="${locale}" key="locale.invalidName" var="invalidName"/>
<f:message bundle="${locale}" key="locale.repeatPasswordError" var="repeatPasswordError"/>


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
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
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
            <ul class="nav navbar-nav navbar-right" >
                <c:if test="${sessionScope.userId == null}">
                    <li><form class="navbar-form" action="Controller" method="post">
                        <input type="hidden" name="command" value="log_in" />
                        <div class="form-group">
                            <input type="text" name="logInFormLogin" placeholder="${login}" class="form-control">
                        </div>
                        <div class="form-group">
                            <input type="password" name="logInFormPassword" placeholder="${password}" class="form-control">
                        </div>
                        <button type="submit" class="btn btn-success">${loginButton}</button>
                    </form></li>
                    <li><a href="Controller?command=registration"><span class="glyphicon glyphicon-user"></span> ${signupButton}</a></li>
                </c:if>
                <c:if test="${sessionScope.userId != null}">
                    <li><form class="navbar-form navbar-right" action="Controller" method="post">
                        <input type="hidden" name="command" value="log_out" />
                        <button type="submit" class="btn btn-success">${logoutButton}</button>
                    </form></li>
                    <li><a href="Controller?command=userAccount"><span class="glyphicon glyphicon-user"></span> ${accountButton}</a></li>
                </c:if>
                <c:if test='${requestScope.selectedLanguage eq "EN"}'><li><form class="navbar-form" id="change-language-ru" action="Controller?command=changeLanguage" method="post"><input type="hidden" name="changeLanguage" value="RU"><button type="submit" class="btn btn-danger">RU</button></form></li></c:if>
                <c:if test='${requestScope.selectedLanguage eq "RU"}'><li><form class="navbar-form" id="change-language-en" action="Controller?command=changeLanguage" method="post"><input type="hidden" name="changeLanguage" value="EN"><button type="submit" class="btn btn-danger">EN</button></form></li></c:if>
            </ul>
        </div>
    </div>
</nav>

<div class="container">
    <div class="jumbotron">
        <form onsubmit="return checkPassword('${repeatPasswordError}')" action="Controller" method="post">
            <input type="hidden" name="command" value="registration" />
            <div class="form-group">
                <label for="login">${login}:</label>
                <input name="registrationFormLogin" type="text" value="${param.registrationFormLogin}" minlength="5" maxlength="40" class="form-control" id="login" pattern="^[a-zA-Z][a-zA-Z0-9_]+$" title="${invalidLogin}" required oninvalid='this.setCustomValidity("${emptyField} ${invalidLogin}")' onchange="try{this.setCustomValidity('')}catch(e){}">
            </div>
            <div class="form-group">
                <label for="password">${password}:</label>
                <input name="registrationFormPassword" type="password" minlength="6" maxlength="45" class="form-control" id="password" pattern="(?=^.{6,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$" title="${invalidPassword}" required oninvalid="this.setCustomValidity('${emptyField} ${invalidPassword}')" onchange="try{this.setCustomValidity('')}catch(e){}">
            </div>
            <div class="form-group">
                <label for="repeatPassword">${repeatPassword}:</label>
                <input name="registrationFormRepeatPassword" type="password" minlength="6" maxlength="45" class="form-control" id="repeatPassword" required oninvalid="this.setCustomValidity('${emptyField}')" onchange="try{setCustomValidity('')}catch(e){}">
            </div>
            <div class="form-group">
                <label for="identification-number">${passportIdNumber}:</label>
                <input name="registrationFormIdentificationNumber" type="number" value="${param.registrationFormIdentificationNumber}" min="1000000" max="9999999" class="form-control" id="identification-number" title="${invalidPassportNumber}" required oninvalid="this.setCustomValidity('${emptyField} ${invalidPassportNumber}')" onchange="try{setCustomValidity('')}catch(e){}">
            </div>
            <div class="form-group">
                <label for="series">${passportSeries}:</label>
                <input name="registrationFormSeries" type="text" value="${param.registrationFormSeries}" minlength="2" maxlength="2" class="form-control" id="series" pattern="^[A-Z]+$" title="${invalidSeries}" required oninvalid="this.setCustomValidity('${emptyField} ${invalidSeries}')" onchange="try{setCustomValidity('')}catch(e){}">
            </div>
            <div class="form-group">
                <label for="surname">${surname}:</label>
                <input name="registrationFormSurname" type="text" value="${param.registrationFormSurname}" minlength="2" maxlength="40" class="form-control" id="surname" pattern="^[a-zA-Zа-яёА-ЯЁ\s\-]+$" title="${invalidName}" required oninvalid="this.setCustomValidity('${emptyField} ${invalidName}')" onchange="try{setCustomValidity('')}catch(e){}">
            </div>
            <div class="form-group">
                <label for="name">${name}:</label>
                <input name="registrationFormName" type="text" value="${param.registrationFormName}" minlength="2" maxlength="40" class="form-control" id="name" pattern="^[a-zA-Zа-яёА-ЯЁ\s\-]+$" title="${invalidName}" required oninvalid="this.setCustomValidity('${emptyField} ${invalidName}')" onchange="try{setCustomValidity('')}catch(e){}">
            </div>
            <div class="form-group">
                <label for="patronymic">${patronymic}:</label>
                <input name="registrationFormPatronymic" type="text" value="${param.registrationFormPatronymic}" minlength="2" maxlength="40" class="form-control" id="patronymic" pattern="^[a-zA-Zа-яёА-ЯЁ\s\-]+$" title="${invalidName}" oninvalid="this.setCustomValidity('${invalidName}')" onchange="try{setCustomValidity('')}catch(e){}">
            </div>
            <div class="form-group">
                <label for="date">${birthdayDate}:</label>
                <input name="registrationFormBirthdayDate" type="date" value="${param.registrationFormBirthdayDate}"
                       max="1998-01-01" min="1920-01-01" class="form-control" id="date" required oninvalid="this.setCustomValidity('${emptyField}')" onchange="try{setCustomValidity('')}catch(e){}">
            </div>
            <div id="err-password"></div>
            <div class="form-group">
                <button type="submit" class="btn btn-default">${signupButton}</button>
            </div>
        </form>

        <c:if test="${param.registrationSuccess}">
            <div class="alert alert-success fade in">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    ${registrationSuccess}
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
        <c:if test="${param.serviceError}">
            <div class="alert alert-danger fade in">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    ${serverError}
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
<script type="text/javascript" src="js/script.js"></script>
</body>
</html>
