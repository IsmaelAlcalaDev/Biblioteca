<header>
    <nav class="navbar navbar-expand-lg navbar-light bg-dark">
        <div class="container">
            <a class="navbar-brand text-white fs-3" href="Index">Biblioteca</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto my-2">
                    <li class="nav-item px-3">
                        <a class="nav-link text-white" href="Index">Inicio</a>
                    </li>
                    <li class="nav-item px-3">
                        <a class="nav-link text-white" href="CerrarSesion">Cerrar sesión</a>
                    </li>
                    <%-- Muestra panel dependiendo del ROL --%>
                    <c:if test="${sessionScope.usuario.tipoUsuario eq 'ADMIN'}">
                        <li class="nav-item px-3">
                            <a class="btn btn-primary nav-link text-white" href="PanelAdministrador">Panel de Administración</a>
                        </li>
                    </c:if>
                    <c:if test="${sessionScope.usuario.tipoUsuario eq 'EMPLEADO'}">
                        <li class="nav-item px-3">
                            <a class="btn btn-primary nav-link text-white" href="PanelEmpleado">Panel de Empleado</a>
                        </li>
                    </c:if>
                    <c:if test="${sessionScope.usuario.tipoUsuario eq 'SOCIO'}">
                        <li class="nav-item px-3">
                            <a class="btn btn-primary nav-link text-white" href="PanelUsuario">Panel de Usuario</a>
                        </li>
                    </c:if>
                    <li class="nav-item px-3">
                        <span class="nav-link text-white">${sessionScope.usuario.nombre.toUpperCase()}</span>
                    </li>
                </ul>
            </div>
        </div>
    </nav>    
</header>

