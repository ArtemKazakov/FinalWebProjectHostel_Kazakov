<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 30.01.2017
  Time: 4:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setBundle basename="local" var="local"/>
<f:message bundle="${local}" key="local.siteName" var="siteName"/>

<footer class="container">
    <p>&copy; 2016 ${siteName}</p>
</footer>