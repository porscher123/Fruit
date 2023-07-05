package myssm.io;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ClassPathXmlApplicationContext implements BeanFactory {

    private Map<String, Object> beanMap = new HashMap<>();

    public ClassPathXmlApplicationContext() {
        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("applicationContext.xml");

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = null;
            try {
                documentBuilder = documentBuilderFactory.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                throw new RuntimeException(e);
            }

            // 创建Document对象
            Document document = null;
            try {
                document = documentBuilder.parse(inputStream);
            } catch (SAXException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            NodeList beanNodeList = document.getElementsByTagName("bean");


            // 获取所有的bean结点
            for (int i = 0; i < beanNodeList.getLength(); i++) {
                Node beanNode = beanNodeList.item(i);
                // 如果是元素结点
                if (beanNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element beanElement = (Element) beanNode;
                    // 对应xml中的id和clas属性
                    String beanId = beanElement.getAttribute("id");
                    String className = beanElement.getAttribute("class");

                    Object beanObj = null;
                    try {
                        beanObj = Class.forName(className).getDeclaredConstructor().newInstance();
                    } catch (InstantiationException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    beanMap.put(beanId, beanObj);
                    // bean之间的依赖关系还没有设置


                }
            }
            // 5.组装bean之间的依赖关系
            for (int i = 0; i < beanNodeList.getLength(); i++) {
                Node beanNode = beanNodeList.item(i);
                // 如果是元素结点
                if (beanNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element beanElement = (Element) beanNode;
                    String beanId = beanElement.getAttribute("id");
                    NodeList childNodeList = beanElement.getChildNodes();
                    // 遍历子节点
                    for (int j = 0; j < childNodeList.getLength(); j++) {
                        // 获取子节点
                        Node childNode = childNodeList.item(j);
                        // 如果子节点的结点类型时元素结点且名称为property
                        if (childNode.getNodeType() == Node.ELEMENT_NODE
                                && childNode.getNodeName().equals("property")) {
                            // 转为Element
                            Element propertyElement = (Element) childNode;
                            // 获取元素值
                            String propertyName = propertyElement.getAttribute("name");
                            String propertyRef = propertyElement.getAttribute("ref");
                            // 1.找到propertyRef对应的实例
                            Object refObj = beanMap.get(propertyRef);
                            // 2. 给当前beanObj对象设置一个refObj实例的属性
                            Object beanObj = beanMap.get(beanId);

                            Class beanObjClass = beanObj.getClass();
                            // 获取名为propertyName的属性
                            Field filed = beanObjClass.getDeclaredField(propertyName);
                            filed.setAccessible(true);
                            // 给属性赋值
                            filed.set(beanObj, refObj);
                        }
                    }
                }
            }
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Object getBean(String id) {
        return beanMap.get(id);
    }
}
