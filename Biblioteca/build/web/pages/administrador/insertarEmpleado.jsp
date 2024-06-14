<%@ page import="javax.persistence.Persistence" %>
<%@ page language="java" contentType="text/html; charset=latin1" pageEncoding="latin1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js" defer></script>
    <link rel="stylesheet" href="css/style.css"/>
    <title>Insertar empleado</title>
</head>
<body class="d-flex flex-column min-vh-100">
    <%@ include file="../../components/navbarPrivada.jsp" %>
    <main class="flex-grow-1 d-flex flex-column justify-content-center align-items-center">
        <div class="container mb-5">
            <div class="row justify-content-center">
                <div class="col-md-6">
                    <div class="card mt-5">
                        <div class="card-body">
                            <h5 class="card-title text-center">Registro de Empleado</h5>
                            <c:if test="${not empty errores}">
                                <div class="alert alert-danger" role="alert">
                                    <ul class="m-0">
                                        <c:forEach items="${errores}" var="error">
                                            <li>${error}</li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </c:if>
                            <%-- Imprime el mensaje de confirmacion si existe --%>
                            <c:if test="${not empty mensaje}">
                                <div id="success-message" class="alert alert-success" role="alert">
                                    ${mensaje}
                                </div>
                            </c:if>
                            <form method="POST">
                                <div class="mb-3">
                                    <label for="nombre" class="form-label">Nombre</label>
                                    <input type="text" class="form-control" id="nombre" name="nombre" placeholder="Ingrese su nombre" required>
                                </div>
                                <div class="mb-3">
                                    <label for="apellidos" class="form-label">Apellidos</label>
                                    <input type="text" class="form-control" id="apellidos" name="apellidos" placeholder="Ingrese sus apellidos" required>
                                </div>
                                <div class="mb-3">
                                    <label for="email" class="form-label">Correo Electrónico</label>
                                    <input type="email" class="form-control" id="email" name="email" placeholder="Ingrese su correo electrónico" required>
                                </div>
                                <div class="mb-3">
                                    <label for="contrasena" class="form-label">Contraseña</label>
                                    <input type="password" class="form-control" id="contrasena" name="contrasena" placeholder="Ingrese su contraseña" required>
                                </div>
                                <div class="mb-3">
                                    <label for="repetirContrasena" class="form-label">Repita Contraseña</label>
                                    <input type="password" class="form-control" id="repetirContrasena" name="repetirContrasena" placeholder="Repita su contraseña" required>
                                </div>
                                <div class="mb-3">
                                    <label for="domicilio" class="form-label">Domicilio</label>
                                    <input type="text" class="form-control" id="domicilio" name="domicilio" placeholder="Ingrese su domicilio" required>
                                </div>
                                <div class="mb-3">
                                    <label for="tipoUsuario" class="form-label">Tipo de Usuario</label>
                                    <select class="form-select" id="tipoUsuario" name="tipoUsuario" required>
                                        <option value="">Seleccione el tipo de usuario</option>
                                        <option value="EMPLEADO">Empleado</option>
                                        <option value="ADMIN">Administrador</option>
                                    </select>
                                </div>
                                <div class="d-flex justify-content-between">
                                    <button type="submit" class="btn btn-primary">Registrarse</button>
                                    <a href="GestionEmpleados" class="btn btn-danger">Volver atrás</a>
                                </div>
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