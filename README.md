Analog Web Framework
===============================================

[Guice](https://github.com/google/guice) application working with AnalogWeb's Router. 

```java
public class HelloModule {

   public String sayHello() {
      return "Hello!";
   }

}
```


```java
import java.net.URI;
import javax.inject.Inject;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import org.analogweb.ApplicationContext;
import org.analogweb.ApplicationProperties;
import org.analogweb.annotation.Route;
import org.analogweb.core.DefaultApplicationProperties;
import org.analogweb.core.Servers;
import org.analogweb.guice.GuiceApplicationContext;

@Route("/")
public class Hello {

      public static void main(String... args) {

         Injectori injector = Guice.createInjector(new AbstractModule() {
             @Override
             protected void configure() {
               bind(Hello.class);
               bind(HelloModule.class);
             }
         });
         ApplicationContext context = GuiceApplicationContext.context(injector);
         ApplicationProperties props = DefaultApplicationProperties
                                     .properties(Hello.class.getPackage().getName());
         Servers.create(URI,create("http://localhost:8080"),props,context).run();
      }

      @Inject
      private HelloModule module;

      @Route
      public String hello() {
         return module.sayHello();
      }
}
```
