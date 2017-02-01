package com.epam.hostel.util.injection;

import com.epam.hostel.util.injection.annotation.InjectBean;
import com.epam.hostel.util.injection.exception.InjectionException;
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Realizes Dependency Injection Pattern.
 * You can use {@link InjectBean} annotation with name of bean
 * to show that class field should be injected during runtime.
 * Information about beans stored in xml configuration files.
 */
public interface Injectable {
    Logger logger = Logger.getLogger(Injectable.class);
    String BEAN = "bean";
    String NAME = "name";
    String CLASS = "class";

    /**
     * Search all fields with {@link InjectBean} annotation
     * and assign for it they dependency
     *
     * @param configuration path to xml file, that contains bean
     */
    default void inject(String configuration) {
        Map<String, String> beans = getDataFromConfiguration(configuration);

        try {
            for (Field field : this.getClass().getDeclaredFields()) {
                InjectBean annotation = field.getAnnotation(InjectBean.class);
                if (annotation != null) {
                    field.setAccessible(true);

                    String beanName = annotation.beanName();
                    String className = beans.get(beanName);
                    if (className == null) {
                        logger.error("Configuration file doesn't have requested class with name" + beanName);
                        throw new InjectionException("Configuration file doesn't have requested class with name" + beanName);
                    } else {
                        field.set(this, Class.forName(className).newInstance());
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e);
            throw new InjectionException("Can't inject dependency and create object", e);
        }
    }

    /**
     * Parses xml file by SAX parser and contains all beans into {@link Map}
     * @param configuration path to xml file, that contains bean
     * @return collection where key - the name of a bean and
     * value - bean class name
     */
    default Map<String, String> getDataFromConfiguration(String configuration) {
        HashMap<String, String> beans = new HashMap<>();

        try {
            XMLReader reader = XMLReaderFactory.createXMLReader();
            reader.setContentHandler(new DefaultHandler() {
                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    if (qName.equals(BEAN)) {
                        beans.put(attributes.getValue(NAME), attributes.getValue(CLASS));
                    }
                }
            });

            reader.parse(new InputSource(this.getClass().getResourceAsStream(configuration)));
        } catch (SAXException | IOException e) {
            logger.error(e);
            throw new InjectionException("Error during reading configuration file", e);
        }

        return beans;
    }
}
