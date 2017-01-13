<%-- Created by IntelliJ IDEA. --%>
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
<f:message bundle="${locale}" key="locale.singleRooms" var="singleRoom"/>
<f:message bundle="${locale}" key="locale.doubleRooms" var="doubleRoom"/>
<f:message bundle="${locale}" key="locale.tripleRooms" var="tripleRoom"/>
<f:message bundle="${locale}" key="locale.quadrupleRooms" var="quadrupleRoom"/>
<f:message bundle="${locale}" key="locale.plusHeader" var="plusHeader"/>
<f:message bundle="${locale}" key="locale.plus1header" var="plus1header"/>
<f:message bundle="${locale}" key="locale.plus2header" var="plus2header"/>
<f:message bundle="${locale}" key="locale.plus3header" var="plus3header"/>
<f:message bundle="${locale}" key="locale.plus4header" var="plus4header"/>
<f:message bundle="${locale}" key="locale.plus5header" var="plus5header"/>
<f:message bundle="${locale}" key="locale.plus6header" var="plus6header"/>
<f:message bundle="${locale}" key="locale.plus1" var="plus1"/>
<f:message bundle="${locale}" key="locale.plus2" var="plus2"/>
<f:message bundle="${locale}" key="locale.plus3" var="plus3"/>
<f:message bundle="${locale}" key="locale.plus4" var="plus4"/>
<f:message bundle="${locale}" key="locale.plus5" var="plus5"/>
<f:message bundle="${locale}" key="locale.plus6" var="plus6"/>
<f:message bundle="${locale}" key="locale.mainPageText1" var="mainPageText1"/>
<f:message bundle="${locale}" key="locale.mainPageText2" var="mainPageText2"/>
<f:message bundle="${locale}" key="locale.mainPageText3" var="mainPageText3"/>
<f:message bundle="${locale}" key="locale.mainPageTextHeader" var="mainPageTextHeader"/>
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


<div class="jumbotron">
    <div class="container">
        <c:if test="${param.wrongLogin}">
            <div class="alert alert-danger fade in">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    ${wrongLogin}
            </div>
        </c:if>
        <c:if test="${param.wrongPassword}">
            <div class="alert alert-danger fade in">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    ${wrongPassword}
            </div>
        </c:if>
        <c:if test="${param.serviceError}">
            <div class="alert alert-danger fade in">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    ${serverError}
            </div>
        </c:if>
        <div class="ta_center">
            <h1>${mainPageTextHeader}</h1>
            <div class="st1">${mainPageText1} <a href="Controller?command=registration">${mainPageText2}</a>
                <br> ${mainPageText3}</div>
            <br>
            <div class="row">
                <div class="col-md-6">
                    <a href="Controller?command=viewRoomsList&seatsNumber=1" class="banner">
                        <img src="../../img/room1.jpg" alt="${singleRoom}">
                        <div class="bann_capt"><span>${singleRoom}</span></div>
                    </a>
                </div>
                <div class="col-md-6">
                    <a href="Controller?command=viewRoomsList&seatsNumber=2" class="banner">
                        <img src="../../img/room2.jpg" alt="${doubleRoom}">
                        <div class="bann_capt"><span>${doubleRoom}</span></div>
                    </a>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <a href="Controller?command=viewRoomsList&seatsNumber=3" class="banner">
                        <img src="../../img/room3.jpg" alt="${tripleRoom}">
                        <div class="bann_capt"><span>${tripleRoom}</span></div>
                    </a>
                </div>
                <div class="col-md-6">
                    <a href="Controller?command=viewRoomsList&seatsNumber=4" class="banner">
                        <img src="../../img/room4.jpg" alt="${quadrupleRoom}">
                        <div class="bann_capt"><span>${quadrupleRoom}</span></div>
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="container">
    <div class="row">
        <div class="col-md-12">
            <h2 class="head1">${plusHeader}</h2>
        </div>
        <div class="col-md-6">
            <img src="../../img/ico1.png" alt="" class="img_inner">
            <div class="extra_wrapper">
                <div class="text1">
                    <h4>${plus1header}</h4>
                </div>
                ${plus1}
            </div>
            <div class="clear"></div>
        </div>
        <div class="col-md-6">
            <img src="../../img/ico2.png" alt="" class="img_inner">
            <div class="extra_wrapper">
                <div class="text1">
                    <h4>${plus2header}</h4>
                </div>
                ${plus2}
            </div>
        </div>
        <div class="col-md-6">
            <img src="../../img/ico3.png" alt="" class="img_inner">
            <div class="extra_wrapper">
                <div class="text1">
                    <h4>${plus3header}</h4>
                </div>
                ${plus3}
            </div>
            <div class="clear"></div>
        </div>
        <div class="col-md-6">
            <img src="../../img/ico4.png" alt="" class="img_inner">
            <div class="extra_wrapper">
                <div class="text1">
                    <h4>${plus4header}</h4>
                </div>
                ${plus4}
            </div>
        </div>
        <div class="col-md-6">
            <img src="../../img/ico5.png" alt="" class="img_inner">
            <div class="extra_wrapper">
                <div class="text1">
                    <h4>${plus5header}</h4>
                </div>
                ${plus5}
            </div>
        </div>
        <div class="col-md-6">
            <img src="../../img/ico6.png" alt="" class="img_inner">
            <div class="extra_wrapper">
                <div class="text1">
                    <h4>${plus6header}</h4>
                </div>
                ${plus6}
            </div>
        </div>
    </div>

    <hr>

    <footer>
        <p>&copy; 2016 ${siteName}</p>
    </footer>
</div>

<script type="text/javascript" src="../../js/jquery.min.js"></script>
<script type="text/javascript" src="../../js/bootstrap.min.js"></script>
</body>
</html>