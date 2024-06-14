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
    <title>Panel administrador</title>
</head>
<body class="d-flex flex-column min-vh-100">
    <%@ include file="../../components/navbarPrivada.jsp" %>
    <main class="flex-grow-1 d-flex flex-column justify-content-center align-items-center">
        <div class="container text-center">
            <h3>Panel de Administración</h3>
            <div class="row mt-5">
                <div class="col-md-4">
                    <div class="card">
                        <div class="card-body py-5">
                            <h5 class="card-title">Gestionar Empleados</h5>
                            <p class="card-text">Aquí puedes gestionar el personal de la biblioteca.</p>
                            <a href="GestionEmpleados" class="btn btn-primary btn-card">Gestionar</a>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card">
                        <div class="card-body py-5">
                            <h5 class="card-title">Gestionar Préstamos</h5>
                            <p class="card-text">Aquí puedes administrar los préstamos de libros.</p>
                            <a href="GestionPrestamos" class="btn btn-primary btn-card">Gestionar</a>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card">
                        <div class="card-body py-5">
                            <h5 class="card-title">Gestionar Libros</h5>
                            <p class="card-text">Aquí puedes administrar el catálogo de libros.</p>
                            <a href="GestionLibros" class="btn btn-primary btn-card">Gestionar</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
    <%@ include file="../../components/footer.jsp" %>
</body>
</html>