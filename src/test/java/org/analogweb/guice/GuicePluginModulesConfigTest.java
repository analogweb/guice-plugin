package org.analogweb.guice;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.analogweb.ModulesBuilder;
import org.analogweb.guice.GuiceContainerAdaptorFactory;
import org.analogweb.guice.GuicePluginModulesConfig;
import org.junit.Before;
import org.junit.Test;

/**
 * @author snowgoose
 */
public class GuicePluginModulesConfigTest {

    private GuicePluginModulesConfig config;
    private ModulesBuilder modulesBuilder;

    @Before
    public void setUp() throws Exception {
        config = new GuicePluginModulesConfig();
        modulesBuilder = mock(ModulesBuilder.class);
    }

    @Test
    public void testPrepare() {

        when(modulesBuilder.setInvocationInstanceProviderClass(GuiceContainerAdaptorFactory.class))
                .thenReturn(modulesBuilder);

        ModulesBuilder actual = config.prepare(modulesBuilder);
        assertSame(actual, modulesBuilder);

        verify(modulesBuilder).setInvocationInstanceProviderClass(
                GuiceContainerAdaptorFactory.class);
    }

}
