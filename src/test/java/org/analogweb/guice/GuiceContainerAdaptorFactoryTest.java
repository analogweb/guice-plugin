package org.analogweb.guice;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;

import javax.servlet.ServletContext;


import org.analogweb.exception.AssertionFailureException;
import org.analogweb.guice.GuiceContainerAdaptor;
import org.analogweb.guice.GuiceContainerAdaptorFactory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author snowgoose
 */
public class GuiceContainerAdaptorFactoryTest {

    private GuiceContainerAdaptorFactory factory;
    private ServletContext servletContext;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        factory = new GuiceContainerAdaptorFactory();
        servletContext = mock(ServletContext.class);
    }

    @Test
    public void testCreateContainerAdaptor() {
        GuiceContainerAdaptor containerAdaptor = factory.createContainerAdaptor(servletContext);
        assertNotNull(containerAdaptor);
        GuiceContainerAdaptor other = factory.createContainerAdaptor(servletContext);
        assertNotSame(containerAdaptor, other);
    }

    @Test
    public void testCreateContainerAdaptorWithNullServletContext() {
        thrown.expect(AssertionFailureException.class);
        factory.createContainerAdaptor(null);
    }

}
