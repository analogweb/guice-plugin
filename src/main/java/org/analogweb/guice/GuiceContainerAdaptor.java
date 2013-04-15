package org.analogweb.guice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.analogweb.ContainerAdaptor;
import org.analogweb.util.Assertion;
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
    private final Injector injector;

    public GuiceContainerAdaptor(Injector injector) {
        Assertion.notNull(injector, Injector.class.getName());
        this.injector = injector;
    }

    @Override
    public <T> T getInstanceOfType(Class<T> type) {
        Injector injector = getInjector();
        List<Binding<T>> bindings = findBindings(type, injector);
        if (bindings.isEmpty()) {
            return null;
        }
        T instance = injector.getInstance(bindings.get(0).getKey());
        log.log(GuicePluginModulesConfig.PLUGIN_MESSAGE_RESOURCE, "DGB000001", type, instance,
                String.valueOf(injector.hashCode()));
        return instance;
    }

    @Override
    public <T> List<T> getInstancesOfType(Class<T> type) {
        Injector injector = getInjector();
        List<Binding<T>> findBindingsByType = findBindings(type, injector);
        List<T> typeInstances = new ArrayList<T>();
        for (Binding<T> binding : findBindingsByType) {
            T instance = injector.getInstance(binding.getKey());
            typeInstances.add(instance);
        }
        log.log(GuicePluginModulesConfig.PLUGIN_MESSAGE_RESOURCE, "DGB000001", type, typeInstances,
                injector);
        return Collections.unmodifiableList(typeInstances);
    }

    private <T> List<Binding<T>> findBindings(Class<T> type, Injector injector) {
        TypeLiteral<T> typeLiteral = TypeLiteral.get(type);
        return injector.findBindingsByType(typeLiteral);
    }

    protected Injector getInjector() {
        return this.injector;
    }
}
