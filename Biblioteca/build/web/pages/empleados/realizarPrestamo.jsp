<%@ page import="javax.persistence.Persistence" %>
<%@ page language="java" contentType="text/html; charset=latin1" pageEncoding="latin1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/style.css"/>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js" defer></script>
        <link rel="stylesheet" href="css/style.css"/>
        <title>Realizar prestamo</title>
    </head>
    <body class="d-flex flex-column min-vh-100">
        <%@ include file="../../components/navbarPrivada.jsp" %>
        <main class="flex-grow-1">
            <div class="container mb-5">
                <div class="row justify-content-center">
                    <div class="col-md-6">
                        <div class="card mt-5">
                            <div class="card-body">
                                <c:if test="${!prestamoRealizado}">
                                    <h5 class="card-title text-center">Realizar préstamo</h5>
                                    <c:if test="${not empty errores}">
                                        <div class="alert alert-danger" role="alert">
                                            <ul class="m-0">
                                                <c:forEach items="${errores}" var="error">
                                                    <li>${error}</li>
                                                    </c:forEach>
                                            </ul>
                                        </div>
                                    </c:if>
                                    <%-- Imprime el mensaje de confirmación si existe --%>
                                    <c:if test="${not empty mensaje}">
                                        <div class="alert alert-success" role="alert">${mensaje}</div>
                                    </c:if>
                                    <form method="POST">
                                        <div class="mb-3">
                                            <label for="libro" class="form-label">Selecciona un libro:</label>
                                            <select class="form-select" id="libro" name="libro" required>
                                                <option value="">Elige un libro</option>
                                                <c:forEach var="libro" items="${listaLibros}">
                                                    <option value="${libro.isbn}">${libro.titulo} - ${libro.isbn}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="mb-3">
                                            <label for="socio" class="form-label">Selecciona un socio:</label>
                                            <select class="form-select" id="socio" name="socio" required>
                                                <option value="">Elige un socio</option>
                                                <c:forEach var="socio" items="${listaSocios}">
                                                    <option value="${socio.usuarioID}">${socio.usuarioID} - ${socio.nombre} ${socio.apellidos} - ${socio.correoElectronico}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="d-flex justify-content-between">
                                            <button type="submit" class="btn btn-primary">Realizar préstamo</button>
                                            <a href="PanelEmpleado" class="btn btn-danger">Volver atrás</a>
                                        </div>
                                    </form>
                                </c:if>
                                <c:if test="${prestamoRealizado}">
                                    <h3 class="card-title text-center">Prestamo realizado</h3>
                                    <div class="card mt-3">
                                        <div class="card-body">
                                            <div class="row gap-2">
                                                <div class="col-md-5 my-5">
                                                    <img src="./assets/${libro.imagenPortada}" class="card-img-top img-thumbnail" alt="Portada del libro">
                                                    <div class="text-start small mt-2">
                                                        <span><strong>Nº Ejemplares:</strong> ${libro.numEjemplares}</span>
                                                    </div>
                                                </div>
                                                <div class="col-md-6 my-5">
                                                    <div class="card-text">
                                                        <p><strong>ISBN:</strong> ${libro.isbn}</p>
                                                        <p><strong>Título:</strong> ${libro.titulo}</p>
                                                        <p><strong>Autor:</strong> ${libro.autor}</p>
                                                        <p><strong>Género:</strong> ${libro.genero}</p>
                                                        <p><strong>Fecha de edición:</strong> <fmt:formatDate value="${libro.fechaEdicion}" pattern="dd-MM-yyyy" /></p>
                                                        <p><strong>Fecha del préstamo:</strong> <fmt:formatDate value="${prestamo.fechaPrestamo}" pattern="dd-MM-yyyy" /></p>
                                                        <p><strong>Fecha de devolución:</strong> <fmt:formatDate value="${fechaDevolucion}" pattern="dd-MM-yyyy" /></p>
                                                    </div>
                                                    <hr>
                                                    <div class="card-text">
                                                        <p><strong>Nombre:</strong> ${socio.nombre}</p>
                                                        <p><strong>Apellidos:</strong> ${socio.apellidos}</p>
                                                        <p><strong>Email:</strong> ${socio.correoElectronico}</p>
                                                        <p><strong>Domicilio:</strong> ${socio.domicilio}</p>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="mt-3 text-center">
                                                <a href="PanelEmpleado" class="btn btn-danger">Finalizar</a>
                                            </div>
                                        </div>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <footer class="bg-dark">
            <div class="container">
                <p class="mb-0 text-white text-center p-2">&copy; 2024 Biblioteca</p>
            </div>
        </footer>
    </body>
</html>