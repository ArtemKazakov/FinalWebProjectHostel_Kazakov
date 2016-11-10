<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 10.11.2016
  Time: 0:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Личный кабинет</title>
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
        <c:if test="${sessionScope.userId == requestScope.user.id}">
            <ul>
                <li>Логин: ${requestScope.user.login}</li>
                <li>Пароль: ${requestScope.user.password}</li>
                <li>Забанен: ${requestScope.user.banned}</li>
                <li>Количество посещений: ${requestScope.user.visitsNumber}</li>
                <li>Номер паспорта: ${requestScope.user.passport.identificationNumber}</li>
                <li>Серия паспорта: ${requestScope.user.passport.series}</li>
                <li>Фамилия: ${requestScope.user.passport.surname}</li>
                <li>Имя: ${requestScope.user.passport.name}</li>
                <li>Отчество: ${requestScope.user.passport.lastName}</li>
                <li>Дата рождения: ${requestScope.user.passport.birthday}</li>

            </ul>
        </c:if>

        <c:if test="${requestScope.serviceError}">
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
    </div>

    <hr>

    <footer>
        <p>&copy; 2016 Хостел</p>
    </footer>
</div>

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>  </body>
</html>
