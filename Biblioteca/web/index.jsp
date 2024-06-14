<%@ page import="java.util.List" %>
<%@ page import="javax.persistence.Persistence" %>
<%@ page language="java" contentType="text/html; charset=latin1" pageEncoding="latin1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setLocale value="en_US"/>
<fmt:bundle basename="bundle.message">
<c:set var="datePattern">
    <fmt:message key="date.pattern"/>
</c:set>
<!DOCTYPE html>
<html lang="<fmt:message key="lang"/>">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><fmt:message key="nav.inicio"/></title>
    
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.css">
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js" defer></script>
    <link rel="stylesheet" href="css/style.css"/>
    
</head>
<body class="d-flex flex-column min-vh-100">
    <c:if test="${not empty usuario}">
        <%@ include file="/components/navbarPrivada.jsp" %>
    </c:if>
    <c:if test="${empty usuario}">
        <%@ include file="/components/navbarPublica.jsp" %>
    </c:if>
    <main class="flex-grow-1">
        <h3 class="mt-5 text-center"><fmt:message key="page.titulo"/></h3>
        <div class="container my-3">
            <div class="row justify-content-center text-center">
                <div class="col-md-11">
                    <c:if test="${not empty listaLibros}">
                        <table id="tablaLibros" class="table table-striped text-center align-middle my-5">
                            <thead>
                                <tr>
                                    <th><fmt:message key="table.portada"/></th>
                                    <th><fmt:message key="table.titulo"/></th>
                                    <th><fmt:message key="table.autor"/></th>
                                    <th><fmt:message key="table.genero"/></th>
                                    <th><fmt:message key="table.fecha.publicacion"/></th>
                                    <th><fmt:message key="table.isbn"/></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="libro" items="${listaLibros}">
                                    <tr>
                                        <td><img src="assets/${libro.imagenPortada}" alt="<fmt:message key="book.portada"/> ${libro.titulo}" style="width: 100px; height: auto;"></td>
                                        <td>${libro.titulo}</td>
                                        <td>${libro.autor}</td>
                                        <td style="max-width: 150px; word-wrap: break-word;">
                                            <ul class="list-group">
                                                <c:forEach var="genero" items="${fn:split(libro.genero, ',')}">
                                                    <li class="list-group-item">${genero}</li>
                                                </c:forEach>
                                            </ul>
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${libro.fechaEdicion}" pattern="${datePattern}"/>
                                        </td>
                                        <td>${libro.isbn}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:if>
                    <c:if test="${empty listaLibros}">
                        <p><fmt:message key="page.sin.libros"/></p>
                    </c:if>
                </div>
             </div>
        </div>
    </main>
    <%@ include file="/components/footer.jsp" %>
    <script type="text/javascript">
        $(document).ready(function() {
            // Inicializar DataTable sin funcionalidades extras, solo el input de filtro
            $('#tablaLibros').DataTable({
                "initComplete": function(settings, json) {
                    $('#tablaLibros').removeClass('no-footer'); // Elimina la clase que aplica el estilo
                },
                "language": {
                    "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/<fmt:message key="datatable"/>.json" // Archivo de idioma español
                },
                "paging": false,
                "info": false,
                "searching": true,
                "dom": '<"filtro"lf>t<"footer"ip>',
            });
        });
    </script>
</body>
</html>
</fmt:bundle>
