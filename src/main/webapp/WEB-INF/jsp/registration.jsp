<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 09.11.2016
  Time: 0:10
  To change this template use File | Settings | File Templates.
--%>
<%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setLocale value="${requestScope.selectedLanguage}"/>
<f:setBundle basename="local" var="local"/>
<f:message bundle="${local}" key="local.siteName" var="siteName"/>
<f:message bundle="${local}" key="local.login" var="login"/>
<f:message bundle="${local}" key="local.password" var="password"/>
<f:message bundle="${local}" key="local.repeatPassword" var="repeatPassword"/>
<f:message bundle="${local}" key="local.signupButton" var="signupButton"/>
<f:message bundle="${local}" key="local.wrongLogin" var="wrongLogin"/>
<f:message bundle="${local}" key="local.wrongPassword" var="wrongPassword"/>
<f:message bundle="${local}" key="local.serverError" var="serverError"/>
<f:message bundle="${local}" key="local.registrationSuccess" var="registrationSuccess"/>
<f:message bundle="${local}" key="local.passportIdNumber" var="passportIdNumber"/>
<f:message bundle="${local}" key="local.passportSeries" var="passportSeries"/>
<f:message bundle="${local}" key="local.surname" var="surname"/>
<f:message bundle="${local}" key="local.name" var="name"/>
<f:message bundle="${local}" key="local.patronymic" var="patronymic"/>
<f:message bundle="${local}" key="local.birthdayDate" var="birthdayDate"/>
<f:message bundle="${local}" key="local.emptyField" var="emptyField"/>
<f:message bundle="${local}" key="local.invalidLogin" var="invalidLogin"/>
<f:message bundle="${local}" key="local.invalidPassword" var="invalidPassword"/>
<f:message bundle="${local}" key="local.invalidPassportNumber" var="invalidPassportNumber"/>
<f:message bundle="${local}" key="local.invalidSeries" var="invalidSeries"/>
<f:message bundle="${local}" key="local.invalidName" var="invalidName"/>
<f:message bundle="${local}" key="local.repeatPasswordError" var="repeatPasswordError"/>
<f:message bundle="${local}" key="local.registrarion" var="registrarion"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${siteName} - ${registrarion}</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/hostel_icon.png" type="image/png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<c:import url="template/header.jsp"/>

<div class="container">
    <div class="jumbotron">
        <div class="row">
            <div class="col-md-3"></div>
            <div class="col-md-6">
                <form onsubmit="return checkPassword('${repeatPasswordError}')"
                      action="${pageContext.request.contextPath}/Controller" method="post">
                    <input type="hidden" name="command" value="registration"/>
                    <div class="form-group">
                        <label for="login">${login}:</label>
                        <div class="input-group">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                            <input name="registrationFormLogin" type="text" value="${param.registrationFormLogin}"
                                   minlength="5" maxlength="40" class="form-control" id="login"
                                   pattern="^[a-zA-Z][a-zA-Z0-9_]+$" title="${invalidLogin}" required
                                   oninvalid='this.setCustomValidity("${emptyField} ${invalidLogin}")'
                                   onchange="try{this.setCustomValidity('')}catch(e){}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="password">${password}:</label>
                        <div class="input-group">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                            <input name="registrationFormPassword" type="password" minlength="6" maxlength="45"
                                   class="form-control" id="password"
                                   pattern="(?=^.{6,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$"
                                   title="${invalidPassword}" required
                                   oninvalid="this.setCustomValidity('${emptyField} ${invalidPassword}')"
                                   onchange="try{this.setCustomValidity('')}catch(e){}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="repeatPassword">${repeatPassword}:</label>
                        <div class="input-group">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                            <input name="registrationFormRepeatPassword" type="password" minlength="6" maxlength="45"
                                   class="form-control" id="repeatPassword" required
                                   oninvalid="this.setCustomValidity('${emptyField}')"
                                   onchange="try{setCustomValidity('')}catch(e){}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="identification-number">${passportIdNumber}:</label>
                        <div class="input-group">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-search"></i></span>
                            <input name="registrationFormIdentificationNumber" type="number"
                                   value="${param.registrationFormIdentificationNumber}" min="1000000" max="9999999"
                                   class="form-control" id="identification-number" title="${invalidPassportNumber}"
                                   required
                                   oninvalid="this.setCustomValidity('${emptyField} ${invalidPassportNumber}')"
                                   onchange="try{setCustomValidity('')}catch(e){}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="series">${passportSeries}:</label>
                        <div class="input-group">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-paperclip"></i></span>
                            <input name="registrationFormSeries" type="text" value="${param.registrationFormSeries}"
                                   minlength="2" maxlength="2" class="form-control" id="series" pattern="^[A-Z]+$"
                                   title="${invalidSeries}" required
                                   oninvalid="this.setCustomValidity('${emptyField} ${invalidSeries}')"
                                   onchange="try{setCustomValidity('')}catch(e){}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="surname">${surname}:</label>
                        <div class="input-group">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                            <input name="registrationFormSurname" type="text" value="${param.registrationFormSurname}"
                                   minlength="2" maxlength="40" class="form-control" id="surname"
                                   pattern="^[a-zA-Zа-яёА-ЯЁ\s\-]+$" title="${invalidName}" required
                                   oninvalid='this.setCustomValidity("${emptyField} ${invalidName}")'
                                   onchange="try{setCustomValidity('')}catch(e){}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="name">${name}:</label>
                        <div class="input-group">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                            <input name="registrationFormName" type="text" value="${param.registrationFormName}"
                                   minlength="2" maxlength="40" class="form-control" id="name"
                                   pattern="^[a-zA-Zа-яёА-ЯЁ\s\-]+$" title="${invalidName}" required
                                   oninvalid='this.setCustomValidity("${emptyField} ${invalidName}")'
                                   onchange="try{setCustomValidity('')}catch(e){}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="patronymic">${patronymic}:</label>
                        <div class="input-group">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                            <input name="registrationFormPatronymic" type="text"
                                   value="${param.registrationFormPatronymic}"
                                   minlength="2" maxlength="40" class="form-control" id="patronymic"
                                   pattern="^[a-zA-Zа-яёА-ЯЁ\s\-]+$" title="${invalidName}"
                                   oninvalid='this.setCustomValidity("${invalidName}")'
                                   onchange="try{setCustomValidity('')}catch(e){}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="date">${birthdayDate}:</label>
                        <div class="input-group">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                            <input name="registrationFormBirthdayDate" type="date"
                                   value="${param.registrationFormBirthdayDate}"
                                   max="1998-12-31" min="1920-01-01" class="form-control" id="date" required
                                   oninvalid="this.setCustomValidity('${emptyField}')"
                                   onchange="try{setCustomValidity('')}catch(e){}">
                        </div>
                    </div>
                    <div id="err-password"></div>
                    <div class="form-group">
                        <button type="submit" class="btn btn-default">${signupButton}</button>
                    </div>
                </form>
            </div>
            <div class="col-md-3"></div>
        </div>
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

</div>

<c:import url="template/footer.jsp"/>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>
