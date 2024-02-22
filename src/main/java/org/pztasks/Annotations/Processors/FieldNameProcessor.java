package org.pztasks.Annotations.Processors;

import com.google.auto.service.AutoService;
import com.google.common.base.CaseFormat;
import org.pztasks.Annotations.FieldNames;
import org.pztasks.Annotations.DTO.ClassDTO;
import org.pztasks.Annotations.DTO.FieldDTO;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@SupportedAnnotationTypes("org.pztasks.Annotations.FieldNames")
@SupportedSourceVersion(SourceVersion.RELEASE_11)
@AutoService(Processor.class)
public class FieldNameProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for(TypeElement annotation : annotations){
            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
            for (Element annotatedElement : annotatedElements){
                final TypeMirror mirror = annotatedElement.asType();
                final String annotatedElementName = annotatedElement.getSimpleName().toString();
                final FieldNames settings = annotatedElement.getAnnotation(FieldNames.class);
                final String newClassName = annotatedElementName + settings.postfix();
                final Set<FieldDTO> fields = annotatedElement.getEnclosedElements().stream()
                        .filter(this::isField)
                        .map(
                                element -> {
                                    final String fieldName = element.getSimpleName().toString();
                                    final String fieldStringName =
                                            CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, fieldName);
                                    return FieldDTO.of(fieldStringName, fieldName);
                                }
                        ).collect(Collectors.toSet());
                final ClassDTO newClass = new ClassDTO();
                newClass.setClassName(newClassName);
                newClass.setFields(fields);
                newClass.setClassPackage(getPackage(mirror));
                ClassCreator.record(newClass, processingEnv);
            }
        }
        return true;
    }
    public boolean isField(Element element) {
        return element != null && element.getKind().isField();
    }
    public static String getPackage(TypeMirror typeMirror) {
        final String[] split = typeMirror.toString().split("\\.");
        return String.join(".", Arrays.copyOf(split, split.length - 1));
    }
}
