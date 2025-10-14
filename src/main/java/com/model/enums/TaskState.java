package com.model.enums;

public enum TaskState {
    Pending("Pending"),
    InProgress("In Progress"),
    Done("Done"),
    Canceled("Canceled");

    private final String dbValue;

    TaskState(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }

    public static TaskState fromDbValue(String value) {
        for (TaskState state : TaskState.values()) {
            if (state.dbValue.equalsIgnoreCase(value)) {
                return state;
            }
        }
        throw new IllegalArgumentException("Unknown TaskState value: " + value);
    }
}
