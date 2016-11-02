<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 02.11.2016
  Time: 3:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Комнаты</title>
</head>
<body>
<table>
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
</body>
</html>
