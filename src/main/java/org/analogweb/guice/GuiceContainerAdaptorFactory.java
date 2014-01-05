package org.analogweb.guice;

import org.analogweb.ApplicationContext;
import org.analogweb.ContainerAdaptorFactory;
import org.analogweb.util.Assertion;

import com.google.inject.Injector;

/**
 * {@link GuiceContainerAdaptor}のインスタンスを生成するファクトリです。
 * @author snowgoose
 */
public class GuiceContainerAdaptorFactory implements ContainerAdaptorFactory<GuiceContainerAdaptor> {

    @Override
    public GuiceContainerAdaptor createContainerAdaptor(ApplicationContext context) {
        Assertion.notNull(context, ApplicationContext.class.getName());
        Injector injector = context.getAttribute(Injector.class, Injector.class.getName());
        if (injector == null) {
            return null;
        }
        return new GuiceContainerAdaptor(injector);
    }

}
