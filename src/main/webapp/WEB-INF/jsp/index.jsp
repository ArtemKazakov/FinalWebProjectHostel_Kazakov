<%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setLocale value="${requestScope.selectedLanguage}"/>
<f:setBundle basename="local" var="local"/>
<f:message bundle="${local}" key="local.siteName" var="siteName"/>
<f:message bundle="${local}" key="local.rooms" var="rooms"/>
<f:message bundle="${local}" key="local.singleRooms" var="singleRoom"/>
<f:message bundle="${local}" key="local.doubleRooms" var="doubleRoom"/>
<f:message bundle="${local}" key="local.tripleRooms" var="tripleRoom"/>
<f:message bundle="${local}" key="local.quadrupleRooms" var="quadrupleRoom"/>
<f:message bundle="${local}" key="local.plusHeader" var="plusHeader"/>
<f:message bundle="${local}" key="local.plus1header" var="plus1header"/>
<f:message bundle="${local}" key="local.plus2header" var="plus2header"/>
<f:message bundle="${local}" key="local.plus3header" var="plus3header"/>
<f:message bundle="${local}" key="local.plus4header" var="plus4header"/>
<f:message bundle="${local}" key="local.plus5header" var="plus5header"/>
<f:message bundle="${local}" key="local.plus6header" var="plus6header"/>
<f:message bundle="${local}" key="local.plus1" var="plus1"/>
<f:message bundle="${local}" key="local.plus2" var="plus2"/>
<f:message bundle="${local}" key="local.plus3" var="plus3"/>
<f:message bundle="${local}" key="local.plus4" var="plus4"/>
<f:message bundle="${local}" key="local.plus5" var="plus5"/>
<f:message bundle="${local}" key="local.plus6" var="plus6"/>
<f:message bundle="${local}" key="local.mainPageText1" var="mainPageText1"/>
<f:message bundle="${local}" key="local.mainPageText2" var="mainPageText2"/>
<f:message bundle="${local}" key="local.mainPageText3" var="mainPageText3"/>
<f:message bundle="${local}" key="local.mainPageTextHeader" var="mainPageTextHeader"/>
<f:message bundle="${local}" key="local.wrongLogin" var="wrongLogin"/>
<f:message bundle="${local}" key="local.wrongPassword" var="wrongPassword"/>
<f:message bundle="${local}" key="local.serverError" var="serverError"/>
<f:message bundle="${local}" key="local.userBanned" var="userBanned"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${siteName}</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/hostel_icon.png" type="image/png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<c:import url="template/header.jsp"/>

<div class="jumbotron">
    <div class="container">
        <c:if test="${param.userBanned}">
            <div class="alert alert-danger fade in">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    ${userBanned}
            </div>
        </c:if>
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
            <div class="st1">${mainPageText1}&nbsp<a
                    href="${pageContext.request.contextPath}/Controller?command=registrationPage">${mainPageText2}</a>
                <br> ${mainPageText3}</div>
            <br>
            <div class="row">
                <div class="col-md-6">
                    <a href="${pageContext.request.contextPath}/Controller?command=viewRoomsList&seatsNumber=1"
                       class="banner">
                        <img src="${pageContext.request.contextPath}/img/room1.jpg" alt="${singleRoom}">
                        <div class="bann_capt"><span>${singleRoom}</span></div>
                    </a>
                </div>
                <div class="col-md-6">
                    <a href="${pageContext.request.contextPath}/Controller?command=viewRoomsList&seatsNumber=2"
                       class="banner">
                        <img src="${pageContext.request.contextPath}/img/room2.jpg" alt="${doubleRoom}">
                        <div class="bann_capt"><span>${doubleRoom}</span></div>
                    </a>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <a href="${pageContext.request.contextPath}/Controller?command=viewRoomsList&seatsNumber=3"
                       class="banner">
                        <img src="${pageContext.request.contextPath}/img/room3.jpg" alt="${tripleRoom}">
                        <div class="bann_capt"><span>${tripleRoom}</span></div>
                    </a>
                </div>
                <div class="col-md-6">
                    <a href="${pageContext.request.contextPath}/Controller?command=viewRoomsList&seatsNumber=4"
                       class="banner">
                        <img src="${pageContext.request.contextPath}/img/room4.jpg" alt="${quadrupleRoom}">
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
            <img src="${pageContext.request.contextPath}/img/ico1.png" alt="" class="img_inner">
            <div class="extra_wrapper">
                <div class="text1">
                    <h4>${plus1header}</h4>
                </div>
                ${plus1}
            </div>
            <div class="clear"></div>
        </div>
        <div class="col-md-6">
            <img src="${pageContext.request.contextPath}/img/ico2.png" alt="" class="img_inner">
            <div class="extra_wrapper">
                <div class="text1">
                    <h4>${plus2header}</h4>
                </div>
                ${plus2}
            </div>
        </div>
        <div class="col-md-6">
            <img src="${pageContext.request.contextPath}/img/ico3.png" alt="" class="img_inner">
            <div class="extra_wrapper">
                <div class="text1">
                    <h4>${plus3header}</h4>
                </div>
                ${plus3}
            </div>
            <div class="clear"></div>
        </div>
        <div class="col-md-6">
            <img src="${pageContext.request.contextPath}/img/ico4.png" alt="" class="img_inner">
            <div class="extra_wrapper">
                <div class="text1">
                    <h4>${plus4header}</h4>
                </div>
                ${plus4}
            </div>
        </div>
        <div class="col-md-6">
            <img src="${pageContext.request.contextPath}/img/ico5.png" alt="" class="img_inner">
            <div class="extra_wrapper">
                <div class="text1">
                    <h4>${plus5header}</h4>
                </div>
                ${plus5}
            </div>
        </div>
        <div class="col-md-6">
            <img src="${pageContext.request.contextPath}/img/ico6.png" alt="" class="img_inner">
            <div class="extra_wrapper">
                <div class="text1">
                    <h4>${plus6header}</h4>
                </div>
                ${plus6}
            </div>
        </div>
    </div>

    <hr>

</div>

<c:import url="template/footer.jsp"/>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>