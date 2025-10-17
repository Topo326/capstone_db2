# 📝 TO DO LIST CAPSTONE DB2

Sistema de gestión de tareas desarrollado en **Java** con **Hibernate** y **SQL Server**.  
Permite a los usuarios registrarse, ver sus tareas, crear nuevas, editarlas, eliminarlas y organizarlas por **categorías**.  
También incluye la funcionalidad de **gestionar equipos**, permitiendo crear grupos de trabajo y ver las tareas asociadas a cada uno.

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

1. Crear la base de datos ejecutando el script principal:
- `Script_Creacion_Tablas.sql`
2. Ejecutar los scripts adicionales para asegurar la correcta estructura:
- `Add_Active_Column.sql`
- `Trigger_History_Log.sql`

3. Revisar el archivo `.env` para asegurarte de que las credenciales sean correctas:

```env
DB_URL=jdbc:sqlserver://localhost:1433;databaseName=to_do_capstone;encrypt=true;trustServerCertificate=true
DB_USER=tu_usuario
DB_PASSWORD=tu_contraseña
```

## Ejecucion

Ejecutar el proyecto desde la terminal de IntelliJ:

- `mvn clean compile exec:java -Dexec.mainClass="com.Main"`
