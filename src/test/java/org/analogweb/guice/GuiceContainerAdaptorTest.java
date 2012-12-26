package org.analogweb.guice;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.List;

import org.analogweb.exception.AssertionFailureException;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.inject.AbstractModule;
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
    private Injector injector;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {

                Multibinder<Foo> multiBinder = Multibinder.newSetBinder(binder(), Foo.class);
                multiBinder.addBinding().to(FooImpl.class);
                multiBinder.addBinding().to(OtherFooImpl.class);

                bind(Baa.class).to(BaaImpl.class);
            }
        });
        adaptor = new GuiceContainerAdaptor(injector);
    }

    @Test
    public void testGetInstanceOfType() {
        Foo foo = adaptor.getInstanceOfType(Foo.class);
        assertThat(foo, instanceOf(FooImpl.class));
        Baa baa = adaptor.getInstanceOfType(Baa.class);
        assertThat(baa, instanceOf(BaaImpl.class));
        Baz baz = adaptor.getInstanceOfType(Baz.class);
        assertThat(baz, is(nullValue()));
    }

    @Test
    public void testGetInstanceOfTypeWithoutInjector() {
        thrown.expect(AssertionFailureException.class);
        adaptor = new GuiceContainerAdaptor(null);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetInstancesOfType() {
        List<Foo> foo = adaptor.getInstancesOfType(Foo.class);

        assertThat(foo.size(), is(2));
        assertThat(foo.get(0), instanceOf(Foo.class));
        assertThat(foo.get(1), instanceOf(Foo.class));

        List<Baa> baa = adaptor.getInstancesOfType(Baa.class);
        assertThat(baa.size(), is(1));
        assertThat(baa.get(0), instanceOf(BaaImpl.class));

        List<Baz> baz = adaptor.getInstancesOfType(Baz.class);
        assertThat(baz, is(emptyCollection()));
    }

    @SuppressWarnings("rawtypes")
    private static Matcher emptyCollection() {
        return new BaseMatcher<List<?>>() {
            @Override
            public boolean matches(Object arg0) {
                if (arg0 instanceof Collection) {
                    Collection<?> c = (Collection<?>) arg0;
                    return c.isEmpty();
                }
                return false;
            }

            @Override
            public void describeTo(Description arg0) {

            }
        };
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
