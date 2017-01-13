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
<f:message bundle="${locale}" key="locale.loginButton" var="loginButton"/>
<f:message bundle="${locale}" key="locale.logoutButtom" var="logoutButton"/>
<f:message bundle="${locale}" key="locale.signupButton" var="signupButton"/>
<f:message bundle="${locale}" key="locale.account" var="accountButton"/>
<f:message bundle="${locale}" key="locale.rooms" var="rooms"/>
<f:message bundle="${locale}" key="locale.number" var="number"/>
<f:message bundle="${locale}" key="locale.seatsNumber" var="seatsNumber"/>
<f:message bundle="${locale}" key="locale.perdayCost" var="perdayCost"/>
<f:message bundle="${locale}" key="locale.singleRooms" var="singleRooms"/>
<f:message bundle="${locale}" key="locale.doubleRooms" var="doubleRooms"/>
<f:message bundle="${locale}" key="locale.tripleRooms" var="tripleRooms"/>
<f:message bundle="${locale}" key="locale.quadrupleRooms" var="quadrupleRooms"/>
<f:message bundle="${locale}" key="locale.allRooms" var="allRooms"/>
<f:message bundle="${locale}" key="locale.addRoomButton" var="addRoomButton"/>
<f:message bundle="${locale}" key="locale.applyButton" var="applyButton"/>
<f:message bundle="${locale}" key="locale.serverError" var="serverError"/>
<f:message bundle="${locale}" key="locale.addRoomSuccess" var="addRoomSuccess"/>
<f:message bundle="${locale}" key="locale.editRoomSuccess" var="editRoomSuccess"/>
<f:message bundle="${locale}" key="locale.deleteRoomSuccess" var="deleteRoomSuccess"/>
<f:message bundle="${locale}" key="locale.existingRoomError" var="existingRoomError"/>
<f:message bundle="${locale}" key="locale.closeButton" var="closeButton"/>
<f:message bundle="${locale}" key="locale.deleteButton" var="deleteButton"/>
<f:message bundle="${locale}" key="locale.editButton" var="editButton"/>
<f:message bundle="${locale}" key="locale.saveButton" var="saveButton"/>
<f:message bundle="${locale}" key="locale.editRoom" var="editRoom"/>


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
                        <form class="navbar-form navbar-right" action="Controller" method="post">
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
    <br>
    <div class="btn-group">
        <button type="button" data-toggle="dropdown" class="btn btn-info dropdown-toggle">
            ${rooms}
            <span class="caret"></span>
        </button>
        <ul class="dropdown-menu">
            <li><a href="Controller?command=viewRoomsList">${allRooms}</a></li>
            <li><a href="Controller?command=viewRoomsList&seatsNumber=1">${singleRooms}</a></li>
            <li><a href="Controller?command=viewRoomsList&seatsNumber=2">${doubleRooms}</a></li>
            <li><a href="Controller?command=viewRoomsList&seatsNumber=3">${tripleRooms}</a></li>
            <li><a href="Controller?command=viewRoomsList&seatsNumber=4">${quadrupleRooms}</a></li>
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
                        <form action="Controller" method="post">
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
    <table class="table table-striped">
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
                        <a href="Controller?command=deleteRoom&deleteFormRoomNumber=${room.number}"
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
                                        <form action="Controller" method="post">
                                            <input type="hidden" name="command" value="editRoom"/>
                                            <input type="hidden" name="editFormOriginalRoomNumber" value="${room.number}"/>
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

    <hr>

    <footer>
        <p>&copy; 2016 ${siteName}</p>
    </footer>
</div>

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
</body>
</html>
