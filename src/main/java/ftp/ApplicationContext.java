package ftp;

import anno.FTPResponseHandler;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * create by zhong
 * ftp
 * Date 2019/4/27
 */
public class ApplicationContext {
    private Map handlers = new ConcurrentHashMap<String, ResponseHandler>();

    public ApplicationContext(String handlerPackage) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        if (handlerPackage == null) {
            throw new NullPointerException();
        }
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resourcePatternResolver.getResources("classpath*:" + handlerPackage + "\\*");
        MetadataReaderFactory metadata = new SimpleMetadataReaderFactory();
        for (Resource resource : resources) {
            MetadataReader metadataReader = metadata.getMetadataReader(resource);
            ScannedGenericBeanDefinition beanDefinition = new ScannedGenericBeanDefinition(metadataReader);
            beanDefinition.setResource(resource);
            beanDefinition.setSource(resource);
            String className = beanDefinition.getBeanClassName();
            Class c = Class.forName(className);
            FTPResponseHandler handler = Class.forName(className).getDeclaredAnnotation(FTPResponseHandler.class);
            if (handler != null) {
                register(handler.status(), (ResponseHandler) c.newInstance());
            }
        }
    }

    public Map getHandlers() {
        return handlers;
    }

    public void setHandlers(Map handlers) {
        this.handlers = handlers;
    }

    public void register(String status, ResponseHandler handler) {
        handlers.put(status, handler);
    }
}
