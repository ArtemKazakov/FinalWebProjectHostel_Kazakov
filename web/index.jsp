<%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Test</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
  </head>
  <body>

  <form action="Controller" method="post">
    <input type="hidden" name="command" value="log_in" />
    <p>Логин:
      <input type="text" name="logInFormLogin" /></p>
    <p>Пароль:
      <input type="text" name="logInFormPassword" /></p>
    <br>
    <input type="submit" value="Войти" /><br />
  </form>
  <p>${userLogin}</p>
  <form action="Controller" method="post">
    <input type="hidden" name="command" value="log_out" />
    <input type="submit" value="Выйти" /><br />
  </form>
    <hr>

  <form action="Controller" method="post">
    <input type="hidden" name="command" value="registration" />
    <p>Логин:
      <input type="text" name="registrationFormLogin" /></p>
    <p>Пароль:
      <input type="text" name="registrationFormPassword" /></p>
    <p>Номер пасспорта:
      <input type="text" name="registrationIdentificationNumber" /></p>
    <p>Серия пасспорта:
      <input type="text" name="registrationFormSeries" /></p>
    <p>Фамилия:
      <input type="text" name="registrationFormSurname" /></p>
    <p>Имя:
      <input type="text" name="registrationFormName" /></p>
    <p>Отчество:
      <input type="text" name="registrationFormLastName" /></p>
    <p>Дата:
      <input type="date" name="registrationBirthdayDate" /></p>
    <br>
    <input type="submit" value="Зарегистрироваться" /><br />
  </form>

    <hr>
  <form action="Controller" method="post">
    <input type="hidden" name="command" value="viewRoomsList" />
    <input type="submit" value="Показать все комнаты" /><br />
  </form>
  <hr>
    <p>${status}</p>


  </body>
</html>