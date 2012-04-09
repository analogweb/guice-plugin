package org.analogweb.guice;

import javax.servlet.ServletContext;


import org.analogweb.ContainerAdaptorFactory;
import org.analogweb.util.Assertion;

/**
 * {@link GuiceContainerAdaptor}のインスタンスを生成するファクトリです。
 * @author snowgoose
 */
public class GuiceContainerAdaptorFactory implements ContainerAdaptorFactory<GuiceContainerAdaptor> {

    @Override
    public GuiceContainerAdaptor createContainerAdaptor(ServletContext servletContext) {
        Assertion.notNull(servletContext, ServletContext.class.getName());
        return new GuiceContainerAdaptor(servletContext);
    }

}
