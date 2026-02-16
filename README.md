# MediCat  - Aplicativo Web para Veterinarias

Proyecto académico desarrollado para la materia **Desarrollo Web**.

---

## Descripción del Proyecto

**MediCat** es una aplicación web diseñada para digitalizar la gestión de una clínica veterinaria especializada en hospitalización de perros y gatos.

El sistema permite:

* Promocionar los servicios de la veterinaria.
* Registrar dueños y mascotas.
* Gestionar tratamientos médicos.
* Permitir a los clientes consultar el estado de sus mascotas.
* Proporcionar un portal administrativo con indicadores clave del negocio.
* Implementar control de acceso basado en roles.

---

## Objetivo

Desarrollar una solución web completa que permita:

* Optimizar la gestión interna de la clínica.
* Llevar control digital de tratamientos y medicamentos.
* Brindar acceso diferenciado según el rol del usuario.
* Visualizar métricas clave del negocio mediante un dashboard.

---

## Roles del Sistema

El sistema cuenta con tres tipos de usuarios:

### Cliente (Dueño)

* Inicia sesión.
* Consulta sus mascotas registradas.
* Visualiza detalles y tratamientos.
* No puede modificar información.

### Veterinario

* Accede al portal veterinario.
* CRUD de dueños.
* CRUD de mascotas.
* Registrar tratamientos.
* Consultar historial médico.
* Visualizar mascotas tratadas.

### Administrador

* Accede al portal administrador.
* CRUD de veterinarios.
* Visualiza dashboard con KPIs.
* Controla estados laborales (activo/inactivo).

---

## Arquitectura del Proyecto

El sistema estrá dividido en:

* **Frontend:** SPA desarrollada en Angular.
* **Backend:** API REST.
* **Base de datos:** Modelo relacional.
* **Repositorio:** GitHub con ramas:

  * `dev`
  * `production` 
  * `main`

---

## Reglas del Negocio Importantes

* Solo mascotas activas pueden recibir tratamientos.
* Si se elimina un dueño, sus mascotas se eliminan en cascada.
* Los veterinarios pueden tratar múltiples mascotas y viceversa.
* No se usa herencia en el modelo.
* Se guarda historial completo de tratamientos.

---
