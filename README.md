# 📚 Aplicación de Gestión de Biblioteca

Esta aplicación es un sistema de gestión de biblioteca desarrollado utilizando el patrón de diseño MVC, JSP y JPA. La aplicación permite a los usuarios registrar y gestionar libros, empleados, préstamos y más.

## 🌟 Características Principales

### Funcionalidades Públicas

- **Página de Inicio**: Muestra todos los libros con sus portadas, estén o no prestados, y permite filtrar y ordenar por diversos campos.
- **Registro**: Formulario de registro con validación de contraseñas y email, registro como socio por defecto.
- **Inicio de Sesión**: Valida usuario por email y contraseña, redirigiendo según el rol del usuario.

### Funcionalidades del Administrador

- **Panel de Administración**: Acceso a la gestión de empleados, libros y préstamos.
- **Gestión de Empleados**: Listado, añadido, edición y eliminación de empleados y administradores.
- **Gestión de Préstamos**: Historial de préstamos con opciones de filtrado y envío de emails masivos a usuarios con préstamos pendientes.
- **Gestión de Libros**: Listado, añadido, edición y eliminación de libros, con gestión de géneros y validación de préstamos asociados.

### Funcionalidades del Empleado

- **Panel de Administración**: Acceso a la gestión de préstamos y penalizaciones.
- **Consultar Préstamos**: Historial de préstamos con opciones de filtrado y visualización de gráficas.
- **Realizar Préstamos**: Formulario para realizar préstamos, con validación de disponibilidad de libros y estado de los socios.

### Funcionalidades del Usuario

- **Panel de Usuario**: Historial de préstamos con opciones de devolución y visualización del estado de penalización.

## 🛠 Tecnologías Utilizadas

- **Lenguajes**: Java, JSP
- **Frameworks**: JPA
- **Bases de Datos**: MySQL
- **Otros**: HTML, CSS, JavaScript
