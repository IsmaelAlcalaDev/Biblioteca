<%@ page import="javax.persistence.Persistence" %>
<%@ page language="java" contentType="text/html; charset=latin1" pageEncoding="latin1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js" defer></script>
    <title>Panel usuario</title>
</head>
<body class="d-flex flex-column min-vh-100">
    <%@ include file="../../components/navbarPrivada.jsp" %>
    <main class="flex-grow-1">
        <h3 class="mt-5 text-center">Panel de usuario</h3>
        <div class="container my-5">
            <div class="row justify-content-center text-center">
                <div class="col-md-10">
                    <c:if test="${not empty prestamos}">
                        <table class="table table-striped align-middle">
                            <thead>
                                <tr>
                                    <th>ID Prestamo</th>
                                    <th>Fecha Préstamo</th>
                                    <th>Fecha Devolución</th>
                                    <th>Dias transcurridos</th>
                                    <th>ISBN del Libro</th>
                                    <th>Acciones</th> 
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="prestamo" items="${prestamos}">
                                    <tr>
                                        <td>${prestamo.prestamoID}</td>
                                        <td><fmt:formatDate value="${prestamo.fechaPrestamo}" pattern="dd-MM-yyyy" /></td>
                                        <td><fmt:formatDate value="${prestamo.fechaDevolucion}" pattern="dd-MM-yyyy" /></td>
                                        <td style="color: ${prestamo.calcularDiasTranscurridos(prestamo.fechaPrestamo, prestamo.fechaDevolucion) > 30 ? '#D10036' : 'black'};">
                                            <c:if test="${prestamo.calcularDiasTranscurridos(prestamo.fechaPrestamo, prestamo.fechaDevolucion) > 30}">
                                                <strong>Penalizado</strong>
                                            </c:if>
                                            <c:if test="${prestamo.calcularDiasTranscurridos(prestamo.fechaPrestamo, prestamo.fechaDevolucion) <= 30}">
                                                ${prestamo.calcularDiasTranscurridos(prestamo.fechaPrestamo, prestamo.fechaDevolucion)}
                                            </c:if>
                                        </td>
                                        <td>${prestamo.libroISBN.isbn}</td>
                                        <td>
                                            <c:if test="${empty prestamo.fechaDevolucion}">
                                                <a href="PanelUsuario?id=${prestamo.prestamoID}&amp;isbn=${prestamo.getLibroISBN().getIsbn()}" class="btn btn-primary">Devolver</a>
                                            </c:if>
                                            <c:if test="${not empty prestamo.fechaDevolucion}">Entregado</c:if>
                                       </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:if>
                    <c:if test="${empty prestamos}">
                        <p>No se encontraron préstamos.</p>
                    </c:if>
                </div>
            </div>  
        </div>
    </main>
    <%@ include file="../../components/footer.jsp" %>
</body>
</html>