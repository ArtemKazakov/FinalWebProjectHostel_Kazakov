<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 10.11.2016
  Time: 0:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setLocale value="${requestScope.selectedLanguage}"/>
<f:setBundle basename="local" var="local"/>
<f:message bundle="${local}" key="local.siteName" var="siteName"/>
<f:message bundle="${local}" key="local.login" var="login"/>
<f:message bundle="${local}" key="local.password" var="password"/>
<f:message bundle="${local}" key="local.repeatPassword" var="repeatPassword"/>
<f:message bundle="${local}" key="local.wrongLogin" var="wrongLogin"/>
<f:message bundle="${local}" key="local.wrongPassword" var="wrongPassword"/>
<f:message bundle="${local}" key="local.editSuccess" var="editSuccess"/>
<f:message bundle="${local}" key="local.serverError" var="serverError"/>
<f:message bundle="${local}" key="local.passportIdNumber" var="passportIdNumber"/>
<f:message bundle="${local}" key="local.passportSeries" var="passportSeries"/>
<f:message bundle="${local}" key="local.surname" var="surname"/>
<f:message bundle="${local}" key="local.name" var="name"/>
<f:message bundle="${local}" key="local.patronymic" var="patronymic"/>
<f:message bundle="${local}" key="local.birthdayDate" var="birthdayDate"/>
<f:message bundle="${local}" key="local.banned" var="banned"/>
<f:message bundle="${local}" key="local.visitsNumber" var="visitsNumber"/>
<f:message bundle="${local}" key="local.emptyField" var="emptyField"/>
<f:message bundle="${local}" key="local.invalidLogin" var="invalidLogin"/>
<f:message bundle="${local}" key="local.invalidPassword" var="invalidPassword"/>
<f:message bundle="${local}" key="local.invalidPassportNumber" var="invalidPassportNumber"/>
<f:message bundle="${local}" key="local.invalidSeries" var="invalidSeries"/>
<f:message bundle="${local}" key="local.invalidName" var="invalidName"/>
<f:message bundle="${local}" key="local.repeatPasswordError" var="repeatPasswordError"/>
<f:message bundle="${local}" key="local.saveButton" var="saveButton"/>
<f:message bundle="${local}" key="local.closeButton" var="closeButton"/>
<f:message bundle="${local}" key="local.editButton" var="editButton"/>
<f:message bundle="${local}" key="local.existUserError" var="existUserError"/>
<f:message bundle="${local}" key="local.personalInformation" var="personalInformation"/>
<f:message bundle="${local}" key="local.editInformation" var="editInformation"/>
<f:message bundle="${local}" key="local.applyButton" var="applyButton"/>
<f:message bundle="${local}" key="local.discounts" var="sales"/>
<f:message bundle="${local}" key="local.value" var="value"/>
<f:message bundle="${local}" key="local.used" var="used"/>
<f:message bundle="${local}" key="local.giveDiscount" var="giveDiscount"/>
<f:message bundle="${local}" key="local.requests" var="rentalRequests"/>
<f:message bundle="${local}" key="local.seatsNumber" var="seatsNumber"/>
<f:message bundle="${local}" key="local.checkInDate" var="chekInDate"/>
<f:message bundle="${local}" key="local.daysNumber" var="daysNumber"/>
<f:message bundle="${local}" key="local.requestType" var="requestType"/>
<f:message bundle="${local}" key="local.payment" var="payment"/>
<f:message bundle="${local}" key="local.requestStatus" var="requestStatus"/>
<f:message bundle="${local}" key="local.reservation" var="reservation"/>
<f:message bundle="${local}" key="local.fullPayment" var="fullPayment"/>
<f:message bundle="${local}" key="local.orderRoom" var="orderRoom"/>
<f:message bundle="${local}" key="local.banButton" var="banButton"/>
<f:message bundle="${local}" key="local.unbanButton" var="unbanButton"/>
<f:message bundle="${local}" key="local.clients" var="clients"/>
<f:message bundle="${local}" key="local.deleteButton" var="deleteButton"/>
<f:message bundle="${local}" key="local.editDiscount" var="editDiscount"/>
<f:message bundle="${local}" key="local.invalidDiscountValue" var="invalidDiscountValue"/>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${siteName} - ${personalInformation}</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/hostel_icon.png" type="image/png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<c:import url="template/header.jsp"/>


