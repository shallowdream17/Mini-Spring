package com.minis.beans;


import com.minis.BeanDefinition;
import com.minis.core.Resource;
import org.dom4j.Element;

/**
 * 将解析的XML内容转换成BeanDefinition，并加载到BeanFactory中
 */
public class XmlBeanDefinitionReader {
    BeanFactory beanFactory;
    public XmlBeanDefinitionReader(BeanFactory beanFactory){
        this.beanFactory = beanFactory;
    }
    public void loadBeanDefinitions(Resource resource){
        while(resource.hasNext()){
            Element element = (Element) resource.next();
            String beanID = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanID,beanClassName);
            this.beanFactory.registerBeanDefinition(beanDefinition);
        }
    }
}
