package de.digitalfrontiers.springboot.environment;

import de.digitalfrontiers.util.PrintUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootApplication
public class SpringEnvironmentExplorationApplication {

  public static void main(String[] args) {

    final ConfigurableApplicationContext context =
        SpringApplication.run(SpringEnvironmentExplorationApplication.class, args);

    PrintUtil.printCaption("Spring Environment Exploration");

    final ConfigurableEnvironment environment = context.getEnvironment();
    PrintUtil.printKeyValue("server.port", environment.getProperty("server.port"));

    // print all currently activated profiles
    PrintUtil.printKeyValue("active profiles:", environment.getActiveProfiles());

    // print all property sources
    final String[] sources = environment.getPropertySources()
        .stream()
        .map(Object::toString)
        .toArray(String[]::new);
    PrintUtil.printKeyValue("property sources:", sources);

    // close the spring application
    System.out.println("-----------------------------");
    System.exit(SpringApplication.exit(context));
  }

}
