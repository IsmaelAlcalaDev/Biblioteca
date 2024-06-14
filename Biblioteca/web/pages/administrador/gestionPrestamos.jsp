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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="css/style.css"/>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js" defer></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <title>Gestión prestamos</title>
</head>
<body class="d-flex flex-column min-vh-100">
    <%@ include file="../../components/navbarPrivada.jsp" %>
    <main class="flex-grow-1">
        <h3 class="mt-5 text-center">Gestion de Prestamos</h3>
            <div class="container mt-2 mb-5">
                <div class="row justify-content-center text-center">
                    <div class="col-md-11">
                    <c:if test="${not empty listaPrestamos}">
                        <div class="d-flex justify-content-between my-3">
                            <form action="FiltrarPrestamos" method="post" class="d-flex flex-column gap-3">
                                <div class="form-group mb-0 d-flex align-items-center">
                                    <div class="text-start">
                                        <label for="isbn" class="w-75">Filtrar por libro:</label>
                                        <select class="form-control" id="isbn" name="isbn">
                                            <option value="">Selecciona un libro</option>
                                            <option value="mostrarTodos">Mostrar todos</option>
                                            <c:forEach var="libro" items="${listaLibros}">
                                                <option value="${libro.isbn}">${libro.titulo}</option>
                                            </c:forEach>
                                        </select>    
                                    </div>
                                    <div class="text-start">
                                        <label for="estado" class="ms-3 d-flex text-start">Filtrar por estado:</label>
                                        <div class="d-flex  text-start"> 
                                            <select class="form-control ms-3" id="estado" name="estado">
                                                <option value="" selected>Selecciona un estado</option>
                                                <option value="todos">Mostrar Todos</option>
                                                <option value="prestados">Mostrar Prestados</option>
                                                <option value="devueltos">Mostrar Devueltos</option>
                                            </select>
                                            <button type="submit" class="btn btn-primary ms-3">Filtrar</button>
                                        </div>
                                    </div>                             
                                </div>
                            </form>
                            <form action="EnviarEmailASocio" method="POST" class="d-flex align-items-end">
                                <div class="text-end mt-0 d-flex flex-column">
                                    <c:if test="${not empty mensaje}">
                                        <small class="mensaje text-dark text-start mb-2">${mensaje}</small>
                                    </c:if>
                                    <c:if test="${not empty error}">
                                        <small class="mensaje text-danger text-start mb-2">${error}</small>
                                    </c:if>
                                    <button type="button" class="btn btn-danger" onclick="mostrarFormulario()">
                                        <i class="fas fa-bell"></i> Notificar Préstamos Pendientes a Socios
                                    </button>
                                </div>
                            </form>
                        <script>
                            setTimeout(function(){
                                var elementos = document.querySelectorAll('.mensaje');
                                elementos.forEach(function(elemento) {
                                    elemento.style.display = 'none';
                                });
                            }, 3000);
                        </script>
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
                        <a href="PanelAdministrador">Volver al panel de administración</a>
                    </c:if>
                </div>
            </div>
        </div>
    </main>
    <%@ include file="../../components/footer.jsp" %>
    <script>
        function mostrarFormulario() {
            Swal.fire({
                title: 'Ingrese su correo electrónico y contraseña',
                html:
                    '<form id="formulario-swal">' +
                    '<input id="email" name="email" class="swal2-input" placeholder="Correo electrónico">' +
                    '<input id="password" name="contrasena" type="password" class="swal2-input" placeholder="Contraseña">' +
                    '</form>',
                focusConfirm: false,
                showCancelButton: true,
                confirmButtonText: 'Enviar',
                cancelButtonText: 'Cancelar',
                preConfirm: () => {
                    const email = Swal.getPopup().querySelector('#email').value;
                    const password = Swal.getPopup().querySelector('#password').value;
                    enviarFormulario(email, password);
                }
            });
        }

        function enviarFormulario(email, password) {
            const form = document.createElement('form');
            form.method = 'POST';
            form.action = 'EnviarEmailASocio'; // Ruta del servlet

            const inputEmail = document.createElement('input');
            inputEmail.type = 'hidden';
            inputEmail.name = 'email';
            inputEmail.value = email;
            form.appendChild(inputEmail);

            const inputPassword = document.createElement('input');
            inputPassword.type = 'hidden';
            inputPassword.name = 'contrasena';
            inputPassword.value = password;
            form.appendChild(inputPassword);

            document.body.appendChild(form);
            form.submit();
        }
    </script>
</body>
</html>