<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 10.11.2016
  Time: 0:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
<f:message bundle="${locale}" key="locale.wrongLogin" var="wrongLogin"/>
<f:message bundle="${locale}" key="locale.wrongPassword" var="wrongPassword"/>
<f:message bundle="${locale}" key="locale.serverError" var="serverError"/>


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
                <span class="sr-only">Навигация</span>
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
                    <li><a href="Controller?command=userAccount&userId=${sessionScope.userId}"><span class="glyphicon glyphicon-user"></span> ${accountButton}</a></li>
                </c:if>
                <c:if test='${requestScope.selectedLanguage eq "EN"}'><li><form class="navbar-form" id="change-language-ru" action="Controller?command=changeLanguage" method="post"><input type="hidden" name="changeLanguage" value="RU"><button type="submit" class="btn btn-danger">RU</button></form></li></c:if>
                <c:if test='${requestScope.selectedLanguage eq "RU"}'><li><form class="navbar-form" id="change-language-en" action="Controller?command=changeLanguage" method="post"><input type="hidden" name="changeLanguage" value="EN"><button type="submit" class="btn btn-danger">EN</button></form></li></c:if>
            </ul>
        </div>
    </div>
</nav>


<div class="container">
    <div class="jumbotron">
        <div id="user-info">
                <h3>Персональные данные</h3>
                <ul class="list-group">
                    <li class="list-group-item">Логин: ${requestScope.user.login}</li>
                    <li class="list-group-item">Забанен:  <span class="glyphicon <c:if test="${requestScope.user.banned == true}">glyphicon-ok</c:if> <c:if test="${requestScope.user.banned == false}">glyphicon-remove</c:if>"></span></li>
                    <li class="list-group-item">Количество посещений: ${requestScope.user.visitsNumber}</li>
                    <li class="list-group-item">Номер паспорта: ${requestScope.user.passport.identificationNumber}</li>
                    <li class="list-group-item">Серия паспорта: ${requestScope.user.passport.series}</li>
                    <li class="list-group-item">Фамилия: ${requestScope.user.passport.surname}</li>
                    <li class="list-group-item">Имя: ${requestScope.user.passport.name}</li>
                    <li class="list-group-item">Отчество: ${requestScope.user.passport.patronymic}</li>
                    <li class="list-group-item">Дата рождения: ${requestScope.user.passport.birthday}</li>
                </ul>
                <button class="btn btn-info" type="button" data-toggle="modal" data-target="#myModal">Редактировать</button>
                <div id="myModal" class="modal fade">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header"><button class="close" type="button" data-dismiss="modal">×</button>
                                <h4 class="modal-title">Редактирование данных</h4>
                            </div>
                            <div class="modal-body">
                                <form action="Controller" method="post">
                                    <input type="hidden" name="command" value="editUser" />
                                    <input type="hidden" name="userId" value="${requestScope.user.id}">
                                    <div class="form-group">
                                        <label for="login">Логин:</label>
                                        <input name="editFormLogin" type="text" value="${requestScope.user.login}" minlength="1" maxlength="40" class="form-control" id="login">
                                    </div>
                                    <div class="form-group">
                                        <label for="password">Пароль:</label>
                                        <input name="editFormPassword" type="password" minlength="1" maxlength="45" class="form-control" id="password" >
                                    </div>
                                    <div class="form-group">
                                        <label for="identification-number">Номер паспорта:</label>
                                        <input name="editFormIdentificationNumber" type="text" value="${requestScope.user.passport.identificationNumber}" minlength="7" maxlength="7" class="form-control" id="identification-number" >
                                    </div>
                                    <div class="form-group">
                                        <label for="series">Серия паспорта:</label>
                                        <input name="editFormSeries" type="text" value="${requestScope.user.passport.series}" minlength="2" maxlength="2" class="form-control" id="series" >
                                    </div>
                                    <div class="form-group">
                                        <label for="surtname">Фамилия:</label>
                                        <input name="editFormSurname" type="text" value="${requestScope.user.passport.surname}" minlength="1" maxlength="40" class="form-control" id="surtname" >
                                    </div>
                                    <div class="form-group">
                                        <label for="name">Имя:</label>
                                        <input name="editFormName" type="text" value="${requestScope.user.passport.name}" minlength="1" maxlength="40" class="form-control" id="name" >
                                    </div>
                                    <div class="form-group">
                                        <label for="patronymic">Отчество:</label>
                                        <input name="editFormPatronymic" type="text" value="${requestScope.user.passport.patronymic}" minlength="1" maxlength="40" class="form-control" id="patronymic" >
                                    </div>
                                    <div class="form-group">
                                        <label for="date">Дата:</label>
                                        <input name="editFormBirthdayDate" type="date" value="${requestScope.user.passport.birthday}"
                                               max="1998-01-01" min="1920-01-01" class="form-control" id="date">
                                    </div>
                                    <div class="form-group">
                                        <button type="submit" class="btn btn-default">Сохранить</button>
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer"><button class="btn btn-default" type="button" data-dismiss="modal">Закрыть</button></div>
                        </div>
                    </div>
                </div>
            <br><br>
            <c:if test="${param.serviceError}">
                <div class="alert alert-danger fade in">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    Ошибка сервера.
                </div>
            </c:if>
            <c:if test="${requestScope.user == null}">
                <div class="alert alert-danger fade in">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    Пользователя не сущетвует.
                </div>
            </c:if>
            <c:if test="${param.editSuccess}">
                <br>
                <div class="alert alert-success fade in">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    Редактирование прошло успешно.
                </div>
            </c:if>
            <c:if test="${param.wrongLogin}">
                <div class="alert alert-danger fade in">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    Данный логин уже занят или введен некоректно.
                </div>
            </c:if>
            <c:if test="${param.wrongPassword}">
                <div class="alert alert-danger fade in">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    Неверный пароль.
                </div>
            </c:if>
            <h3>Скидки</h3>
            <table class="table table-striped">
                <tr>
                    <th>Величина</th>
                    <th>Использована</th>
                </tr>
                <c:forEach var="discount" items="${requestScope.discounts}">
                    <tr>
                        <td>${discount.value}</td>
                        <td><span class="glyphicon <c:if test="${discount.used == true}">glyphicon-ok</c:if> <c:if test="${discount.used == false}">glyphicon-remove</c:if>"></span></td>
                    </tr>
                </c:forEach>
            </table>
            <c:if test="${sessionScope.userRole}">
            <h3>Дать скидку</h3>
            <form action="Controller" method="post">
                <input type="hidden" name="command" value="addDiscount" />
                <input type="hidden" name="discountFormClientId" value="${requestScope.user.id}"/>
                <div class="form-group">
                    <label for="discountValue">Величина:</label>
                    <input name="discountFormValue" type="number" value="0"
                           max="1000" min="1" class="form-control" id="discountValue">
                </div>
                <div class="form-group">
                    <button type="submit" class="btn btn-default">Применить</button>
                </div>
            </c:if>
        </div>
    </div>

    <hr>

    <footer>
        <p>&copy; 2016 ${siteName}</p>
    </footer>
</div>

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>  </body>
</html>
