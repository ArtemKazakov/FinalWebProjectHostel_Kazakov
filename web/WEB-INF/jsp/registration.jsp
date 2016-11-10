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
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Регистрация</title>
    <link rel="shortcut icon" href="img/hostel_icon.png" type="image/png">
    <link rel="stylesheet" href="css/bootstrap.css">
    <link rel="stylesheet" href="css/style.css">
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
            <a class="navbar-brand" href="/">Хостел</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li><a href="Controller?command=viewRoomsList">Номера</a></li>
            </ul>
            <c:if test="${sessionScope.userId == null}">
                <ul class="nav navbar-nav navbar-right" >
                    <li><form class="navbar-form" action="Controller" method="post">
                        <input type="hidden" name="command" value="log_in" />
                        <div class="form-group">
                            <input type="text" name="logInFormLogin" placeholder="Логин" class="form-control">
                        </div>
                        <div class="form-group">
                            <input type="password" name="logInFormPassword" placeholder="Пароль" class="form-control">
                        </div>
                        <button type="submit" class="btn btn-success">Войти</button>
                    </form></li>
                    <li><a href="Controller?command=registration"><span class="glyphicon glyphicon-user"></span> Зарегистрироваться</a></li>
                </ul>
            </c:if>
            <c:if test="${sessionScope.userId != null}">
                <ul class="nav navbar-nav navbar-right" >
                    <li><form class="navbar-form navbar-right" action="Controller" method="post">
                        <input type="hidden" name="command" value="log_out" />
                        <button type="submit" class="btn btn-success">Выйти</button>
                    </form></li>
                    <li><a href="Controller?command=viewUser&id=${sessionScope.userId}"><span class="glyphicon glyphicon-user"></span> Мой аккаунт</a></li>
                </ul>
            </c:if>
        </div>
    </div>
</nav>

<div class="container">
    <div class="jumbotron">
        <form action="Controller" method="post">
            <input type="hidden" name="command" value="registration" />
            <div class="form-group">
                <label for="login">Логин:</label>
                <input name="registrationFormLogin" type="text" value="${requestScope.registrationFormLogin}" minlength="1" maxlength="40" class="form-control" id="login">
            </div>
            <div class="form-group">
                <label for="password">Пароль:</label>
                <input name="registrationFormPassword" type="password" minlength="1" maxlength="45" class="form-control" id="password" >
            </div>
            <div class="form-group">
                <label for="identification-number">Номер паспорта:</label>
                <input name="registrationFormIdentificationNumber" type="text" value="${requestScope.registrationIdentificationNumber}" minlength="7" maxlength="7" class="form-control" id="identification-number" >
            </div>
            <div class="form-group">
                <label for="series">Серия паспорта:</label>
                <input name="registrationFormSeries" type="text" value="${requestScope.registrationFormSeries}" minlength="2" maxlength="2" class="form-control" id="series" >
            </div>
            <div class="form-group">
                <label for="surtname">Фамилия:</label>
                <input name="registrationFormSurname" type="text" value="${requestScope.registrationFormSurname}" minlength="1" maxlength="40" class="form-control" id="surtname" >
            </div>
            <div class="form-group">
                <label for="name">Имя:</label>
                <input name="registrationFormName" type="text" value="${requestScope.registrationFormName}" minlength="1" maxlength="40" class="form-control" id="name" >
            </div>
            <div class="form-group">
                <label for="lastname">Отчество:</label>
                <input name="registrationFormLastName" type="text" value="${requestScope.registrationFormLastName}" minlength="1" maxlength="40" class="form-control" id="lastname" >
            </div>
            <div class="form-group">
                <label for="date">Дата:</label>
                <input name="registrationFormBirthdayDate" type="date" value="${requestScope.registrationFormBirthdayDate}"
                       max="1998-01-01" min="1920-01-01" class="form-control" id="date">
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-default">Зарегистрироваться</button>
            </div>
        </form>

        <c:if test="${requestScope.registrationSuccess}">
            <div class="alert alert-success fade in">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    Регистрация прошла успешно.
            </div>
        </c:if>
        <c:if test="${requestScope.wrongLogin}">
            <div class="alert alert-danger fade in">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    Данный логин уже занят или введен некоректно.
            </div>
        </c:if>
        <c:if test="${requestScope.wrongPassword}">
            <div class="alert alert-danger fade in">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                Неверный пароль.
            </div>
        </c:if>
        <c:if test="${requestScope.serviceError}">
            <div class="alert alert-danger fade in">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    Ошибка сервера.
            </div>
        </c:if>
    </div>

    <hr>

    <footer>
        <p>&copy; 2016 Хостел</p>
    </footer>
</div>

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
</body>
</html>
