<%--
  Created by IntelliJ IDEA.
  User: Averjan(Тимофей)
  Date: 06.12.2021
  Time: 01:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<jsp:include page="common/header.jsp"/>
    <head>
        <title>Catalog | Hotel</title>
    </head>

    <body>
        <fmt:setLocale value="${sessionScope.language}"/>
        <fmt:setBundle basename="localization" var="loc"/>

        <div class="container d-flex justify-content-around flex-wrap">

            <c:forEach var="apartment" items="${apartments}">
                <div class="my-lg-2">
                    <div class="card text-center">
                        <div class="card-header bg-success text-white">
                            <h5><c:out value="${apartment.status}"/>
                                <c:if test="${sessionScope.role.name == 'admin'}">
                                    <a href="${pageContext.request.contextPath}/booking?command=changeApartmentStatus&apartmentId=${apartment.id}">
                                        <fmt:message bundle="${loc}" key="language.edit"/>
                                    </a>
                                </c:if>
                            </h5>
                        </div>
                        <div class="card-body">
                            <div class="row">
                                <div class="col-md-8">
                                    <p><fmt:message bundle="${loc}" key="language.description"/><c:out value=":"/></p>
                                    <p><fmt:message bundle="${loc}" key="language.apartmentNumber"/><c:out value=": "/><c:out value="${apartment.number}"/></p>
                                    <p><fmt:message bundle="${loc}" key="language.numberOfRooms"/><c:out value=": "/><c:out value="${apartment.roomsNumber}"/></p>
                                    <p><fmt:message bundle="${loc}" key="language.price"/><c:out value=": "/><c:out value="${apartment.price} "/><fmt:message bundle="${loc}" key="language.cur"/></p>
                                    <div class="container">
                                        <div class="row">

                                            <div class="col-sm text-right">
                                                <c:set scope="request" var="priceFlag" value="0"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <c:if test="${sessionScope.user!=null }">
                                        <div class="row justify-content-center">
                                            <p><input type="button"  class="btn btn-primary" value="<fmt:message bundle="${loc}" key="language.arrange"/>"
                                                      onClick='location.href="${pageContext.request.contextPath}/booking?command=addOrder&apartment_id=${apartment.id}"'></p>

                                        </div>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </body>
    <jsp:include page="common/footer.jsp"/>
</html>
