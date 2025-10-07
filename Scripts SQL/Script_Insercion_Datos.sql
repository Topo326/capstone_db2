USE [to_do_capstone];
GO

-- 1️⃣ Categorías
INSERT INTO [dbo].[Category] (Category_name, Category_description) VALUES
('Desarrollo', 'Tareas relacionadas con programación y desarrollo de software'),
('Diseño', 'Creación de interfaces, logotipos y material visual'),
('Documentación', 'Elaboración de manuales y reportes técnicos'),
('Testing', 'Pruebas de calidad y corrección de errores');
GO

-- 2️⃣ Equipos
INSERT INTO [dbo].[Team] (Team_name, Team_description) VALUES
('Backend Team', 'Equipo encargado del desarrollo del servidor y base de datos'),
('Frontend Team', 'Equipo encargado de la interfaz gráfica y experiencia de usuario'),
('QA Team', 'Equipo responsable de las pruebas y control de calidad');
GO

-- 3️⃣ Usuarios
INSERT INTO [dbo].[Users] (Users_name, Users_password, Users_Create_Day) VALUES
('Manuel', '1234', GETDATE()),
('Laura', 'abcd', GETDATE()),
('Carlos', 'pass', GETDATE()),
('Sofia', 'secure', GETDATE());
GO

-- 4️⃣ Tareas
INSERT INTO [dbo].[Task] (Category_id, Task_name) VALUES
(1, 'Implementar API REST para autenticación'),
(2, 'Diseñar logo principal del proyecto'),
(3, 'Redactar manual de instalación'),
(4, 'Ejecutar pruebas unitarias en módulo de usuarios');
GO

-- 5️⃣ Detalles de tarea
INSERT INTO [dbo].[TaskDetail] (Task_id, TaskDetail_Description, TaskDetail_Init_Date, TaskDetail_End_Date, TaskDetail_State) VALUES
(1, 'Crear endpoints para login y registro', '2025-09-01', '2025-09-10', 'Done'),
(2, 'Diseño preliminar del logo y variantes', '2025-09-05', '2025-09-12', 'In Progress'),
(3, 'Documentar el proceso de despliegue en servidor', '2025-09-08', '2025-09-20', 'Pending'),
(4, 'Realizar pruebas con JUnit y Mockito', '2025-09-15', '2025-09-25', 'In Progress');
GO

-- 6️⃣ Asignación de tareas a usuarios y equipos
INSERT INTO [dbo].[TaskUser] (Users_id, Task_id, Team_id) VALUES
(1, 1, 1),  -- Manuel en backend
(2, 2, 2),  -- Laura en frontend
(3, 3, 1),  -- Carlos en backend
(4, 4, 3);  -- Sofia en QA
GO