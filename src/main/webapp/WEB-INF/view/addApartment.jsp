<%--
  Created by IntelliJ IDEA.
  User: Averjan(Тимофей)
  Date: 11.12.2021
  Time: 22:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<jsp:include page="common/header.jsp"/>
<head>
    <title>Add product | Hotel</title>
</head>
<body class="d-flex flex-column h-100">
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="localization" var="loc"/>

<div class="container">
    <div class="row justify-content-center">
        <div class="col-6 my-sm-3">
            <c:if test="${sessionScope.role.name != 'admin'}">
                <div class="alert alert-danger fade show " role="alert">
                    <fmt:message bundle="${loc}" key="language.noRights"/>
                </div>
            </c:if>
            <c:if test="${sessionScope.role.name == 'admin'}">

                <c:if test="${param.message == 'error'}">
                    <div class="alert alert-danger fade show " role="alert">
                        <fmt:message bundle="${loc}" key="language.apartmentAddingError"/>
                    </div>
                </c:if>
                <c:if test="${param.message == 'ok'}">
                    <div class="alert alert-success fade show " role="alert">
                        <fmt:message bundle="${loc}" key="language.apartmentAddedSuccessfully"/>
                    </div>
                </c:if>


                <form action="${pageContext.request.contextPath}/booking?command=confirmAddApartment"
                      method="post">
                    <h4><fmt:message bundle="${loc}" key="language.enterApartmentDescription"/></h4>
                    <div class="row mb-3">
                        <div class="col-sm">
                            <label for="price" class="form-label"><fmt:message bundle="${loc}" key="language.price"/></label>
                            <input type="text" id="price" name="price" class="form-control"
                                   placeholder="13.82" pattern="^(([1-9]\d*\.\d+)|(0[1-9]*\.\d+)|(\d\.\d+)|(0)|([1-9]\d*))$" required>
                        </div>
                    </div>
                    <div class="mb-3">
                        <label for="status" class="form-label"><fmt:message bundle="${loc}" key="language.status"/></label>

                        <select name="status" id="status" required>
                            <option value="booked" selected="selected"><fmt:message bundle="${loc}" key="language.booked"/></option>
                            <option value="available"><fmt:message bundle="${loc}" key="language.available"/></option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="roomsNumber" class="form-label"><fmt:message bundle="${loc}" key="language.numberOfRooms"/></label>
                        <input type="text" class="form-control" name="roomsNumber" id="roomsNumber"
                               placeholder="2" pattern="^(([1-9][0-9]*)|([1-9]))$" maxlength="15"  required>
                    </div>
                    <div class="mb-3">
                        <label for="apartmentNumber" class="form-label"><fmt:message bundle="${loc}" key="language.apartmentNumber"/></label>
                        <input type="text" class="form-control" name="apartmentNumber" id="apartmentNumber"
                               placeholder="73"pattern="^(([1-9][0-9]*)|([1-9]))$" maxlength="15" required>
                    </div>

                    <button class="btn btn-primary" type="submit"><fmt:message bundle="${loc}" key="language.add"/></button>
                </form>
                <hr class="dropdown-divider">
                <a href="/booking?command=home"><fmt:message bundle="${loc}" key="language.home"/></a>
            </c:if>
        </div>
    </div>
</div>
</body>
<jsp:include page="common/footer.jsp"/>
</html>
