# 📝 TO DO LIST CAPSTONE DB2

Sistema de gestión de tareas desarrollado en **Java** con **Hibernate** y **SQL Server**.  
Permite a los usuarios **registrarse**, **ver**, **crear**, **editar** y **eliminar** tareas, además de **organizarlas por categorías**.  
También ofrece la funcionalidad de **gestionar equipos**, permitiendo crear grupos de trabajo y visualizar las tareas asociadas a cada uno.

---

## 🚀 Características principales

- 👤 **Registro e inicio de sesión** de usuarios
- ✅ **Creación, visualización, modificación y eliminación** de tareas
- 🗂️ **Gestión de categorías** de tareas
- 🧩 **Gestión de equipos** por tareas
- 🖥️ **Interfaz de consola interactiva**
- 🗄️ Conexión a base de datos **SQL Server** mediante **Hibernate ORM**

---

## ⚙️ Configuración de la base de datos

### 1️⃣ Crear la base de datos y tablas principales

Ejecuta el script principal en tu gestor SQL despues de crear la base de datos:

```bash
  Script_Creacion_Tablas.sql
  Add_Active_Column.sql
  Trigger_History_Log.sql
```
Recomendación:
Puedes ejecutar los scripts desde:

Windows: usando SQL Server Management Studio (SSMS) o DBeaver.

Linux/macOS: usando Azure Data Studio, DBeaver, o el comando sqlcmd en terminal:

- `sqlcmd -S localhost -U tu_usuario -P tu_contraseña -i Script_Creacion_Tablas.sql`

## Edita el archivo .env y configura tus credenciales de conexión:

```
DB_URL=jdbc:sqlserver://localhost:1433;databaseName=to_do_capstone;encrypt=true;trustServerCertificate=true
DB_USER=tu_usuario
DB_PASSWORD=tu_contraseña
```

## 🧩 Ejecución del proyecto

Desde IntelliJ IDEA en el archivo main hacer click en el boton Play.


