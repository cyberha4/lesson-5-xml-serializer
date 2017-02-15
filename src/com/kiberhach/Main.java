package com.kiberhach;

import com.kiberhach.serialize.Deserializer;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.lang.reflect.Field;

/**
 * Created by Хидир on 10.02.2017.
 */
public class Main {
    private static Field _field;
    public static void main(String[] argv) throws Exception {
        People people = new People("testPeopleXml",22,"tt");
        Student student = new Student("student", 44);

        JarClassLoader loader = new JarClassLoader("Kubator.jar");
        System.out.println(((loader.loadClass("com.kiberhach.Animal")) == null));

        Class c = Class.forName("com.kiberhach.Animal");
        c.newInstance();
        System.out.println(c);

        Downloader downloader = new Downloader("https://github.com/kiberhach/lesson-5-xml-serializer/raw/master/Kubator.jar");
        downloader.download("MyJar.jar");

        //System.exit(1);


        //serialize(people);
        //serialize(student);

        Deserializer deser = new Deserializer("com.kiberhach.People.xml");
        //Deserializer deser2 = new Deserializer("Student.xml");

        System.out.println(deser.getInstace());
        //System.out.println(deser2.getInstace());

    }

    public static void serialize(Object obj) throws Exception {


        Field[] fields = obj.getClass().getDeclaredFields();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation impl = builder.getDOMImplementation(); // более сложный, но и более гибкий способ создания документов
        Document doc = impl.createDocument(null, // namespaceURI
                null, // qualifiedName
                null); // doctype
        Element e1 = doc.createElement("Object");

        String className = obj.getClass().getName();
        e1.setAttribute("type", className);
        doc.appendChild(e1);

        for(Field field:
                fields){
            field.setAccessible(true);
            Element e = doc.createElement("field");
            e.setAttribute("name", field.getName());
            e.setAttribute("type", field.getType().getSimpleName());
            e.setAttribute("value", field.get(obj).toString());
            //e.setAttribute("value", field.get);
            e1.appendChild(e);
        }

        String fileName = "test/" + className+".xml";

        TransformerFactory transformerFactory =
                TransformerFactory.newInstance();
        Transformer transformer =
                transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result =
                new StreamResult(new File(fileName));
        transformer.transform(source, result);
        // Output to console for testing
        StreamResult consoleResult =
                new StreamResult(System.out);
        transformer.transform(source, consoleResult);
    }

}
