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
        <title>Editar libro</title>
    </head>
    <body class="d-flex flex-column min-vh-100">
        <%@ include file="../../components/navbarPrivada.jsp" %>
        <main class="flex-grow-1">
            <div class="container mb-5">
                <div class="row justify-content-center">
                    <div class="col-md-6">
                        <div class="card mt-5">
                            <div class="card-body">
                                <h5 class="card-title text-center">Editar libro</h5>
                                <%-- Imprime el mensaje de error si existe --%>
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
                                    <div class="alert alert-success" role="alert">
                                        ${mensaje}
                                    </div>
                                </c:if>
                                <form method="POST" enctype="multipart/form-data">
                                    <div class="mb-3">
                                        <label for="isbn" class="form-label">ISBN:</label>
                                        <input type="text" value="${isbn}" class="form-control" id="isbn" name="isbn" required>
                                    </div>
                                    <div class="mb-3">
                                        <label for="titulo" class="form-label">Título:</label>
                                        <input type="text" value="${libro.titulo}" class="form-control" id="titulo" name="titulo" required>
                                    </div>
                                    <div class="mb-3">
                                        <label for="genero" class="form-label">Género:</label>
                                        <select multiple class="form-select" id="genero" name="genero[]" required>
                                            <c:forEach var="genero" items="${arrayGeneros}">
                                                <c:set var="isSelected" value="false" />
                                                <c:forEach var="selectedGenero" items="${arrayGenerosSeleccionados}">
                                                    <c:if test="${genero eq selectedGenero}">
                                                        <c:set var="isSelected" value="true" />
                                                    </c:if>
                                                </c:forEach>
                                                <option value="${genero}" <c:if test="${isSelected eq 'true'}">selected</c:if>>${genero}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="mb-3">
                                        <label for="autor" class="form-label">Autor:</label>
                                        <input type="text" value="${libro.autor}" class="form-control" id="autor" name="autor" required>
                                    </div>
                                    <div class="mb-3">
                                        
                                        <label for="fechaEdicion" class="form-label">Fecha de Edición (dd-MM-yyyy):</label>
                                        <input type="date" value="${fechaFormateada}" class="form-control" id="fechaEdicion" name="fechaEdicion" required>
                                    </div>
                                    <div class="mb-3">
                                        <label for="numEjemplares" class="form-label">Número de Ejemplares:</label>
                                        <input type="text" value="${libro.numEjemplares}" class="form-control" id="numEjemplares" name="numEjemplares" required>
                                    </div>
                                    <div class="mb-3">
                                        <label for="imagen" class="form-label">Imagen de Portada:</label>
                                        <input type="file" class="form-control" id="imagen" name="imagen">
                                    </div>
                                    <div class="d-flex justify-content-between">
                                        <button type="submit" class="btn btn-primary">Editar libro</button>
                                        <a href="GestionLibros" class="btn btn-danger">Volver atrás</a>
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