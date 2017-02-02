<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 09.01.2017
  Time: 1:02
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
<f:message bundle="${local}" key="local.checkInDate" var="checkInDate"/>
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
<f:message bundle="${local}" key="local.room" var="room"/>
<f:message bundle="${local}" key="local.checkOutDate" var="checkOutDate"/>
<f:message bundle="${local}" key="local.paymentDuty" var="paymentDuty"/>
<f:message bundle="${local}" key="local.client" var="client"/>
<f:message bundle="${local}" key="local.administrator" var="administrator"/>
<f:message bundle="${local}" key="local.closeButton" var="closeButton"/>
<f:message bundle="${local}" key="local.request" var="request"/>
<f:message bundle="${local}" key="local.showRequestInfo" var="showRequestInfo"/>
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
    <title>${siteName} - ${schedule}</title>
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
        <li><a href="${pageContext.request.contextPath}/Controller?command=viewRequestsList">${requests}</a></li>
        <li class="active"><a href="${pageContext.request.contextPath}/Controller?command=viewScheduleRecords">${schedule}</a></li>
    </ul>

    <c:if test="${param.serviceError}">
        <div class="alert alert-danger fade in">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                ${serverError}
        </div>
    </c:if>
    <br>
    <table class="table table-striped">
        <tr>
            <th></th>
            <th>${room}</th>
            <th>${checkInDate}</th>
            <th>${checkOutDate}</th>
            <th>${paymentDuty}</th>
        </tr>
        <c:forEach var="scheduleRecord" items="${requestScope.scheduleRecords.content}">
            <tr>
                <td>
                    <button onclick="viewRequest(${scheduleRecord.requestId})" class="btn btn-default" type="button"
                            data-toggle="modal" data-target="#viewRequest">
                            ${showRequestInfo}
                    </button>
                </td>
                <td>${scheduleRecord.roomNumber}</td>
                <td>${scheduleRecord.checkInDate}</td>
                <td>${scheduleRecord.checkoutDate}</td>
                <td>${scheduleRecord.paymentDuty}</td>
            </tr>
        </c:forEach>
    </table>
    <div class="ta_center">
        <div class="btn-group">
            <c:if test="${requestScope.scheduleRecords.currentPage != 1}">
                <a href="${pageContext.request.contextPath}/Controller?command=viewScheduleRecords&page=1"
                   class="btn btn-default">
                    <span class="glyphicon glyphicon-fast-backward"></span> ${first}
                </a>
                <a href="${pageContext.request.contextPath}/Controller?command=viewScheduleRecords&page=${requestScope.scheduleRecords.currentPage - 1}"
                   class="btn btn-default">
                    <span class="glyphicon glyphicon-chevron-left"></span> ${prev}
                </a>
            </c:if>
            <c:if test="${requestScope.scheduleRecords.lastPage > requestScope.scheduleRecords.currentPage}">
                <a href="${pageContext.request.contextPath}/Controller?command=viewScheduleRecords&page=${requestScope.scheduleRecords.currentPage +1}"
                   class="btn btn-default">
                    <span class="glyphicon glyphicon-chevron-right"></span> ${next}
                </a>
                <a href="${pageContext.request.contextPath}/Controller?command=viewScheduleRecords&page=${requestScope.scheduleRecords.lastPage}"
                   class="btn btn-default">
                    <span class="glyphicon glyphicon glyphicon-fast-forward"></span> ${last}
                </a>
            </c:if>
        </div>
    </div>

    <div id="viewRequest" class="modal fade">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button class="close" type="button" data-dismiss="modal">×</button>
                    <h4 class="modal-title">${request}</h4>
                    <h4 id="rentalRequestError"></h4>
                </div>
                <div class="modal-body" id="rentalRequestBody">
                    <ul class="list-group">
                        <li class="list-group-item"><strong>${client}:</strong> <span id="client"></span></li>
                        <li class="list-group-item"><strong>${administrator}:</strong> <span id="administrator"></span>
                        </li>
                        <li class="list-group-item"><strong>${seatsNumber}:</strong> <span id="seatsNumber"></span></li>
                        <li class="list-group-item"><strong>${checkInDate}:</strong> <span id="checkInDate"></span></li>
                        <li class="list-group-item"><strong>${daysNumber}:</strong> <span id="daysNumber"></span></li>
                        <li class="list-group-item"><strong>${requestType}:</strong>
                            <span id="requestType1">${fullPayment}</span>
                            <span id="requestType2">${reservation}</span>
                        </li>
                        <li class="list-group-item"><strong>${payment}:</strong> <span id="payment"></span></li>
                    </ul>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-default" type="button"
                            data-dismiss="modal">${closeButton}
                    </button>
                </div>
            </div>
        </div>
    </div>

    <hr>

</div>

<c:import url="template/footer.jsp"/>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ajaxrentalrequest.js"></script>

</body>
</html>


