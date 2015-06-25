package org.analogweb.guice.it;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.analogweb.ApplicationContext;
import org.analogweb.ApplicationProperties;
import org.analogweb.core.DefaultApplicationProperties;
import org.analogweb.core.fake.FakeApplication;
import org.analogweb.core.fake.ResponseResult;
import org.analogweb.guice.GuiceApplicationContext;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class GuicePluginIntegrationTest {

	private Injector injector;
	private FakeApplication app;

	@Before
	public void setUp() throws Exception {
		injector = Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
				bind(TestRouter.class);
				bind(TestModule.class);
			}
		});
		ApplicationContext context = GuiceApplicationContext.context(injector);
		ApplicationProperties props = DefaultApplicationProperties
				.properties(GuicePluginIntegrationTest.class.getPackage()
						.getName());
		app = new FakeApplication(context, props);
	}

	@Test
	public void test() {
		ResponseResult result = app.request("/hello", "GET");
		assertThat(result.toBody(), is("Hello!"));
	}

}
