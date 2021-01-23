import com.google.auto.service.AutoService;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

/**
 * 03.12.2020
 * 15.Annotations_SOURCE
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes(value = {"HtmlForm","HtmlInput"})
@Slf4j
public class HtmlProcessor extends AbstractProcessor {
    String path;
    Path out;
    BufferedWriter writer;
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // получить типы с аннотаций HtmlForm
        Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(HtmlForm.class);

        for (Element element : annotatedElements) {
            log.info("Сообщение из SLF4J");
            // получаем полный путь для генерации html
            String path = HtmlProcessor.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            // User.class -> User.html
            path = path.substring(1) + element.getSimpleName().toString() + ".ftlh";
            out = Paths.get(path);
            try {
                writer = new BufferedWriter(new FileWriter(out.toFile()));
                HtmlForm annotation = element.getAnnotation(HtmlForm.class);

                writer.write("<form action='" + annotation.action() + "' method='" + annotation.method() + "'/>");
                writer.newLine();
                element.getEnclosedElements().stream().forEach(x->{
                    HtmlInput innerAnnotation = x.getAnnotation(HtmlInput.class);
                    if (innerAnnotation!=null) {
                        try {
                            writer.write("<input type='" + innerAnnotation.type() + "' name='" + innerAnnotation.name() + "' placeholder='" + innerAnnotation.placeholder() + "'/>");
                            log.info("InnerAnnotation type {}",innerAnnotation.type());
                            log.info("InnerAnnotation plaeceholder {}",innerAnnotation.placeholder());
                            writer.newLine();
                        } catch (IOException e) {

                            log.error("Ошибка {}",new IllegalStateException(e).toString());

                            e.printStackTrace();
                        }
                    }
                });
                writer.write("</form>");
                writer.flush();
                writer.close();
            } catch (IOException e) {

                log.error("Ошибка {}",new IllegalStateException(e).toString());

                throw new IllegalStateException(e);
            }



        }
        /*annotatedElements = roundEnv.getElementsAnnotatedWith(HtmlInput.class);
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, annotatedElements.toString());


        annotatedElements.forEach(element -> {

          *//*  String path = HtmlProcessor.class.getProtectionDomain().getCodeSource().getLocation().getPath();*//*
            // User.class -> User.html
           *//* path = path.substring(1) + element.getSimpleName().toString() + ".ftlh";*//*
            *//*Path out = Paths.get(path);*//*

            try {
               *//* BufferedWriter writer = new BufferedWriter(new FileWriter(out.toFile()));*//*
                HtmlInput annotation = element.getAnnotation(HtmlInput.class);
                writer.write("<input type='" + annotation.type() + "' name='" + annotation.name() + "' placeholder='" + annotation.placeholder() + "'/>");
                writer.newLine();

            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        });*/

       /* try {
            writer.write("</form>");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return true;
    }
}
