package com.kiberhach.serialize;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

/**
 * Created by Хидир on 10.02.2017.
 */
public class Deserializer {
    private String fileName;
    private Document document;
    private Field _field;


    public Deserializer(String fileName) {
        this.fileName = fileName;
        this.document = getDocument();
    }

    private Document getDocument() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); //создали фабрику строителей, сложный и грамосткий процесс (по реже выполняйте это действие)
        Document doc = null;
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(new File(fileName));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    public String getClassname(){
        NodeList list = document.getChildNodes();
        Node childNode = list.item(0);
        String classname = childNode.getAttributes().getNamedItem("type").getNodeValue();

        return classname;
    }

    public NodeList getNodeFields(){
        NodeList list = document.getChildNodes();

        Node childNode = list.item(0);
        System.out.println(childNode.getAttributes().getNamedItem("type").getNodeValue());
        String classname = childNode.getAttributes().getNamedItem("type").getNodeValue();

        NodeList objecFiels = childNode.getChildNodes();
        //смотрим поля
        return objecFiels;
    }

    public Object getInstace() throws Exception{
        String classname = getClassname();

        NodeList objecFiels = getNodeFields();

        Class peop = Class.forName(classname);
        Object object = peop.newInstance();

        for(int i=0;i<objecFiels.getLength();i++){
            //Смотрим значения полей (название, тип, значение)
            for(int j=0;j<objecFiels.item(i).getAttributes().getLength();j++){

                if(objecFiels.item(i).getAttributes().item(j).getNodeName().equals("name")){
                    //System.out.println("-----" + objecFiels.item(i).getAttributes().item(j).getNodeValue());
                    _field = object.getClass().getDeclaredField(objecFiels.item(i).getAttributes().item(j).getNodeValue());
                    _field.setAccessible(true);
                }

                if(objecFiels.item(i).getAttributes().item(j).getNodeName().equals("value")){
                    //System.out.println("-----" + objecFiels.item(i).getAttributes().item(j).getNodeValue());
                    String value = objecFiels.item(i).getAttributes().item(j).getNodeValue();



                    //final Class<?> castClass = Class.forName("int");
                    //castClass.cast()

                    switch (_field.getType().getSimpleName()) {
                        case "int":
                            _field.set(object, Integer.parseInt(value));
                            break;
                        default:
                            //System.out.println("default");
                            _field.set(object, value);
                            break;
                    }

                }
            }
        }

        return object;

    }


}
