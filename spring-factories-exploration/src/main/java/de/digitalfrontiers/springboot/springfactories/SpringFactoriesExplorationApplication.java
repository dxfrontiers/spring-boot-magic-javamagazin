package de.digitalfrontiers.springboot.springfactories;

import de.digitalfrontiers.util.PrintUtil;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.util.List;

/**
 * Sample Application to explore the functionality of {@link
 * org.springframework.core.io.support.SpringFactoriesLoader}.
 * <p>
 * This is one of the rare examples that will not be an actual {@link
 * org.springframework.boot.autoconfigure.SpringBootApplication}
 */
public class SpringFactoriesExplorationApplication {

  public static void main(String[] args) {

    final List<String> candidates = SpringFactoriesLoader
        .loadFactoryNames(EnableAutoConfiguration.class, null);

    PrintUtil.printClassTree(candidates);
  }

}
