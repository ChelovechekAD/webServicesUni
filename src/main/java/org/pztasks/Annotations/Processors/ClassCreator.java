package org.pztasks.Annotations.Processors;

import org.pztasks.Annotations.DTO.ClassDTO;
import org.pztasks.Annotations.DTO.FieldDTO;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class ClassCreator {
    private ClassCreator() {
    }
    public static void record(ClassDTO classDTO, ProcessingEnvironment environment) {
        JavaFileObject builderFile = null;
        try {
            builderFile = environment.getFiler().createSourceFile(classDTO.getClassName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            assert builderFile != null;
            try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {
                out.println("package " + classDTO.getClassPackage() + ";");
                out.println();
                out.print("public class " + classDTO.getClassName() + " {");
                out.println();
                out.println();
                generateNames(classDTO.getFields(), out);
                out.println();
                out.println("}");
                out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void generateNames(Set<FieldDTO> fields, PrintWriter out) {
        for (FieldDTO field : fields) {
            out.println(" public static final String " + field.getFieldStringName() + " = \"" + field.getFieldName() + "\";");
        }
    }
}
