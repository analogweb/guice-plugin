package org.analogweb.guice;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.analogweb.ApplicationContextResolver;
import org.analogweb.core.AssertionFailureException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.inject.Injector;

/**
 * @author snowgoose
 */
public class GuiceContainerAdaptorFactoryTest {

    private GuiceContainerAdaptorFactory factory;
    private ApplicationContextResolver resolver;
    private Injector injector;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        factory = new GuiceContainerAdaptorFactory();
        resolver = mock(ApplicationContextResolver.class);
        injector = mock(Injector.class);
    }

    @Test
    public void testCreateContainerAdaptor() {
        when(resolver.resolve(Injector.class, Injector.class.getName())).thenReturn(injector);
        GuiceContainerAdaptor containerAdaptor = factory.createContainerAdaptor(resolver);
        assertThat(containerAdaptor, is(not(nullValue())));
        GuiceContainerAdaptor other = factory.createContainerAdaptor(resolver);
        assertThat(containerAdaptor, is(not(sameInstance(other))));
    }

    @Test
    public void testCreateContainerAdaptorWithoutInjector() {
        when(resolver.resolve(Injector.class, Injector.class.getName())).thenReturn(null);
        GuiceContainerAdaptor containerAdaptor = factory.createContainerAdaptor(resolver);
        assertThat(containerAdaptor, is(nullValue()));
    }

    @Test
    public void testCreateContainerAdaptorWithNullServletContext() {
        thrown.expect(AssertionFailureException.class);
        factory.createContainerAdaptor(null);
    }

}
