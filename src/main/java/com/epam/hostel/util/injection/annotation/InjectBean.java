package com.epam.hostel.util.injection.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Identifies injectable by beans fields.
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectBean {

    /**
     * Contains the name of a bean, that should be inject.
     * @return bean name
     */
    String beanName();
}
