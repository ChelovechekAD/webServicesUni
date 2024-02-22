package org.pztasks.Annotations.DTO;

public class FieldDTO {
    private final String fieldStringName;
    private final String fieldName;

    private FieldDTO(String fieldStringName, String fieldName) {
        this.fieldStringName = fieldStringName;
        this.fieldName = fieldName;
    }
    public static FieldDTO of(String fieldStringName, String fieldName){
        return new FieldDTO(fieldStringName, fieldName);
    }

    public String getFieldStringName() {
        return fieldStringName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
