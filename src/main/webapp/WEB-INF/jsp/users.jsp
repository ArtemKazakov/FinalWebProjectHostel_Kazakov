<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 18.11.2016
  Time: 1:56
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
<f:message bundle="${local}" key="local.wrongLogin" var="wrongLogin"/>
<f:message bundle="${local}" key="local.wrongPassword" var="wrongPassword"/>
<f:message bundle="${local}" key="local.serverError" var="serverError"/>
<f:message bundle="${local}" key="local.passportIdNumber" var="passportIdNumber"/>
<f:message bundle="${local}" key="local.passportSeries" var="passportSeries"/>
<f:message bundle="${local}" key="local.surname" var="surname"/>
<f:message bundle="${local}" key="local.name" var="name"/>
<f:message bundle="${local}" key="local.patronymic" var="patronymic"/>
<f:message bundle="${local}" key="local.birthdayDate" var="birthdayDate"/>
<f:message bundle="${local}" key="local.banned" var="banned"/>
<f:message bundle="${local}" key="local.visitsNumber" var="visitsNumber"/>
<f:message bundle="${local}" key="local.personalInformation" var="personalInformation"/>
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
    <br>
    <ul class="nav nav-tabs">
        <li>
            <a href="${pageContext.request.contextPath}/Controller?command=userAccount">${personalInformation}</a>
        </li>
        <li class="active"><a href="${pageContext.request.contextPath}/Controller?command=viewUsersList">${clients}</a></li>
        <li><a href="${pageContext.request.contextPath}/Controller?command=viewRequestsList">${requests}</a></li>
        <li><a href="${pageContext.request.contextPath}/Controller?command=viewScheduleRecords">${schedule}</a></li>
    </ul>
    <br>
    <table class="table table-striped">
        <tr>
            <th></th>
            <th>${login}</th>
            <th>${banned}</th>
            <th>${visitsNumber}</th>
            <th>${passportIdNumber}</th>
            <th>${passportSeries}</th>
            <th>${surname}</th>
            <th>${name}</th>
            <th>${patronymic}</th>
            <th>${birthdayDate}</th>
        </tr>
        <c:forEach var="user" items="${requestScope.users}">
            <tr>
                <td><a href="${pageContext.request.contextPath}/Controller?command=viewUser&clientId=${user.id}"><span
                        class="glyphicon glyphicon-edit"></span></a></td>
                <td>${user.login}</td>
                <td><span
                        class="glyphicon <c:if test="${user.banned == true}">glyphicon-ok</c:if> <c:if test="${user.banned == false}">glyphicon-remove</c:if>"></span>
                </td>
                <td>${user.visitsNumber}</td>
                <td>${user.passport.identificationNumber}</td>
                <td>${user.passport.series}</td>
                <td>${user.passport.surname}</td>
                <td>${user.passport.name}</td>
                <td>${user.passport.patronymic}</td>
                <td>${user.passport.birthday}</td>
            </tr>
        </c:forEach>
    </table>

    <hr>

</div>

<c:import url="template/footer.jsp"/>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>
