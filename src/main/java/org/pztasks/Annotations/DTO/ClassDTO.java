package org.pztasks.Annotations.DTO;

import java.util.Set;

public class ClassDTO {

    private String className;
    private Set<FieldDTO> fields;
    private String classPackage;

    public String getClassName(){
        return className;
    }

    public void setClassName(String className){
        this.className = className;
    }

    public Set<FieldDTO> getFields() {
        return fields;
    }

    public void setFields(Set<FieldDTO> fields) {
        this.fields = fields;
    }

    public String getClassPackage() {
        return classPackage;
    }

    public void setClassPackage(String classPackage) {
        this.classPackage = classPackage;
    }
}
