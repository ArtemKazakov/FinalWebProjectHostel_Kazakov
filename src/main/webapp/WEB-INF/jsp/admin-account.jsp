<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 16.11.2016
  Time: 21:10
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
<f:message bundle="${local}" key="local.serverError" var="serverError"/>
<f:message bundle="${local}" key="local.passportIdNumber" var="passportIdNumber"/>
<f:message bundle="${local}" key="local.passportSeries" var="passportSeries"/>
<f:message bundle="${local}" key="local.surname" var="surname"/>
<f:message bundle="${local}" key="local.name" var="name"/>
<f:message bundle="${local}" key="local.patronymic" var="patronymic"/>
<f:message bundle="${local}" key="local.birthdayDate" var="birthdayDate"/>
<f:message bundle="${local}" key="local.personalInformation" var="personalInformation"/>
<f:message bundle="${local}" key="local.existUserError" var="existUserError"/>
<f:message bundle="${local}" key="local.clients" var="clients"/>
<f:message bundle="${local}" key="local.requests" var="requests"/>
<f:message bundle="${local}" key="local.schedule" var="schedule"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${siteName}</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/hostel_icon.png" type="image/png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<c:import url="template/header.jsp"/>

<div class="container">
    <div id="user-info">
        <br>
        <ul class="nav nav-tabs">
            <li class="active">
                <a href="${pageContext.request.contextPath}/Controller?command=userAccount">${personalInformation}</a>
            </li>
            <li><a href="${pageContext.request.contextPath}/Controller?command=viewUsersList">${clients}</a></li>
            <li><a href="${pageContext.request.contextPath}/Controller?command=viewRequestsList">${requests}</a></li>
            <li><a href="${pageContext.request.contextPath}/Controller?command=viewScheduleRecords">${schedule}</a></li>
        </ul>
        <h2>${personalInformation}</h2>
        <ul class="list-group">
            <li class="list-group-item">${login}: ${requestScope.user.login}</li>
            <li class="list-group-item">${passportIdNumber}: ${requestScope.user.passport.identificationNumber}</li>
            <li class="list-group-item">${passportSeries}: ${requestScope.user.passport.series}</li>
            <li class="list-group-item">${surname}: ${requestScope.user.passport.surname}</li>
            <li class="list-group-item">${name}: ${requestScope.user.passport.name}</li>
            <li class="list-group-item">${patronymic}: ${requestScope.user.passport.patronymic}</li>
            <li class="list-group-item">${birthdayDate}: ${requestScope.user.passport.birthday}</li>
        </ul>
        <br>

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
    </div>
    <hr>
</div>

<c:import url="template/footer.jsp"/>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>
