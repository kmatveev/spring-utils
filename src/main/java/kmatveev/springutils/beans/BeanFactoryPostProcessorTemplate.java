package kmatveev.springutils.beans;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.*;

public abstract class BeanFactoryPostProcessorTemplate implements BeanFactoryPostProcessor{

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        for (String beanName : beanFactory.getBeanDefinitionNames()) {
            if (beanFactory.containsBeanDefinition(beanName)) {
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
                handleRecursively(beanName, beanDefinition);
            }
        }
    }

    private void handleRecursively(String beanName, BeanDefinition beanDefinition) {
        String beanClassName = beanDefinition.getBeanClassName();
        boolean changed = processBeanDefinition(beanName, beanDefinition, beanClassName);
        if (!changed) {
            for (ConstructorArgumentValues.ValueHolder constructorValueHolder : beanDefinition.getConstructorArgumentValues().getGenericArgumentValues()) {
                if (constructorValueHolder.getValue() instanceof BeanDefinitionHolder) {
                    BeanDefinition innerBeanDefinition = ((BeanDefinitionHolder)constructorValueHolder.getValue()).getBeanDefinition();
                    handleRecursively(constructorValueHolder.getName(), innerBeanDefinition);
                }
            }
            for (PropertyValue propertyValue : beanDefinition.getPropertyValues().getPropertyValues()) {
                if (propertyValue.getValue() instanceof BeanDefinitionHolder) {
                    BeanDefinition innerBeanDefinition = ((BeanDefinitionHolder)propertyValue.getValue()).getBeanDefinition();
                    handleRecursively(propertyValue.getName(), innerBeanDefinition);
                }
            }
        }
    }

    protected abstract boolean processBeanDefinition(String beanName, BeanDefinition beanDefinition, String beanClassName);
}


