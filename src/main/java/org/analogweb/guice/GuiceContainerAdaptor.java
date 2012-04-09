package org.analogweb.guice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletContext;


import org.analogweb.ContainerAdaptor;
import org.analogweb.util.logging.Log;
import org.analogweb.util.logging.Logs;

import com.google.inject.Binding;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;

/**
 * {@link Injector}が管理するオブジェクトのインスタンスを取得する
 * {@link ContainerAdaptor}の実装です。
 * @author snowgoose
 */
public class GuiceContainerAdaptor implements ContainerAdaptor {

    private static final Log log = Logs.getLog(GuiceContainerAdaptor.class);
    private final ServletContext servletContext;
    private Injector injector;

    public GuiceContainerAdaptor(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public <T> T getInstanceOfType(Class<T> type) {
        Injector injector = getInjector();
        if (injector != null) {
            T instance = injector.getInstance(type);
            log.log(GuicePluginModulesConfig.PLUGIN_MESSAGE_RESOURCE, "DGB000001", type, instance,
                    String.valueOf(injector.hashCode()));
            return instance;
        }
        return null;
    }

    @Override
    public <T> List<T> getInstancesOfType(Class<T> type) {
        Injector injector = getInjector();
        if (injector == null) {
            return Collections.emptyList();
        }
        TypeLiteral<T> typeLiteral = TypeLiteral.get(type);
        List<Binding<T>> findBindingsByType = injector.findBindingsByType(typeLiteral);
        List<T> typeInstances = new ArrayList<T>();
        for (Binding<T> binding : findBindingsByType) {
            T instance = injector.getInstance(binding.getKey());
            typeInstances.add(instance);
        }
        log.log(GuicePluginModulesConfig.PLUGIN_MESSAGE_RESOURCE, "DGB000001", type, typeInstances,
                injector);
        return typeInstances;
    }

    protected Injector getInjector() {
        if (this.injector == null) {
            this.injector = (Injector) servletContext.getAttribute(Injector.class.getName());
            if (this.injector != null) {
                log.log(GuicePluginModulesConfig.PLUGIN_MESSAGE_RESOURCE, "IGB000002",
                        new Object[] { String.valueOf(this.injector.hashCode()), this.injector });
            } else {
                log.log(GuicePluginModulesConfig.PLUGIN_MESSAGE_RESOURCE, "WGB000001");
            }
        }
        return this.injector;
    }

}
