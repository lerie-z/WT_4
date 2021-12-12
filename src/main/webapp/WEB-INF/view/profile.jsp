<%--
  Created by IntelliJ IDEA.
  User: Averjan(Тимофей)
  Date: 06.12.2021
  Time: 01:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<jsp:include page="common/header.jsp"/>
<head>
    <title>Profile | Online-shop</title>
</head>
<body>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="localization" var="loc"/>

<div class="container">
    <c:if test="${sessionScope.user == null}">
        <div class="row justify-content-center">
            <div class="alert alert-danger fade show " role="alert">
                <fmt:message bundle="${loc}" key="language.noRights"/>
            </div>
        </div>
    </c:if>
    <c:if test="${sessionScope.user != null}">
        <div class="p-3 p-md-4 border rounded-3 my-sm-3">
            <div class="col"></div>
            <div>
                <h2 class="text-center"><fmt:message bundle="${loc}" key="language.profile"/></h2>
            </div>
            <div class="d-md-flex justify-content-around">
                <div><h4 class="text-primary"><fmt:message bundle="${loc}" key="language.emailAddress"/>:</h4></div>
                <div><h4>${sessionScope.user.email}</h4></div>
            </div>

        </div>
    </c:if>
</div>
</body>
<jsp:include page="common/footer.jsp"/>
</html>
