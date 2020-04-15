package de.digitalfrontiers.springboot;

import org.apache.sshd.server.shell.ShellFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SshServerApplication {

  @Bean
  public ShellFactory shellFactory() {
    return new SimpleShellFactory();
  }

  public static void main(String[] args) throws Exception {

    SpringApplication.run(SshServerApplication.class, args);

  }

}
