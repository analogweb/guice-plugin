package org.analogweb.guice;

import org.analogweb.ApplicationContextResolver;
import org.analogweb.ContainerAdaptorFactory;
import org.analogweb.util.Assertion;

import com.google.inject.Injector;

/**
 * {@link GuiceContainerAdaptor}のインスタンスを生成するファクトリです。
 * @author snowgoose
 */
public class GuiceContainerAdaptorFactory implements ContainerAdaptorFactory<GuiceContainerAdaptor> {

    @Override
    public GuiceContainerAdaptor createContainerAdaptor(ApplicationContextResolver resolver) {
        Assertion.notNull(resolver, ApplicationContextResolver.class.getName());
        Injector injector = resolver.resolve(Injector.class, Injector.class.getName());
        if (injector == null) {
            return null;
        }
        return new GuiceContainerAdaptor(injector);
    }

}
