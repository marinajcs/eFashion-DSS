# Release Notes

**Fecha de subida**: 19/10/2024

**Fecha de actualización de RestControllers:** 17/12/2024

A continuación, se presenta un resumen de los tipos de usuarios, sus respectivos niveles de seguridad y las credenciales utilizadas:

## Tipos de usuarios/roles y niveles de seguridad

1. **Administrador (Admin)**:
   - *Permisos*: acceso completo a todas las funcionalidades, incluyendo las de niveles más bajos. Posee permisos especiales como el de la gestión de productos del catálogo (edición, adición y eliminación) y la posibilidad de exportar la base de datos.
   - *Nivel de seguridad*: alto/máximo (control sobre todas las funciones).
   - *Credenciales*: username: "admin" - password: "admin".

2. **Usuario estándar (Standard user)**:
   - *Permisos*: puede consultar el catálogo y tener su propia bolsa (o carrito), donde podrá añadir o quitar productos, para más tarde comprarlos, generando una factura en formato PDF y vaciando su carrito.
   - *Nivel de seguridad*: medio (acceso limitado a funciones básicas de comprador).
   - *Credenciales*: username: "user" - password: "user".

3. **Invitado (Guest)**:
   - *Permisos*: solo puede consultar el catálogo, ya que para poder disponer de un carrito y poder realizar compras tendrá que autenticarse.
   - *Nivel de Seguridad*: bajo (acceso de solo visualización).
   - Para la vista de invitados no es necesario autenticarse.
