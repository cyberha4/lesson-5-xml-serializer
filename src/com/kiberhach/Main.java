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

        serialize(people);
        serialize(student);

        Deserializer deser = new Deserializer("C:\\php\\People.xml");
        Deserializer deser2 = new Deserializer("C:\\php\\Student.xml");

        System.out.println(deser.getInstace());
        System.out.println(deser2.getInstace());

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

        TransformerFactory transformerFactory =
                TransformerFactory.newInstance();
        Transformer transformer =
                transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result =
                new StreamResult(new File("C:\\php\\"+className+".xml"));
        transformer.transform(source, result);
        // Output to console for testing
        StreamResult consoleResult =
                new StreamResult(System.out);
        transformer.transform(source, consoleResult);
    }

}
