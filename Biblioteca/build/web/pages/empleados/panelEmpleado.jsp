<%@ page import="javax.persistence.Persistence" %>
<%@ page language="java" contentType="text/html; charset=latin1" pageEncoding="latin1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="css/style.css"/>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js" defer></script>
    <title>Panel empleado</title>
</head>
<body class="d-flex flex-column min-vh-100">
<%@ include file="../../components/navbarPrivada.jsp" %>
<main class="flex-grow-1 d-flex justify-content-center align-items-center">
    <div class="container text-center">
        <h3>Panel de Empleados</h3>
        <div class="container">
            <div class="row mt-3 justify-content-center">
                <div class="col-md-8">
                    <div class="text-end mb-3">
                        <c:if test="${not empty listaSocios}">
                            <form method="post">
                                <button type="submit" class="btn btn-danger">
                                    <i class="fas fa-bell"></i> ${listaSocios.size()} Alertas
                                </button>
                            </form>
                        </c:if>
                        <c:if test="${empty listaSocios}">
                            <button type="submit" class="btn btn-success">
                                <i class="fas fa-bell"></i> Sin alertas
                            </button>
                        </c:if>
                    </div>
                </div>
            </div>
            <div class="row mt-3 justify-content-center">
                <div class="col-md-4">
                    <div class="card">
                        <div class="card-body py-5 text-center"> 
                            <h5 class="card-title">Consultar Préstamos</h5>
                            <p class="card-text">Aquí puedes consultar los préstamos activos.</p>
                            <a href="ConsultarPrestamos" class="btn btn-primary btn-card">Gestionar</a> 
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card">
                        <div class="card-body py-5 text-center">
                            <h5 class="card-title">Realizar Préstamo</h5>
                            <p class="card-text">Aquí puedes realizar nuevos préstamos de libros.</p>
                            <a href="RealizarPrestamo" class="btn btn-primary btn-card">Gestionar</a> 
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