package de.digitalfrontiers.springboot.mina.autoconfigure;

import de.digitalfrontiers.util.mina.NoopShellFactory;
import org.apache.sshd.server.ServerBuilder;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.auth.AsyncAuthException;
import org.apache.sshd.server.auth.keyboard.DefaultKeyboardInteractiveAuthenticator;
import org.apache.sshd.server.auth.password.PasswordAuthenticator;
import org.apache.sshd.server.auth.password.PasswordChangeRequiredException;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.session.ServerSession;
import org.apache.sshd.server.shell.ShellFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnClass(SshServer.class)
@Configuration
public class MinaSSHDAutoConfiguration {


  @ConditionalOnMissingBean
  @Bean(initMethod = "start", destroyMethod = "stop")
  public SshServer sshServer(ShellFactory shellFactory) {
    final ServerBuilder builder = ServerBuilder.builder();
    final SshServer server = builder.build();
    server.setPort(8022);

    server.setKeyboardInteractiveAuthenticator(
        new DefaultKeyboardInteractiveAuthenticator());
    server.setPasswordAuthenticator(passwordAuthenticator());
    server.setKeyPairProvider(new SimpleGeneratorHostKeyProvider());

    server.setShellFactory(shellFactory);

    return server;
  }

  private PasswordAuthenticator passwordAuthenticator() {
    return new PasswordAuthenticator() {
      @Override
      public boolean authenticate(String username, String password, ServerSession session)
          throws PasswordChangeRequiredException, AsyncAuthException {
        return "admin".equals(username) && "password".equals(password);
      }
    };
  }

  @Bean
  @ConditionalOnMissingBean
  public ShellFactory shellFactory() {
    return new NoopShellFactory();
  }

}
