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