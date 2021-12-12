<%--
  Created by IntelliJ IDEA.
  User: Averjan(Тимофей)
  Date: 06.12.2021
  Time: 01:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Log Up | Hotel</title>
    <jsp:include page="common/header.jsp"/>
</head>
<body class="d-flex flex-column h-100">
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="localization" var="loc"/>

<div class="container">
    <div class="row justify-content-center">
        <div class="col"></div>
        <div class="col-6 my-sm-3">
            <c:if test="${sessionScope.user != null}">
                <div class="alert alert-danger fade show" role="alert">
                    <fmt:message bundle="${loc}" key="language.alreadyLoggedIn"/>
                </div>
            </c:if>
            <c:if test="${sessionScope.user == null}">
            <h1 class="mt-5 fw-bold"><fmt:message bundle="${loc}" key="language.logUp"/></h1>

            <c:if test="${message == 'error'}">
                <div class="alert alert-danger fade show " role="alert">
                    <fmt:message bundle="${loc}" key="language.registrationFailed"/>
                </div>
            </c:if>
            <c:if test="${message == 'ok'}">
                <div class="alert alert-success fade show " role="alert">
                    <fmt:message bundle="${loc}" key="language.successfullyRegistration"/>
                </div>
            </c:if>


            <form action="${pageContext.request.contextPath}/booking?command=signup" method="post">

                <div class="mb-3">
                    <label for="email" class="form-label"><fmt:message bundle="${loc}"
                                                                       key="language.emailAddress"/></label>
                    <input type="email" class="form-control" name="email" id="email"
                           placeholder="name@example.com" pattern="^([a-z0-9_-]+\.)*[a-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$" required>
                </div>
                <div class="mb-3">
                    <label for="password-first" class="form-label"><fmt:message bundle="${loc}"
                                                                                key="language.password"/></label>
                    <input type="password" class="form-control" name="password-first" id="password-first"
                           required
                           minlength="8">
                </div>
                <div class="mb-3">
                    <label for="password-second" class="form-label"><fmt:message bundle="${loc}"
                                                                                 key="language.confirmPassword"/></label>
                    <input type="password" class="form-control" name="password-second" id="password-second"
                           required
                           minlength="8">
                </div>

                <button class="btn btn-primary" type="submit"><fmt:message bundle="${loc}" key="language.logUp"/></button>

            </form>
            <hr class="dropdown-divider">
            <p class="text-muted"><fmt:message bundle="${loc}" key="language.haveAccount"/><a
                    href="${pageContext.request.contextPath}/booking?command=openSignin"> <fmt:message
                    bundle="${loc}" key="language.logIn"/></a></p>
            <a href="/booking?command=home"><fmt:message bundle="${loc}" key="language.home"/></a>
        </div>
        </c:if>
        <div class="col-sm"></div>
    </div>
</div>


</body>
</html>

