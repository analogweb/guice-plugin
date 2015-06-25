package org.analogweb.guice;

import java.util.Map;

import org.analogweb.core.DefaultApplicationContext;
import org.analogweb.util.Assertion;
import org.analogweb.util.Maps;

import com.google.inject.Injector;

/**
 * {@link DefaultApplicationContext} associated with {@link Injector}.
 * @author snowgooseyk
 */
public class GuiceApplicationContext extends DefaultApplicationContext {

	public static GuiceApplicationContext context(Injector injector) {
		return new GuiceApplicationContext(injector);
	}

	public GuiceApplicationContext(Injector injector) {
		this(Maps.newHashMap(Injector.class.getName(), injector));
		Assertion.notNull(injector, Injector.class.getCanonicalName());
	}

	public GuiceApplicationContext(Map<String, ?> context) {
		super(context);
	}

}
