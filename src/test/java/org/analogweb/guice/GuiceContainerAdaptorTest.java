package org.analogweb.guice;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.servlet.ServletContext;

import org.analogweb.guice.GuiceContainerAdaptor;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.inject.AbstractModule;
import com.google.inject.ConfigurationException;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.multibindings.Multibinder;

/**
 * @author snowgoose
 */
public class GuiceContainerAdaptorTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private GuiceContainerAdaptor adaptor;
    private ServletContext servletContext;
    private Injector injector;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        servletContext = mock(ServletContext.class);
        adaptor = new GuiceContainerAdaptor(servletContext);
        injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {

                Multibinder<Foo> multiBinder = Multibinder.newSetBinder(binder(), Foo.class);
                multiBinder.addBinding().to(FooImpl.class);
                multiBinder.addBinding().to(OtherFooImpl.class);

                bind(Baa.class).to(BaaImpl.class);
            }
        });
    }

    @Test
    public void testGetInstanceOfType() {
        injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(Foo.class).to(FooImpl.class);
                bind(Baa.class).to(BaaImpl.class);
            }
        });
        when(servletContext.getAttribute(Injector.class.getName())).thenReturn(injector);

        Foo foo = adaptor.getInstanceOfType(Foo.class);
        assertTrue(foo instanceof FooImpl);
        Baa baa = adaptor.getInstanceOfType(Baa.class);
        assertTrue(baa instanceof BaaImpl);
    }

    @Test
    public void testGetInstanceOfTypeWithNotBindingType() {
        thrown.expect(ConfigurationException.class);
        when(servletContext.getAttribute(Injector.class.getName())).thenReturn(injector);

        adaptor.getInstanceOfType(Baz.class);
    }

    @Test
    public void testGetInstanceOfTypeWithoutInjector() {
        when(servletContext.getAttribute(Injector.class.getName())).thenReturn(null);

        assertNull(adaptor.getInstanceOfType(Foo.class));
        assertNull(adaptor.getInstanceOfType(Baa.class));
        assertNull(adaptor.getInstanceOfType(Baz.class));
    }

    @Test
    public void testGetInstancesOfTypeWithoutInjector() {
        when(servletContext.getAttribute(Injector.class.getName())).thenReturn(null);

        assertTrue(adaptor.getInstancesOfType(Foo.class).isEmpty());
        assertTrue(adaptor.getInstancesOfType(Baa.class).isEmpty());
        assertTrue(adaptor.getInstancesOfType(Baz.class).isEmpty());
    }

    @Test
    public void testGetInstancesOfType() {
        when(servletContext.getAttribute(Injector.class.getName())).thenReturn(injector);

        List<Foo> foo = adaptor.getInstancesOfType(Foo.class);

        assertThat(foo.size(), is(2));
        assertTrue(foo.get(0) instanceof Foo);
        assertTrue(foo.get(1) instanceof Foo);
        assertFalse(foo.get(0).getClass().equals(foo.get(1).getClass()));
    }

    private static interface Foo {
    }

    private static class FooImpl implements Foo {
    }

    private static class OtherFooImpl implements Foo {
    }

    private static interface Baa {
    }

    private static class BaaImpl implements Baa {
    }

    private static interface Baz {

    }

}
