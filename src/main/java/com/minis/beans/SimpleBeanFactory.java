package com.minis.beans;

import com.minis.BeanDefinition;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleBeanFactory implements BeanFactory{
    private List<BeanDefinition> beanDefinitions = new ArrayList<>();
    private List<String> beanNames = new ArrayList<>();
    private Map<String,Object> singletons = new HashMap<>();
    public SimpleBeanFactory(){}

    /**
     * getbean,容器的核心方法
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object getBean(String beanName) throws BeansException {
        //先尝试直接拿bean实例
        Object singleton = singletons.get(beanName);
        //如果此时还没有这个bean的实例，则获取它的定义来创建实例
        if(singleton==null){
            int pos = beanName.indexOf(beanName);
            //pos=-1说明这个bean还没有beandefinition
            if(pos==-1){
                throw new BeansException("can't find bean!");
            }
            //获取bean的定义
            BeanDefinition beanDefinition = this.beanDefinitions.get(pos);
            try{
                singleton = Class.forName(beanDefinition.getClassName()).getDeclaredConstructor().newInstance();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            singletons.put(beanDefinition.getId(),singleton);
        }
        return singleton;
    }

    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanDefinitions.add(beanDefinition);
        this.beanNames.add(beanDefinition.getId());
    }
}
