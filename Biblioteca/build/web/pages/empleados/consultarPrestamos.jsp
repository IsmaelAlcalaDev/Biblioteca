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
        <link rel="stylesheet" href="css/style.css"/>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js" defer></script>
        <script src="https://cdn.jsdelivr.net/npm/chart.js" defer></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <title>Consultar prestamos</title>
    </head>
    <body class="d-flex flex-column min-vh-100">
        <%@ include file="../../components/navbarPrivada.jsp" %>
        <main class="flex-grow-1">
            <h3 class="mt-5 text-center">Consultar prestamos</h3>
            <div class="container mt-2 mb-5">
                <div class="row justify-content-center text-center">
                    <div class="col-md-11">
                        <c:if test="${not empty listaPrestamos}">
                            <div class="d-flex justify-content-between my-3">
                                <form action="ConsultarPrestamos" method="post" class="d-flex gap-3">
                                    <div class="form-group mb-0 d-flex align-items-center">
                                        <div class="text-start">
                                            <label for="fechaInicio" class="w-75">Fecha de inicio:</label>
                                            <input type="date" class="form-control" name="fechaInicio">
                                        </div>
                                        <div class="text-start ms-3">
                                            <label for="fechaFin">Fecha de fin:</label>
                                            <input type="date" class="form-control" name="fechaFin">
                                        </div>
                                    </div>
                                    <div class="mt-3 d-flex align-items-end">
                                        <button type="submit" class="btn btn-primary">Consultar</button>
                                    </div>
                                </form>
                                <div class="d-flex align-items-end">
                                    <a href="ConsultarPrestamos"><button  class="btn btn-primary">Mostrar todos</button></a>    
                                </div>
                            </div>
                            <table class="table table-striped align-middle">
                                <thead>
                                    <tr>
                                        <th class="px-3">ID Prestamo</th>
                                        <th class="px-3">Titulo</th>
                                        <th class="px-3">ISBN</th>
                                        <th class="px-3">Fecha de expedición</th>
                                        <th class="px-3">Fecha de devolución</th>
                                        <th class="px-3">Nombre del socio</th>
                                        <th class="px-3">ID Socio</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="prestamo" items="${listaPrestamos}">
                                        <tr>
                                            <td>${prestamo.prestamoID}</td>
                                            <td>${prestamo.getLibroISBN().titulo}</td>
                                            <td>${prestamo.getLibroISBN().isbn}</td>
                                            <td><fmt:formatDate value="${prestamo.fechaPrestamo}" pattern="dd-MM-yyyy" /></td>
                                            <td><fmt:formatDate value="${prestamo.fechaDevolucion}" pattern="dd-MM-yyyy" /></td>
                                            <td>${prestamo.getUsuarioID().nombre} ${prestamo.getUsuarioID().apellidos}</td>
                                            <td>${prestamo.getUsuarioID().usuarioID}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:if>
                        <c:if test="${empty listaPrestamos}">
                            <p>No existe ningun prestamo actualmente.</p>
                        </c:if>
                    </div>
                </div>
            </div>   
            <c:if test="${not empty nombresLibros and not empty vecesPrestado}">
                <div class="d-flex justify-content-center">
                    <div class="w-50 mb-5">
                        <canvas id="graficoPrestamos"></canvas>    
                    </div>
                </div>
            </c:if>
            <script>
                document.addEventListener("DOMContentLoaded", function () {
                    let nombresLibros = ${nombresLibros};
                    let vecesPrestado = ${vecesPrestado};

                    let ctx = document.getElementById('graficoPrestamos').getContext('2d');
                    let myChart = new Chart(ctx, {
                        type: 'bar',
                        data: {
                            labels: nombresLibros,
                            datasets: [{
                                    label: 'Número de préstamos por libro',
                                    data: vecesPrestado,
                                    backgroundColor: 'rgba(54, 162, 235, 0.2)',
                                    borderColor: 'rgba(54, 162, 235, 1)',
                                    borderWidth: 1
                                }]
                        },
                        options: {
                            scales: {
                                y: {
                                    beginAtZero: true
                                }
                            }
                        }
                    });
                });
            </script>
        </main>
        <%@ include file="../../components/footer.jsp" %>
    </body>
</html>