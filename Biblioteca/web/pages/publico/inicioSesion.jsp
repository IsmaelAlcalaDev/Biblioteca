<%@ page import="javax.persistence.Persistence" %>
<%@ page language="java" contentType="text/html; charset=latin1" pageEncoding="latin1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en_US"/>
<fmt:bundle basename="bundle.message">
<!DOCTYPE html>
<html lang="<fmt:message key="lang"/>">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js" defer></script>
    <link rel="stylesheet" href="css/style.css"/>
    <title><fmt:message key="form.title.iniciarSesion"/></title>
</head>
<body class="d-flex flex-column min-vh-100">
    <%@ include file="../../components/navbarPublica.jsp" %>
    <main class="flex-grow-1">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-6">
                    <div class="card mt-5">
                        <div class="card-body">
                            <h5 class="card-title text-center"><fmt:message key="form.title.iniciarSesion"/></h5>
                            <c:if test="${not empty errores}">
                                <div class="alert alert-danger" role="alert">
                                    <ul class="m-0">
                                        <c:forEach items="${errores}" var="error">
                                            <li>${error}</li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </c:if>
                            <form method="POST">
                                <div class="mb-3">
                                    <label for="email" class="form-label"><fmt:message key="form.email"/></label>
                                    <input type="text" class="form-control" id="email" name="email" placeholder="<fmt:message key="form.placeholder.email"/>" value="${email}" required>
                                </div>
                                <div class="mb-3">
                                    <label for="contrasena" class="form-label"><fmt:message key="form.contrasena"/></label>
                                    <input type="password" class="form-control" id="contrasena" name="contrasena" placeholder="<fmt:message key="form.placeholder.contrasena"/>" required>
                                </div>
                                <button type="submit" class="btn btn-primary btn-block"><fmt:message key="button.iniciarSesion"/></button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
    <%@ include file="../../components/footer.jsp" %>
</body>
</html>
</fmt:bundle>