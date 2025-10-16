CREATE TRIGGER After_User_New
ON Users
AFTER INSERT
AS
BEGIN
	INSERT INTO Category (Category_name, Category_description,Active)
	values ('General', 'Categoria base al crear un nuevo usuario', 1);
END;
GO
