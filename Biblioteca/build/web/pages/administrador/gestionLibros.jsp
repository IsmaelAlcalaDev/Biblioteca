<%@ page import="javax.persistence.Persistence" %>
<%@ page language="java" contentType="text/html; charset=latin1" pageEncoding="latin1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js" defer></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <link rel="stylesheet" href="css/style.css"/>
    <title>Gestión libros</title>
</head>
<body class="d-flex flex-column min-vh-100">
    <%@ include file="../../components/navbarPrivada.jsp" %>
    <main class="flex-grow-1">
        <h3 class="mt-5 text-center">Gestion de Libros</h3>
            <div class="container my-5">
                <div class="row justify-content-center text-center">
                <c:if test="${not empty listaLibros}">
                    <table class="table table-striped align-middle">
                        <thead>
                            <tr>
                                <th class="px-3">Portada</th>
                                <th class="px-3">Titulo</th>
                                <th class="px-3">Autor</th>
                                <th class="px-3">Genero</th>
                                <th class="px-3">Fecha de edición</th>
                                <th class="px-3">ISBN</th>
                                <th class="px-3">Nº Ejemplares</th>
                                <th class="px-5">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="libro" items="${listaLibros}" varStatus="status">
                                <tr>
                                    <td><img src="assets/${libro.imagenPortada}" alt="Portada de ${libro.titulo}" style="width: 100px; height: auto;"></td>
                                    <td>${libro.titulo}</td>
                                    <td>${libro.autor}</td>
                                    <td style="max-width: 150px; word-wrap: break-word;">
                                        <ul class="list-group">
                                            <c:forEach var="genero" items="${fn:split(libro.genero, ',')}">
                                                <li class="list-group-item">${genero}</li>
                                            </c:forEach>
                                        </ul>
                                    </td>
                                    <td><fmt:formatDate value="${libro.fechaEdicion}" pattern="dd-MM-yyyy" /></td>
                                    <td>${libro.isbn}</td>
                                    <td>${libro.numEjemplares}</td>
                                    <td>
                                        <a href="EliminarLibro?id=${libro.isbn}" class="btn btn-danger eliminar-libro">Eliminar</a>
                                        <a href="EditarLibro?id=${libro.isbn}" class="btn btn-primary">Editar</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>
                <c:if test="${empty listaLibros}">
                    <p>No hay libros en la biblioteca</p>
                </c:if>
                <div class="text-start">
                    <a href="InsertarLibro" class="btn btn-primary">Añadir libro</a>
                </div>  
            </div>
        </div>
    </main>
    <%@ include file="../../components/footer.jsp" %>
    <script>
        document.addEventListener("DOMContentLoaded", function() {
            const eliminarEmpleados = document.querySelectorAll('.eliminar-libro');
            eliminarEmpleados.forEach(empleado => {
                empleado.addEventListener('click', function (e) {
                    e.preventDefault();
                    let titulo = e.target.parentElement.parentElement.children[0].textContent;
                    Swal.fire({
                        title: "¿Eliminar el libro " + titulo + "?",
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
    <c:if test="${not empty error}">
        <script>
            // Muestra el Sweet Alert
            document.addEventListener("DOMContentLoaded", function() {
                Swal.fire({
                    title: "Error",
                    text: "${error}",
                    icon: "error",
                    timer: 2500, // Duración en milisegundos (2 segundos)
                    showConfirmButton: false // Esto oculta el botón de confirmación
                });
            });
        </script>
    </c:if>
</body>
</html>