<div class="container">
    <div id="user-info">
        <br>
        <c:if test="${sessionScope.userRole == false}">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="${pageContext.request.contextPath}/Controller?command=userAccount">${personalInformation}</a>
                </li>
                <li><a href="${pageContext.request.contextPath}/Controller?command=findSuitableRooms">${orderRoom}</a>
                </li>
            </ul>
        </c:if>
        <c:if test="${sessionScope.userRole}">
            <a href="${pageContext.request.contextPath}/Controller?command=viewUsersList"
               class="btn btn-info">${clients}</a>
        </c:if>
        <div class="row">
            <div class="col-md-6">
                <h2>${personalInformation}</h2>
                <ul class="list-group">
                    <li class="list-group-item"><strong>${login}:</strong> ${requestScope.user.login}</li>
                    <li class="list-group-item"><strong>${banned}:</strong> <span
                            class="glyphicon <c:if test="${requestScope.user.banned == true}">glyphicon-ok</c:if> <c:if test="${requestScope.user.banned == false}">glyphicon-remove</c:if>"></span>
                    </li>
                    <li class="list-group-item"><strong>${visitsNumber}:</strong> ${requestScope.user.visitsNumber}</li>
                    <li class="list-group-item">
                        <strong>${passportIdNumber}:</strong> ${requestScope.user.passport.identificationNumber}</li>
                    <li class="list-group-item"><strong>${passportSeries}:</strong> ${requestScope.user.passport.series}
                    </li>
                    <li class="list-group-item"><strong>${surname}:</strong> ${requestScope.user.passport.surname}</li>
                    <li class="list-group-item"><strong>${name}:</strong> ${requestScope.user.passport.name}</li>
                    <li class="list-group-item"><strong>${patronymic}:</strong> ${requestScope.user.passport.patronymic}
                    </li>
                    <li class="list-group-item"><strong>${birthdayDate}:</strong> ${requestScope.user.passport.birthday}
                    </li>
                </ul>
            </div>
            <div class="col-md-6">
                <h2>${sales}</h2>
                <table class="table table-striped">
                    <tr>
                        <c:if test="${sessionScope.userRole}">
                            <th></th>
                        </c:if>
                        <th>${value}</th>
                        <th>${used}</th>
                    </tr>
                    <c:forEach var="discount" items="${requestScope.discounts}">
                        <tr>
                            <c:if test="${sessionScope.userRole}">
                                <td>
                                    <a href="${pageContext.request.contextPath}/Controller?command=deleteDiscount&userId=${requestScope.user.id}&discountId=${discount.id}"
                                       class="btn btn-info">${deleteButton}</a>
                                    <button class="btn btn-info" type="button" data-toggle="modal"
                                            data-target="#editDiscountModal${discount.id}">${editButton}</button>
                                    <div id="editDiscountModal${discount.id}" class="modal fade">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button class="close" type="button" data-dismiss="modal">×</button>
                                                    <h4 class="modal-title">${editDiscount}</h4>
                                                </div>
                                                <div class="modal-body">
                                                    <form action="${pageContext.request.contextPath}/Controller"
                                                          method="post">
                                                        <input type="hidden" name="command" value="editDiscount"/>
                                                        <input type="hidden" name="userId"
                                                               value="${requestScope.user.id}">
                                                        <input type="hidden" name="discountId" value="${discount.id}">
                                                        <div class="form-group">
                                                            <label for="discountEditValue">${value}:</label>
                                                            <input name="value" type="number" value="${discount.value}"
                                                                   max="1000" min="1" class="form-control"
                                                                   id="discountEditValue"
                                                                   title="${invalidDiscountValue}" required
                                                                   oninvalid='this.setCustomValidity("${emptyField} ${invalidDiscountValue}")'
                                                                   onchange="try{this.setCustomValidity('')}catch(e){}">
                                                        </div>
                                                        <div class=" form-group">
                                                            <button type="submit"
                                                                    class="btn btn-default">${saveButton}</button>
                                                        </div>
                                                    </form>
                                                </div>
                                                <div class="modal-footer">
                                                    <button class="btn btn-default" type="button"
                                                            data-dismiss="modal">${closeButton}</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </c:if>
                            <td>${discount.value}</td>
                            <td><span
                                    class="glyphicon <c:if test="${discount.used == true}">glyphicon-ok</c:if> <c:if test="${discount.used == false}">glyphicon-remove</c:if>"></span>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <c:if test="${sessionScope.userRole}">
                    <h2>${giveDiscount}</h2>
                    <form action="${pageContext.request.contextPath}/Controller" method="post">
                        <input type="hidden" name="command" value="addDiscount"/>
                        <input type="hidden" name="discountFormClientId" value="${requestScope.user.id}"/>
                        <div class="form-group">
                            <label for="discountValue">${value}:</label>
                            <input name="discountFormValue" type="number" value="0"
                                   max="1000" min="1" class="form-control" id="discountValue"
                                   title="${invalidDiscountValue}" required
                                   oninvalid='this.setCustomValidity("${emptyField} ${invalidDiscountValue}")'
                                   onchange="try{this.setCustomValidity('')}catch(e){}">
                        </div>
                        <div class="form-group">
                            <button type="submit" class="btn btn-default">${applyButton}</button>
                        </div>
                    </form>
                </c:if>
            </div>
        </div>
        <c:if test="${sessionScope.userRole}">
            <a href="${pageContext.request.contextPath}/Controller?command=banUser&userId=${requestScope.user.id}&status=<c:if test="${requestScope.user.banned == false}">1</c:if><c:if test="${requestScope.user.banned == true}">0</c:if>"
               class="btn btn-info">
                <c:if test="${requestScope.user.banned ==true}">
                    ${unbanButton}
                </c:if>
                <c:if test="${requestScope.user.banned ==false}">
                    ${banButton}
                </c:if>
            </a>
            <a href="${pageContext.request.contextPath}/Controller?command=deleteUser&userId=${requestScope.user.id}"
               class="btn btn-info">${deleteButton}</a>
        </c:if>
        <button class="btn btn-info" type="button" data-toggle="modal"
                data-target="#editUserModal">${editButton}</button>
        <div id="editUserModal" class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button class="close" type="button" data-dismiss="modal">×</button>
                        <h4 class="modal-title">${editInformation}</h4>
                    </div>
                    <div class="modal-body">
                        <form onsubmit="return checkPassword('${repeatPasswordError}')"
                              action="${pageContext.request.contextPath}/Controller"
                              method="post">
                            <input type="hidden" name="command" value="editUser"/>
                            <input type="hidden" name="userId" value="${requestScope.user.id}">
                            <div class="form-group">
                                <label for="login">${login}:</label>
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                                    <input name="editFormLogin" type="text" value="${requestScope.user.login}"
                                           minlength="5"
                                           maxlength="40" class="form-control" id="login"
                                           pattern="^[a-zA-Z][a-zA-Z0-9_]+$"
                                           title="${invalidLogin}" required
                                           oninvalid='this.setCustomValidity("${emptyField} ${invalidLogin}")'
                                           onchange="try{this.setCustomValidity('')}catch(e){}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="password">${password}:</label>
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                                    <input name="editFormPassword" type="password" minlength="6" maxlength="45"
                                           class="form-control" id="password"
                                           pattern="(?=^.{6,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$"
                                           title="${invalidPassword}" required
                                           oninvalid="this.setCustomValidity('${emptyField} ${invalidPassword}')"
                                           onchange="try{this.setCustomValidity('')}catch(e){}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="repeatPassword">${repeatPassword}:</label>
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                                    <input name="editFormRepeatPassword" type="password" minlength="6" maxlength="45"
                                           class="form-control" id="repeatPassword" required
                                           oninvalid="this.setCustomValidity('${emptyField}')"
                                           onchange="try{setCustomValidity('')}catch(e){}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="identification-number">${passportIdNumber}:</label>
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-search"></i></span>
                                    <input name="editFormIdentificationNumber" type="number"
                                           value="${requestScope.user.passport.identificationNumber}" min="1000000"
                                           max="9999999" class="form-control" id="identification-number"
                                           title="${invalidPassportNumber}" required
                                           oninvalid="this.setCustomValidity('${emptyField} ${invalidPassportNumber}')"
                                           onchange="try{setCustomValidity('')}catch(e){}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="series">${passportSeries}:</label>
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-paperclip"></i></span>
                                    <input name="editFormSeries" type="text"
                                           value="${requestScope.user.passport.series}"
                                           minlength="2" maxlength="2" class="form-control" id="series"
                                           pattern="^[A-Z]+$"
                                           title="${invalidSeries}" required
                                           oninvalid="this.setCustomValidity('${emptyField} ${invalidSeries}')"
                                           onchange="try{setCustomValidity('')}catch(e){}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="surname">${surname}:</label>
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                                    <input name="editFormSurname" type="text"
                                           value="${requestScope.user.passport.surname}"
                                           minlength="2" maxlength="40" class="form-control" id="surname"
                                           pattern="^[a-zA-Zа-яёА-ЯЁ\s\-]+$" title="${invalidName}" required
                                           oninvalid='this.setCustomValidity("${emptyField} ${invalidName}")'
                                           onchange="try{setCustomValidity('')}catch(e){}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="name">${name}:</label>
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                                    <input name="editFormName" type="text" value="${requestScope.user.passport.name}"
                                           minlength="2" maxlength="40" class="form-control" id="name"
                                           pattern="^[a-zA-Zа-яёА-ЯЁ\s\-]+$" title="${invalidName}" required
                                           oninvalid='this.setCustomValidity("${emptyField} ${invalidName}")'
                                           onchange="try{setCustomValidity('')}catch(e){}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="patronymic">${patronymic}:</label>
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                                    <input name="editFormPatronymic" type="text"
                                           value="${requestScope.user.passport.patronymic}" minlength="2" maxlength="40"
                                           class="form-control" id="patronymic" pattern="^[a-zA-Zа-яёА-ЯЁ\s\-]+$"
                                           title="${invalidName}" oninvalid='this.setCustomValidity("${invalidName}")'
                                           onchange="try{setCustomValidity('')}catch(e){}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="date">${birthdayDate}:</label>
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                                    <input name="editFormBirthdayDate" type="date"
                                           value="${requestScope.user.passport.birthday}"
                                           max="1998-01-01" min="1920-01-01" class="form-control" id="date" required
                                           oninvalid="this.setCustomValidity('${emptyField}')"
                                           onchange="try{setCustomValidity('')}catch(e){}">
                                </div>
                            </div>
                            <div id="err-password"></div>
                            <div class="form-group">
                                <button type="submit" class="btn btn-default">${saveButton}</button>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-default" type="button" data-dismiss="modal">${closeButton}</button>
                    </div>
                </div>
            </div>
        </div>
        <br><br>
        <c:if test="${param.serviceError}">
            <div class="alert alert-danger fade in">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    ${serverError}
            </div>
        </c:if>
        <c:if test="${requestScope.user == null}">
            <div class="alert alert-danger fade in">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    ${existUserError}
            </div>
        </c:if>
        <c:if test="${param.editSuccess}">
            <br>
            <div class="alert alert-success fade in">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    ${editSuccess}
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
        <hr>
        <h2>${rentalRequests}</h2>
        <table class="table table-striped">
            <tr>
                <th>${seatsNumber}</th>
                <th>${chekInDate}</th>
                <th>${daysNumber}</th>
                <th>${requestType}</th>
                <th>${payment}</th>
                <th>${requestStatus}</th>
            </tr>
            <c:forEach var="rentalRequest" items="${requestScope.rentalRequests}">
                <tr>
                    <td>${rentalRequest.seatsNumber}</td>
                    <td>${rentalRequest.checkInDate}</td>
                    <td>${rentalRequest.daysStayNumber}</td>
                    <td><c:if test="${rentalRequest.fullPayment == true}">${fullPayment}</c:if> <c:if
                            test="${rentalRequest.fullPayment == false}">${reservation}</c:if></td>
                    <td>${rentalRequest.payment}</td>
                    <td><span
                            class="glyphicon <c:if test="${rentalRequest.accepted == null}">glyphicon-refresh</c:if> <c:if test="${rentalRequest.accepted == true}">glyphicon-ok</c:if> <c:if test="${rentalRequest.accepted == false}">glyphicon-remove</c:if> "></span>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>

    <hr>

</div>

<c:import url="template/footer.jsp"/>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>
