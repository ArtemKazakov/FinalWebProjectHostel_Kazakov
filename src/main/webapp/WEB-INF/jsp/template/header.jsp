<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 28.01.2017
  Time: 20:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setLocale value="${requestScope.selectedLanguage}"/>
<f:setBundle basename="local" var="local"/>
<f:message bundle="${local}" key="local.mainPage" var="mainPage"/>
<f:message bundle="${local}" key="local.login" var="login"/>
<f:message bundle="${local}" key="local.password" var="password"/>
<f:message bundle="${local}" key="local.loginButton" var="loginButton"/>
<f:message bundle="${local}" key="local.logoutButtom" var="logoutButton"/>
<f:message bundle="${local}" key="local.signupButton" var="signupButton"/>
<f:message bundle="${local}" key="local.account" var="accountButton"/>
<f:message bundle="${local}" key="local.rooms" var="rooms"/>


<header>
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
                <a class="navbar-brand"
                   href="${pageContext.request.contextPath}/Controller?command=mainPage">${mainPage}</a>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
                <ul class="nav navbar-nav">
                    <li><a href="${pageContext.request.contextPath}/Controller?command=viewRoomsList">${rooms}</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <c:if test="${sessionScope.userId == null}">
                        <li>
                            <form class="navbar-form" action="Controller" method="post">
                                <input type="hidden" name="command" value="log_in"/>
                                <div class="form-group">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                                        <input type="text" name="logInFormLogin" placeholder="${login}"
                                               class="form-control">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                                        <input type="password" name="logInFormPassword" placeholder="${password}"
                                               class="form-control">
                                    </div>
                                </div>
                                <button type="submit" class="btn btn-success">${loginButton}</button>
                            </form>
                        </li>
                        <li><a href="${pageContext.request.contextPath}/Controller?command=registrationPage"><span
                                class="glyphicon glyphicon-user"></span> ${signupButton}</a></li>
                    </c:if>
                    <c:if test="${sessionScope.userId != null}">
                        <li>
                            <form class="navbar-form navbar-right" action="Controller" method="post">
                                <input type="hidden" name="command" value="log_out"/>
                                <button type="submit" class="btn btn-success">${logoutButton}</button>
                            </form>
                        </li>
                        <li><a href="${pageContext.request.contextPath}/Controller?command=userAccount"><span
                                class="glyphicon glyphicon-user"></span> ${accountButton}</a></li>
                    </c:if>
                    <c:if test='${requestScope.selectedLanguage eq "EN"}'>
                        <li>
                            <form class="navbar-form" id="change-language-ru"
                                  action="${pageContext.request.contextPath}/Controller?command=changeLanguage"
                                  method="post"><input type="hidden" name="changeLanguage" value="RU">
                                <button type="submit" class="btn btn-danger">RU</button>
                            </form>
                        </li>
                    </c:if>
                    <c:if test='${requestScope.selectedLanguage eq "RU"}'>
                        <li>
                            <form class="navbar-form" id="change-language-en"
                                  action="${pageContext.request.contextPath}/Controller?command=changeLanguage"
                                  method="post"><input type="hidden" name="changeLanguage" value="EN">
                                <button type="submit" class="btn btn-danger">EN</button>
                            </form>
                        </li>
                    </c:if>
                </ul>
            </div>
        </div>
    </nav>
</header>

