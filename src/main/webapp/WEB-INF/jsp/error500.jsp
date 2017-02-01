<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 27.01.2017
  Time: 20:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setLocale value="${sessionScope.languageId}"/>
<f:setBundle basename="local" var="local"/>
<f:message bundle="${local}" key="local.500error" var="message"/>
<f:message bundle="${local}" key="local.goback" var="goback"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${message}</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/hostel_icon.png" type="image/png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container text-center">
    <h2>${message}</h2>
    <a href="${pageContext.request.contextPath}/Controller?command=mainPage">${goback}</a>
    <p>
        <img src="${pageContext.request.contextPath}/img/404.jpg" alt="${message}"
             style="max-width: 100%">
    </p>
</div>
</body>
</html>
