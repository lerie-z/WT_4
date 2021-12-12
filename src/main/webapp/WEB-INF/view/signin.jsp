<%--
  Created by IntelliJ IDEA.
  User: Averjan(Тимофей)
  Date: 06.12.2021
  Time: 01:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Log In | Hotel</title>
    <jsp:include page="common/header.jsp"/>
</head>
<body>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="localization" var="loc"/>
<fmt:setBundle basename="information" var="info"/>

<div class="container">
    <div class="row justify-content-center">
        <div class="col"></div>
        <div class="col-6 my-sm-3">
            <c:if test="${sessionScope.user != null}">
                <div class="alert alert-danger fade show " role="alert">
                    <fmt:message bundle="${loc}" key="language.alreadyLoggedIn"/>
                </div>
            </c:if>
            <c:if test="${sessionScope.user == null}">

                <h1 class="mt-5 fw-bold"><fmt:message bundle="${loc}" key="language.signIn"/></h1>

                <c:if test="${errorMessage=='true'}">
                    <div class="alert alert-danger fade show" role="alert">
                        <fmt:message bundle="${loc}" key="language.loginError"/>
                    </div>
                </c:if>

                <form action="${pageContext.request.contextPath}/booking?command=signin" method="post">

                    <div class="mb-3">
                        <label for="exampleInputEmail1" class="form-label"><fmt:message bundle="${loc}" key="language.emailAddress"/></label>
                        <div class="input-group w-100">
                            <span class="input-group-text" id="basic-addon1">
                                <p style="margin: 0;">E-mail</p>
                            </span>
                            <input name="email" type="email" class="form-control" id="exampleInputEmail1"
                                   aria-describedby="emailHelp" pattern="^([a-z0-9_-]+\.)*[a-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$" placeholder="name@example.com">
                        </div>
                    </div>
                    <div class="mb-3">
                        <label for="passwordInput" class="form-label"><fmt:message bundle="${loc}" key="language.password"/></label>
                        <div class="input-group w-100">
                            <span class="input-group-text">
                                <p style="margin: 0;">Password</p>
                            </span>
                            <input name="password" type="password" class="form-control" id="passwordInput" minlength="8"
                                   placeholder="password">
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary"><fmt:message bundle="${loc}" key="language.signIn"/></button>
                </form>
                <hr class="dropdown-divider">
                <p class="text-muted"><fmt:message bundle="${loc}" key="language.noAccount"/> <a
                        href="/booking?command=openSignup"><fmt:message bundle="${loc}" key="language.logUp"/></a></p>
                <a href="/booking?command=home"><fmt:message bundle="${loc}" key="language.home"/></a>
            </c:if>
        </div>
        <div class="col"></div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
</body>
</html>
