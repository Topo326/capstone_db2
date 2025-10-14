package com.model.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TaskStateConverter implements AttributeConverter<TaskState, String> {

    @Override
    public String convertToDatabaseColumn(TaskState attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getDbValue();
    }

    @Override
    public TaskState convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return TaskState.fromDbValue(dbData);
    }
}