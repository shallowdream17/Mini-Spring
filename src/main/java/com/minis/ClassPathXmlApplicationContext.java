package com.minis;
//
//import org.dom4j.Document;
//import org.dom4j.DocumentException;
//import org.dom4j.Element;
//import org.dom4j.io.SAXReader;
//
//import java.lang.reflect.InvocationTargetException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class ClassPathXmlApplicationContext {
//    private List<BeanDefinition> beanDefinitions = new ArrayList<>();
//    private Map<String,Object> singletons = new HashMap<>();
//
//    //构造器获取外部配置，解析出Bean的定义，形成内存映像
//    public ClassPathXmlApplicationContext(String fileName){
//        this.readXML(fileName);
//        this.instanceBeans();
//    }
//
//    private void readXML(String fileName){
//        SAXReader saxReader = new SAXReader();
//        URL xmlPath = this.getClass().getClassLoader().getResource(fileName);
//        try {
//            Document document = saxReader.read(xmlPath);
//            Element rootElement = document.getRootElement();
//            //对配置文件中的每一个<bean>进行处理
//            for (Element element:((List<Element>) rootElement.elements())){
//                //获取bean的基本信息
//                String beanID = element.attributeValue("id");
//                String beanClassName = element.attributeValue("class");
//                BeanDefinition beanDefinition = new BeanDefinition(beanID,beanClassName);
//                //将Bean的定义存放到beanDefinitions
//                beanDefinitions.add(beanDefinition);
//            }
//        } catch (DocumentException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    //利用反射创建Bean实例，并存储在singletions中
//    private void instanceBeans() {
//        for(BeanDefinition beanDefinition : beanDefinitions){
//            try {
//                singletons.put(beanDefinition.getId(),
//                        Class.forName(beanDefinition.getClassName()).getDeclaredConstructor().newInstance());
//            } catch (InstantiationException e) {
//                throw new RuntimeException(e);
//            } catch (IllegalAccessException e) {
//                throw new RuntimeException(e);
//            } catch (InvocationTargetException e) {
//                throw new RuntimeException(e);
//            } catch (NoSuchMethodException e) {
//                throw new RuntimeException(e);
//            } catch (ClassNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//    //这个是对外的一个方法，让外部程序从容器中获取Bean实例，会逐步演化成核心方法
//    public Object getBean(String beanName){
//        return singletons.get(beanName);
//    }
//}


import com.minis.beans.BeanFactory;
import com.minis.beans.BeansException;
import com.minis.beans.SimpleBeanFactory;
import com.minis.beans.XmlBeanDefinitionReader;
import com.minis.core.ClassPathXmlResource;
import com.minis.core.Resource;

public class ClassPathXmlApplicationContext implements BeanFactory {
    BeanFactory beanFactory;
    //context负责整合容器的启动过程，读外部配置，解析Bean定义，创建BeanFactory
    public ClassPathXmlApplicationContext(String fileName){
        Resource resource = new ClassPathXmlResource(fileName);
        BeanFactory beanFactory = new SimpleBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);
        this.beanFactory=beanFactory;
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        return this.beanFactory.getBean(beanName);
    }

    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanFactory.registerBeanDefinition(beanDefinition);
    }
}