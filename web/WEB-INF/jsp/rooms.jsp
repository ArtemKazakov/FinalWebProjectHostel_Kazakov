<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Номера</title>
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
                <form class="navbar-form navbar-right" action="Controller" method="post">
                    <input type="hidden" name="command" value="log_in" />
                    <div class="form-group">
                        <input type="text" name="logInFormLogin" placeholder="Логин" class="form-control">
                    </div>
                    <div class="form-group">
                        <input type="password" name="logInFormPassword" placeholder="Пароль" class="form-control">
                    </div>
                    <button type="submit" class="btn btn-success">Войти</button>
                    <a class="btn btn-success" href="Controller?command=registration">Зарегистрироваться</a>
                </form>
            </c:if>
            <c:if test="${sessionScope.userId != null}">
                <form class="navbar-form navbar-right" action="Controller" method="post">
                    <input type="hidden" name="command" value="log_out" />
                    <button type="submit" class="btn btn-success">Выйти</button>
                </form>
            </c:if>
        </div>
    </div>
</nav>

<div class="container">
    <table class="table table-striped">
        <tr>
            <th>Номер</th>
            <th>Вместимость</th>
            <th>Цена за день</th>
        </tr>
        <c:forEach var="room" items="${rooms}">
            <tr>
                <td>${room.number}</td>
                <td>${room.seatsNumber}</td>
                <td>${room.perdayCost}</td>
            </tr>
        </c:forEach>
    </table>

    <hr>

    <footer>
        <p>&copy; 2016 Хостел</p>
    </footer>
</div>

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>  </body>
</html>
