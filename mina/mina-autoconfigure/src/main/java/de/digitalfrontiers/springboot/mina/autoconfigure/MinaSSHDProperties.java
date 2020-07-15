package de.digitalfrontiers.springboot.mina.autoconfigure;

import org.apache.sshd.common.FactoryManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.core.io.Resource;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "mina.sshd")
@Validated
public class MinaSSHDProperties {

  private final Session session = new Session();
  @Min(1)
  @Max(65535)
  private int port = 8022;
  private List<Resource> hostKeys;
  private final List<User> users = new ArrayList<>();

  public Session getSession() {
    return session;
  }

  public int getPort() {
    return port;
  }

  /**
   * The port for which SSH connections shall be provided.
   */
  public void setPort(int port) {
    this.port = port;
  }

  public List<Resource> getHostKeys() {
    return hostKeys;
  }

  /**
   * SSH key that the host shall provide as its identification.
   */
  public void setHostKeys(List<Resource> hostKeys) {
    this.hostKeys = hostKeys;
  }

  public List<User> getUsers() {
    return users;
  }

  public static class Session {
    @DurationUnit(ChronoUnit.SECONDS)
    private Duration idleTimeout = Duration.ofMillis(FactoryManager.DEFAULT_IDLE_TIMEOUT);

    public Duration getIdleTimeout() {
      return idleTimeout;
    }

    /**
     * A timeout, expressed in seconds, after which idle sessions will be closed.
     */
    public void setIdleTimeout(Duration idleTimeout) {
      this.idleTimeout = idleTimeout;
    }

  }

  public static class User {
    private String name;
    private String password;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }
  }
}
