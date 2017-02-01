<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setLocale value="${requestScope.selectedLanguage}"/>
<f:setBundle basename="local" var="local"/>
<f:message bundle="${local}" key="local.siteName" var="siteName"/>
<f:message bundle="${local}" key="local.login" var="login"/>
<f:message bundle="${local}" key="local.password" var="password"/>
<f:message bundle="${local}" key="local.number" var="number"/>
<f:message bundle="${local}" key="local.rooms" var="rooms"/>
<f:message bundle="${local}" key="local.seatsNumber" var="seatsNumber"/>
<f:message bundle="${local}" key="local.perdayCost" var="perdayCost"/>
<f:message bundle="${local}" key="local.singleRooms" var="singleRooms"/>
<f:message bundle="${local}" key="local.doubleRooms" var="doubleRooms"/>
<f:message bundle="${local}" key="local.tripleRooms" var="tripleRooms"/>
<f:message bundle="${local}" key="local.quadrupleRooms" var="quadrupleRooms"/>
<f:message bundle="${local}" key="local.allRooms" var="allRooms"/>
<f:message bundle="${local}" key="local.addRoomButton" var="addRoomButton"/>
<f:message bundle="${local}" key="local.applyButton" var="applyButton"/>
<f:message bundle="${local}" key="local.serverError" var="serverError"/>
<f:message bundle="${local}" key="local.addRoomSuccess" var="addRoomSuccess"/>
<f:message bundle="${local}" key="local.editRoomSuccess" var="editRoomSuccess"/>
<f:message bundle="${local}" key="local.deleteRoomSuccess" var="deleteRoomSuccess"/>
<f:message bundle="${local}" key="local.existingRoomError" var="existingRoomError"/>
<f:message bundle="${local}" key="local.closeButton" var="closeButton"/>
<f:message bundle="${local}" key="local.deleteButton" var="deleteButton"/>
<f:message bundle="${local}" key="local.editButton" var="editButton"/>
<f:message bundle="${local}" key="local.saveButton" var="saveButton"/>
<f:message bundle="${local}" key="local.editRoom" var="editRoom"/>

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
    <div class="btn-group">
        <button type="button" data-toggle="dropdown" class="btn btn-info dropdown-toggle">
            ${rooms}
            <span class="caret"></span>
        </button>
        <ul class="dropdown-menu">
            <li><a href="${pageContext.request.contextPath}/Controller?command=viewRoomsList">${allRooms}</a></li>
            <li>
                <a href="${pageContext.request.contextPath}/Controller?command=viewRoomsList&seatsNumber=1">${singleRooms}</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/Controller?command=viewRoomsList&seatsNumber=2">${doubleRooms}</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/Controller?command=viewRoomsList&seatsNumber=3">${tripleRooms}</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/Controller?command=viewRoomsList&seatsNumber=4">${quadrupleRooms}</a>
            </li>
        </ul>
    </div>
    <c:if test="${sessionScope.userRole}">
        <button class="btn btn-info" type="button" data-toggle="modal"
                data-target="#addRoomModal">${addRoomButton}</button>
        <div id="addRoomModal" class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button class="close" type="button" data-dismiss="modal">×</button>
                        <h4 class="modal-title">${addRoomButton}</h4>
                    </div>
                    <div class="modal-body">
                        <form action="${pageContext.request.contextPath}/Controller" method="post">
                            <input type="hidden" name="command" value="addRoom"/>
                            <div class="form-group">
                                <label for="addRoomNumber">${number}:</label>
                                <input name="addFormRoomNumber" type="number"
                                       max="999" min="100" class="form-control" id="addRoomNumber">
                            </div>
                            <div class="form-group">
                                <label for="addRoomSeatsNumber">${seatsNumber}:</label>
                                <input name="addFormRoomSeatsNumber" type="number"
                                       max="5" min="1" class="form-control" id="addRoomSeatsNumber">
                            </div>
                            <div class="form-group">
                                <label for="addRoomPerdayCost">${perdayCost}:</label>
                                <input name="addFormRoomPerdayCost" type="number"
                                       max="300" min="10" class="form-control" id="addRoomPerdayCost">
                            </div>
                            <div class="form-group">
                                <button type="submit" class="btn btn-default">${applyButton}</button>
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
        <c:if test="${param.existingRoomError}">
            <div class="alert alert-danger fade in">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    ${existingRoomError}
            </div>
        </c:if>
        <c:if test="${param.addRoomSuccess}">
            <div class="alert alert-success fade in">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    ${addRoomSuccess}
            </div>
        </c:if>
        <c:if test="${param.editRoomSuccess}">
            <div class="alert alert-success fade in">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    ${editRoomSuccess}
            </div>
        </c:if>
        <c:if test="${param.deleteRoomSuccess}">
            <div class="alert alert-success fade in">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    ${deleteRoomSuccess}
            </div>
        </c:if>
    </c:if>
    <hr>
    <table class="table table-striped" id="room-table">
        <tr>
            <c:if test="${sessionScope.userRole}">
                <th></th>
            </c:if>
            <th>${number}</th>
            <th>${seatsNumber}</th>
            <th>${perdayCost}</th>
        </tr>
        <c:forEach var="room" items="${requestScope.rooms}">
            <tr>
                <c:if test="${sessionScope.userRole}">
                    <td>
                        <a href="${pageContext.request.contextPath}/Controller?command=deleteRoom&deleteFormRoomNumber=${room.number}"
                           class="btn btn-default">${deleteButton}</a>
                        <button class="btn btn-default" type="button" data-toggle="modal"
                                data-target="#editRoomModal${room.number}">${editButton}</button>
                        <div id="editRoomModal${room.number}" class="modal fade">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button class="close" type="button" data-dismiss="modal">×</button>
                                        <h4 class="modal-title">${editRoom}</h4>
                                    </div>
                                    <div class="modal-body">
                                        <form action="${pageContext.request.contextPath}/Controller" method="post">
                                            <input type="hidden" name="command" value="editRoom"/>
                                            <input type="hidden" name="editFormOriginalRoomNumber"
                                                   value="${room.number}"/>
                                            <div class="form-group">
                                                <label for="editRoomNumber">${number}:</label>
                                                <input name="editFormRoomNumber" type="number" value="${room.number}"
                                                       max="999" min="100" class="form-control" id="editRoomNumber">
                                            </div>
                                            <div class="form-group">
                                                <label for="editRoomSeatsNumber">${seatsNumber}:</label>
                                                <input name="editFormRoomSeatsNumber" type="number"
                                                       value="${room.seatsNumber}"
                                                       max="5" min="1" class="form-control" id="editRoomSeatsNumber">
                                            </div>
                                            <div class="form-group">
                                                <label for="editRoomPerdayCost">${perdayCost}:</label>
                                                <input name="editFormRoomPerdayCost" type="number"
                                                       value="${room.perdayCost}"
                                                       max="300" min="10" class="form-control" id="editRoomPerdayCost">
                                            </div>
                                            <div class="form-group">
                                                <button type="submit" class="btn btn-default">${saveButton}</button>
                                            </div>
                                        </form>
                                    </div>
                                    <div class="modal-footer">
                                        <button class="btn btn-default" type="button"
                                                data-dismiss="modal">${closeButton}</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </td>
                </c:if>
                <td>${room.number}</td>
                <td>${room.seatsNumber}</td>
                <td>${room.perdayCost}</td>
            </tr>
        </c:forEach>
    </table>
    <button onclick="getRooms(2)">more</button>
    <hr>

</div>

<c:import url="template/footer.jsp"/>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ajaxviewrooms.js"></script>
</body>
</html>
