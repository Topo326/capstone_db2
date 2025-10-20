USE to_do_capstone;
GO

CREATE TRIGGER after_insert_user
ON Users
AFTER INSERT
AS
Begin
	SET NOCOUNT ON;

	INSERT INTO HistoryLog(HistoryLog_Table_Change, HistoryLog_Type_Change, HistoryLog_Change_Date)
	SELECT 
		'USERS',
		'INSERT',
		GETDATE()
	FROM inserted;
END;
GO

CREATE TRIGGER after_insert_task
ON Task
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;

    INSERT INTO HistoryLog(HistoryLog_Table_Change, HistoryLog_Type_Change, HistoryLog_Change_Date)
    SELECT
        'TASK',
        'INSERT',
        GETDATE()
    FROM inserted;
END;
GO

CREATE TRIGGER after_update_task
ON Task
AFTER UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    INSERT INTO HistoryLog(HistoryLog_Table_Change, HistoryLog_Type_Change, HistoryLog_Change_Date)
    SELECT
        'TASK',
        'UPDATE',
        GETDATE()
    FROM inserted;
END;
GO

CREATE TRIGGER after_insert_category
ON Category
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;

    INSERT INTO HistoryLog(HistoryLog_Table_Change, HistoryLog_Type_Change, HistoryLog_Change_Date)
    SELECT
        'CATEGORY',
        'INSERT',
        GETDATE()
    FROM inserted;
END;
GO

CREATE TRIGGER after_update_category
ON Category
AFTER UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    INSERT INTO HistoryLog(HistoryLog_Table_Change, HistoryLog_Type_Change, HistoryLog_Change_Date)
    SELECT
        'CATEGORY',
        'UPDATE',
        GETDATE()
    FROM inserted;
END;
GO

CREATE TRIGGER after_insert_team
ON Team
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;

    INSERT INTO HistoryLog(HistoryLog_Table_Change, HistoryLog_Type_Change, HistoryLog_Change_Date)
    SELECT
        'TEAM',
        'INSERT',
        GETDATE()
    FROM inserted;
END;
GO

CREATE TRIGGER after_update_team
ON Team
AFTER UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    INSERT INTO HistoryLog(HistoryLog_Table_Change, HistoryLog_Type_Change, HistoryLog_Change_Date)
    SELECT
        'TEAM',
        'UPDATE',
        GETDATE()
    FROM inserted;
END;
GO