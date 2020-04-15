package de.digitalfrontiers.springboot.mina.autoconfigure;

import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.channel.ChannelSession;
import org.apache.sshd.server.command.Command;
import org.apache.sshd.server.shell.ShellFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.logging.ConditionEvaluationReportLoggingListener;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.test.context.FilteredClassLoader;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class MinaSSHDAutoConfigurationTest {

  private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
      // used to print the evaluation report for debugging purposes
      .withInitializer(
          new ConditionEvaluationReportLoggingListener(LogLevel.INFO))
      // the actual auto configuration to be tested
      .withConfiguration(
          AutoConfigurations.of(MinaSSHDAutoConfiguration.class));

  @Test
  void autoconfigurationCreatesConfiguresAndStartsSshServer() {
    contextRunner.run(context -> {
      assertThat(context).hasNotFailed();
      assertThat(context).hasSingleBean(SshServer.class);
      assertThat(context).getBean(SshServer.class)
          .hasFieldOrPropertyWithValue("started", true);
    });
  }



  @Test
  void doesNotFailIfTheSshServerIsNotOnTheClasspath() {
    contextRunner
        // exclude the "key" trigger class
        .withClassLoader(new FilteredClassLoader(SshServer.class))
        // now the autoconfiguration will not run
        .run(context -> {
          assertThat(context).hasNotFailed();
        });
  }



  @Test
  void userDefinedShellFactoryWillBeUsedInsteadOfDefault() {
    contextRunner
        .withUserConfiguration(CustomShellFactoryConfiguration.class)
        .run(context -> {
          assertThat(context).hasNotFailed();
          assertThat(context).getBean(SshServer.class).satisfies(sshServer -> {
            assertThat(sshServer.getShellFactory())
                .isInstanceOf(DummyShellFactory.class);
          });
        });
  }

  @Configuration
  public static class CustomShellFactoryConfiguration {

    @Bean
    public ShellFactory shellFactory() {
      return new DummyShellFactory();
    }
  }

  private static final class DummyShellFactory implements ShellFactory {
    @Override
    public Command createShell(ChannelSession channel) throws IOException {
      throw new UnsupportedOperationException();
    }
  }
}
