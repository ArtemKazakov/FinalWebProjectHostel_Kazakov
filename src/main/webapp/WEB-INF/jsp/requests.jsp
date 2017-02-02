<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 08.01.2017
  Time: 16:38
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
<f:message bundle="${local}" key="local.seatsNumber" var="seatsNumber"/>
<f:message bundle="${local}" key="local.perdayCost" var="perdayCost"/>
<f:message bundle="${local}" key="local.serverError" var="serverError"/>
<f:message bundle="${local}" key="local.checkInDate" var="chekInDate"/>
<f:message bundle="${local}" key="local.daysNumber" var="daysNumber"/>
<f:message bundle="${local}" key="local.requestType" var="requestType"/>
<f:message bundle="${local}" key="local.payment" var="payment"/>
<f:message bundle="${local}" key="local.requestStatus" var="requestStatus"/>
<f:message bundle="${local}" key="local.reservation" var="reservation"/>
<f:message bundle="${local}" key="local.fullPayment" var="fullPayment"/>
<f:message bundle="${local}" key="local.clients" var="clients"/>
<f:message bundle="${local}" key="local.requests" var="requests"/>
<f:message bundle="${local}" key="local.personalInformation" var="personalInformation"/>
<f:message bundle="${local}" key="local.client" var="client"/>
<f:message bundle="${local}" key="local.administrator" var="administrator"/>
<f:message bundle="${local}" key="local.schedule" var="schedule"/>
<f:message bundle="${local}" key="local.deleteButton" var="deleteButton"/>
<f:message bundle="${local}" key="local.acceptButton" var="acceptButton"/>
<f:message bundle="${local}" key="local.rejectButton" var="rejectButton"/>
<f:message bundle="${local}" key="local.next" var="next"/>
<f:message bundle="${local}" key="local.last" var="last"/>
<f:message bundle="${local}" key="local.first" var="first"/>
<f:message bundle="${local}" key="local.prev" var="prev"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${siteName} - ${requests}</title>
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
        <li><a href="${pageContext.request.contextPath}/Controller?command=viewUsersList">${clients}</a></li>
        <li class="active"><a href="${pageContext.request.contextPath}/Controller?command=viewRequestsList">${requests}</a></li>
        <li><a href="${pageContext.request.contextPath}/Controller?command=viewScheduleRecords">${schedule}</a></li>
    </ul>

    <c:if test="${param.serviceError}">
        <br>
        <br>
        <div class="alert alert-danger fade in">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                ${serverError}
        </div>
    </c:if>
    <br>
    <table class="table table-striped">
        <tr>
            <th></th>
            <th>${client}</th>
            <th>${administrator}</th>
            <th>${seatsNumber}</th>
            <th>${chekInDate}</th>
            <th>${daysNumber}</th>
            <th>${requestType}</th>
            <th>${payment}</th>
            <th>${requestStatus}</th>
        </tr>
        <c:forEach var="rentalRequest" items="${requestScope.requestsList.content}">
            <tr>
                <td>
                    <div class="btn-group">
                        <c:if test="${rentalRequest.accepted == null}">
                            <a href="${pageContext.request.contextPath}/Controller?command=editRequest&requestId=${rentalRequest.id}&requestStatus=1"
                               class="btn btn-default">${acceptButton}</a>
                            <a href="${pageContext.request.contextPath}/Controller?command=editRequest&requestId=${rentalRequest.id}&requestStatus=0"
                               class="btn btn-default">${rejectButton}</a>
                        </c:if>
                        <a href="${pageContext.request.contextPath}/Controller?command=deleteRequest&requestId=${rentalRequest.id}"
                           class="btn btn-default">${deleteButton}</a>
                    </div>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/Controller?command=viewUser&clientId=${rentalRequest.client.id}">${rentalRequest.client.login}</a>
                </td>
                <td>${rentalRequest.administrator.login}</td>
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
    <div class="ta_center">
        <div class="btn-group">
            <c:if test="${requestScope.requestsList.currentPage != 1}">
                <a href="${pageContext.request.contextPath}/Controller?command=viewRequestsList&page=1"
                   class="btn btn-default">
                    <span class="glyphicon glyphicon-fast-backward"></span> ${first}
                </a>
                <a href="${pageContext.request.contextPath}/Controller?command=viewRequestsList&page=${requestScope.requestsList.currentPage - 1}"
                   class="btn btn-default">
                    <span class="glyphicon glyphicon-chevron-left"></span> ${prev}
                </a>
            </c:if>
            <c:if test="${requestScope.requestsList.lastPage > requestScope.requestsList.currentPage}">
                <a href="${pageContext.request.contextPath}/Controller?command=viewRequestsList&page=${requestScope.requestsList.currentPage +1}"
                   class="btn btn-default">
                    <span class="glyphicon glyphicon-chevron-right"></span> ${next}
                </a>
                <a href="${pageContext.request.contextPath}/Controller?command=viewRequestsList&page=${requestScope.requestsList.lastPage}"
                   class="btn btn-default">
                    <span class="glyphicon glyphicon glyphicon-fast-forward"></span> ${last}
                </a>
            </c:if>
        </div>
    </div>
    <hr>

</div>

<c:import url="template/footer.jsp"/>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>

