package org.analogweb.guice.it;

import javax.inject.Inject;

import org.analogweb.annotation.Route;

@Route("/")
public class TestRouter {
	
	@Inject
	private TestModule module;

	@Route
	public String hello(){
		return module.sayHello();
	}
}
