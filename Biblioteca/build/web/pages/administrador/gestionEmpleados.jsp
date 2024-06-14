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
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <link rel="stylesheet" href="css/style.css"/>
    <title>Gestión empleados</title>
</head>
<body class="d-flex flex-column min-vh-100">
    <%@ include file="../../components/navbarPrivada.jsp" %>
    <main class="flex-grow-1">
        <h3 class="mt-5 text-center">Gestion de empleados</h3>
        <div class="container my-5">
            <div class="row justify-content-center text-center">
                <div class="col-md-10">
                    <c:if test="${not empty empleados}">
                        <table class="table table-striped align-middle">
                            <thead>
                                <tr>
                                    <th class="px-3">Nombre</th>
                                    <th class="px-3">Apellidos</th>
                                    <th class="px-3">Correo Electrónico</th>
                                    <th class="px-3">Tipo de usuario</th>
                                    <th class="px-3">Domicilio</th>
                                    <th class="px-3">Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="empleado" items="${empleados}">
                                    <tr>
                                        <td>${empleado.nombre}</td>
                                        <td>${empleado.apellidos}</td>
                                        <td>${empleado.correoElectronico}</td>
                                        <td>${empleado.tipoUsuario}</td>
                                        <td>${empleado.domicilio}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${empleado.tipoUsuario ne 'ADMIN'}">
                                                    <a href="EliminarEmpleado?id=${empleado.usuarioID}" class="btn btn-danger eliminar-empleado">Eliminar</a>
                                                </c:when>
                                                <c:otherwise>
                                                    <button class="btn btn-danger" disabled>Eliminar</button>
                                                </c:otherwise>
                                            </c:choose>
                                            <a href="EditarEmpleado?id=${empleado.usuarioID}" class="btn btn-primary">Editar</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:if>
                    <c:if test="${empty empleados}">
                        <p>No se encontraron empleados.</p>
                    </c:if>
                    <div class="text-start">
                        <a href="InsertarEmpleado" class="btn btn-primary">Añadir nuevo empleado</a>
                    </div>
                </div>
            </div>
        </div>
    </main>
    <%@ include file="../../components/footer.jsp" %>
    <script>
        document.addEventListener("DOMContentLoaded", function() {
            const eliminarEmpleados = document.querySelectorAll('.eliminar-empleado');
            eliminarEmpleados.forEach(empleado => {
                empleado.addEventListener('click', function (e) {
                    e.preventDefault();
                    let nombre = e.target.parentElement.parentElement.children[0].textContent;
                    Swal.fire({
                        title: "¿Eliminar al empleado " + nombre + "?",
                        text: "Esta acción no se puede deshacer",
                        icon: 'warning',
                        showCancelButton: true,
                        confirmButtonColor: '#3085d6',
                        cancelButtonColor: '#d33',
                        confirmButtonText: 'Sí, eliminar'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            window.location.href = e.target.href;
                        }
                    });
                });
            });
        });
    </script>
</body>
</html>