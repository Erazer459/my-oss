package io.github.franzli347.foss.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

/**
 * Spring Bean 工具类
 * @author FranzLi
 */
@Component
public class BeanUtils implements BeanFactoryAware {
    private static BeanFactory beanFactory = null;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        BeanUtils.beanFactory = beanFactory;
    }

    public static <T> T getBean(String beanName) {
        return (T) beanFactory.getBean(beanName);
    }
}