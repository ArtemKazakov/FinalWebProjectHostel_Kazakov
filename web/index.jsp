<%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Хостел</title>
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
          <li><a href="Controller?command=viewUser&userId=${sessionScope.userId}"><span class="glyphicon glyphicon-user"></span> Мой аккаунт</a></li>
        </ul>
      </c:if>
    </div>
  </div>
</nav>


<div class="jumbotron">
  <div class="container">
    <c:if test="${requestScope.wrongLogin}">
      <div class="alert alert-danger fade in">
        <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
          Неверный логин.
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
    <h1>Информация</h1>
    <p>Информация</p>
    <p><a class="btn btn-primary btn-lg" href="#" role="button">Узнать больше &raquo;</a></p>
  </div>
</div>

<div class="container">
  <div class="row">
    <div class="col-md-4">
      <h2>Информация</h2>
      <p>Информация </p>
      <p><a class="btn btn-default" href="#" role="button">Подробнее &raquo;</a></p>
    </div>
    <div class="col-md-4">
      <h2>Информация</h2>
      <p>Информация </p>
      <p><a class="btn btn-default" href="#" role="button">Подробнее &raquo;</a></p>
    </div>
    <div class="col-md-4">
      <h2>Информация</h2>
      <p>Информация </p>
      <p><a class="btn btn-default" href="#" role="button">Подробнее &raquo;</a></p>
    </div>
  </div>

  <hr>

  <footer>
    <p>&copy; 2016 Хостел</p>
  </footer>
</div>

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>  </body>
</html>