package kmatveev.springutils.beans;

import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;

public class BeanFactoryUtils {

    public static void setProperty(BeanDefinition beanDefinition, String propertyName, Object value) {
        beanDefinition.getPropertyValues().removePropertyValue(propertyName);
        beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(propertyName, value));
    }


    public static void replaceConstructorArg(BeanDefinition beanDefinition, String argName, Object value) {
        beanDefinition.getConstructorArgumentValues().getGenericArgumentValues().forEach(
                x -> { if (x.getName().equals(argName)) {x.setValue(value); } }
        );
    }
}
